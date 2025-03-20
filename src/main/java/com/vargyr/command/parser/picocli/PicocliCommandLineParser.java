package com.vargyr.command.parser.picocli;

import com.vargyr.command.Option;
import com.vargyr.command.PositionalParameter;
import com.vargyr.command.VgrCommand;
import com.vargyr.command.execution.CommandExecution;
import com.vargyr.command.execution.CommandExecutionState;
import com.vargyr.command.execution.error.CommandExecutionError;
import com.vargyr.command.parser.CommandLineParser;

import io.micronaut.core.util.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.Model.OptionSpec;
import picocli.CommandLine.Model.PositionalParamSpec;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class PicocliCommandLineParser implements CommandLineParser {
    private final VgrCommand command;

    public PicocliCommandLineParser(VgrCommand command) {
        this.command = command;
    }

    private Integer handleUsageHelpRequested(CommandLine commandLine) {
        commandLine.usage(commandLine.getOut());
        return commandLine.getCommandSpec().exitCodeOnUsageHelp();
    }

    private Integer handleVersionHelpRequested(CommandLine commandLine) {
        commandLine.printVersionHelp(commandLine.getOut());
        return commandLine.getCommandSpec().exitCodeOnVersionHelp();
    }

    private VgrCommand getCommandForParseResult(ParseResult parseResult) {
        String commandName = parseResult.commandSpec().name();
        if (StringUtils.isNotEmpty(command.getMetadata().getName()) &&
                commandName.equalsIgnoreCase(command.getMetadata().getName())) {
            return command;
        }

        if (!command.getSubcommands().isEmpty()) {
            Queue<VgrCommand> commandQueue = new ArrayDeque<>(command.getSubcommands());
            while (!commandQueue.isEmpty()) {
                VgrCommand currentCommand = commandQueue.remove();
                if (StringUtils.isNotEmpty(currentCommand.getMetadata().getName()) &&
                        commandName.equalsIgnoreCase(currentCommand.getMetadata().getName())) {
                    return currentCommand;
                }
                commandQueue.addAll(currentCommand.getSubcommands());
            }
        }
        return null;
    }

    private void setPositionalParameterValues(
            List<PositionalParameter> positionalParameters, ParseResult parseResult) {
        List<PositionalParamSpec> matchedPositionalParameters = parseResult.matchedPositionals();

        if (!matchedPositionalParameters.isEmpty()) {
            positionalParameters.forEach(positionalParameter -> {
                matchedPositionalParameters.forEach(matchedPositionalParameter -> {
                    if (positionalParameter.getLabel().equalsIgnoreCase(matchedPositionalParameter.paramLabel())) {
                        positionalParameter.setValue(matchedPositionalParameter.getValue());
                    }
                });
            });
        }
    }

    private void setOptionValues(List<Option> options, ParseResult parseResult) {
        List<OptionSpec> matchedOptions = parseResult.matchedOptions();
        if (!matchedOptions.isEmpty()) {
            options.forEach(option -> {
                matchedOptions.forEach(matchedOption -> {
                    if (option.getLongName().equalsIgnoreCase(matchedOption.longestName())) {
                        option.setValue(matchedOption.getValue());
                    }
                });
            });
        }
    }

    private CommandLine getCommandLineForCommand(VgrCommand command) {
        PicocliCommandLineBuilder builder = new PicocliCommandLineBuilder(command.getMetadata().getName())
                .setSynopsis(command.getMetadata().getSynopsis())
                .setDescription(command.getMetadata().getDescription())
                .addVersionOption(command.getMetadata().getVersion())
                .addHelpOption()
                .addDebugOption()
                .addQuietOption()
                .addNoInputOption()
                .formatUsageHelp();

        if (command.getPositionalParameters() != null && !command.getPositionalParameters().isEmpty()) {
            command.getPositionalParameters().forEach(builder::addPositionalParameter);
        }

        if (command.getOptions() != null && !command.getOptions().isEmpty()) {
            command.getOptions().forEach(builder::addOption);
        }

        if (command.getSubcommands() != null && !command.getSubcommands().isEmpty()) {
            command.getSubcommands().forEach(subcommand -> builder.addSubcommand(getCommandLineForCommand(subcommand)));
        }
        return builder.build();
    }

    @Override
    public void parse(CommandExecution commandExecution) {
        if (command.getMetadata() == null) {
            CommandExecutionError error = new CommandExecutionError();
            error.setDisplayMessage("Unable to parse command line. Metadata not found for command.");
            error.setFatal(true);
            commandExecution.setState(CommandExecutionState.END);
            commandExecution.getErrors().add(error);
            commandExecution.setExitCode(1);
            return;
        }

        if (command.getOriginalArguments() == null) {
            CommandExecutionError error = new CommandExecutionError();
            error.setDisplayMessage("Unable to parse command line. Original arguments not found for command.");
            error.setFatal(true);
            commandExecution.setState(CommandExecutionState.END);
            commandExecution.getErrors().add(error);
            commandExecution.setExitCode(1);
            return;
        }

        CommandLine commandLine = getCommandLineForCommand(command);
        ParseResult parseResult = commandLine.parseArgs(command.getOriginalArguments());

        Queue<CommandLine> commandLineQueue = new ArrayDeque<>(parseResult.asCommandLineList());
        while (!commandLineQueue.isEmpty()) {
            CommandLine currentCommandLine = commandLineQueue.remove();

            if (currentCommandLine.isUsageHelpRequested()) {
                commandExecution.setState(CommandExecutionState.END);
                commandExecution.setExitCode(handleUsageHelpRequested(currentCommandLine));
                return;
            }

            if (currentCommandLine.isVersionHelpRequested()) {
                commandExecution.setState(CommandExecutionState.END);
                commandExecution.setExitCode(handleVersionHelpRequested(currentCommandLine));
                return;
            }

            ParseResult currentParseResult = currentCommandLine.getParseResult();
            VgrCommand currentCommand = getCommandForParseResult(currentParseResult);
            if (currentCommand == null) {
                CommandExecutionError error = new CommandExecutionError();
                error.setDisplayMessage("Unable to parse command line. Command not found.");
                error.setFatal(true);
                commandExecution.setState(CommandExecutionState.END);
                commandExecution.getErrors().add(error);
                commandExecution.setExitCode(1);
                return;
            }

            if (currentParseResult.expandedArgs().isEmpty() &&
                    !currentCommand.getMetadata().getExecutesWithoutArguments()) {
                commandExecution.setState(CommandExecutionState.END);
                commandExecution.setExitCode(handleUsageHelpRequested(currentCommandLine));
            }

            if (currentCommand.getPositionalParameters() != null && !currentCommand.getPositionalParameters().isEmpty()) {
                setPositionalParameterValues(currentCommand.getPositionalParameters(), currentParseResult);
            }

            if (currentCommand.getOptions() != null && !currentCommand.getOptions().isEmpty()) {
                setOptionValues(currentCommand.getOptions(), currentParseResult);
            }

            if (commandLineQueue.isEmpty()) {
                commandExecution.setExecutedCommand(currentCommand);
                return;
            }
        }
    }
}
    