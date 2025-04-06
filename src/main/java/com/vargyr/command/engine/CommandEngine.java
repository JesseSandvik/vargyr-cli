package com.vargyr.command.engine;

import com.vargyr.command.VgrCommand;
import com.vargyr.command.execution.CommandExecution;
import com.vargyr.command.execution.CommandExecutionState;
import com.vargyr.command.execution.error.VgrCommandExecutionErrorManager;
import com.vargyr.command.execution.validator.VgrCommandExecutionValidator;
import com.vargyr.command.parser.picocli.PicocliCommandLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandEngine {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommandEngine.class);
    private final VgrCommand rootCommand;
    private final CommandExecution commandExecution;

    public CommandEngine(VgrCommand rootCommand) {
        this.rootCommand = rootCommand;
        this.commandExecution = new CommandExecution();
    }

    private void initializeCommandExecution(CommandExecution commandExecution, String[] arguments) {
        commandExecution.setErrorManager(new VgrCommandExecutionErrorManager());
        commandExecution.setValidator(new VgrCommandExecutionValidator());
        commandExecution.setParser(new PicocliCommandLineParser(commandExecution));
        commandExecution.setState(CommandExecutionState.INITIAL);
        commandExecution.setOriginalArguments(arguments);
        commandExecution.setRootCommand(this.rootCommand);
        commandExecution.setExitCode(0);
    }

    public Integer run(String[] arguments) {
        initializeCommandExecution(commandExecution, arguments);

        while (!commandExecution.getState().name().equalsIgnoreCase(CommandExecutionState.END.name())) {
            commandExecution.getState().processCurrentState(commandExecution);

            if (commandExecution.getErrorManager().fatalErrorOccurred()) {
                commandExecution.setState(CommandExecutionState.END);
            } else {
                commandExecution.setState(commandExecution.getState().transitionToNextState());
            }
        }

        commandExecution.getErrorManager().getErrors().forEach(error -> {
            if (error.getFatal()) {
                LOGGER.error(error.getDisplayMessage());
            }
        });
        return commandExecution.getExitCode();
    }
}
