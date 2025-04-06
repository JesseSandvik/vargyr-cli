package com.vargyr.command.parser.picocli;

import com.vargyr.command.Option;
import com.vargyr.command.PositionalParameter;
import io.micronaut.core.util.StringUtils;
import lombok.Getter;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.PositionalParamSpec;
import picocli.CommandLine.Model.OptionSpec;

public class PicocliCommandLineBuilder {
    private final CommandSpec commandSpec;

    public PicocliCommandLineBuilder(String commandName) {
        this.commandSpec = CommandSpec.create().name(commandName);
    }

    public PicocliCommandLineBuilder(CommandSpec commandSpec) {
        this.commandSpec = commandSpec;
    }

    public PicocliCommandLineBuilder formatUsageHelp() {
        commandSpec.usageMessage()
                .abbreviateSynopsis(true)
                .parameterListHeading("%nPositional Parameters:%n")
                .optionListHeading("%nOptions:%n")
                .commandListHeading("%nSubcommands:%n");
        return this;
    }

    public PicocliCommandLineBuilder setSynopsis(String synopsis) {
        if (StringUtils.isNotEmpty(synopsis) && !synopsis.isBlank()) {
            commandSpec.usageMessage().description(synopsis);
        }
        return this;
    }

    public PicocliCommandLineBuilder setDescription(String description) {
        if (StringUtils.isNotEmpty(description) && !description.isBlank()) {
            commandSpec.usageMessage().footer("%n" + description + "%n");
        }
        return this;
    }

    public PicocliCommandLineBuilder addPositionalParameter(PositionalParameter positionalParameter) {
        if (positionalParameter != null &&
                StringUtils.isNotEmpty(positionalParameter.getLabel()) &&
                !positionalParameter.getLabel().isBlank() &&
                StringUtils.isNotEmpty(positionalParameter.getSynopsis()) &&
                !positionalParameter.getSynopsis().isBlank()) {
            commandSpec.addPositional(PositionalParamSpec
                    .builder()
                    .paramLabel(positionalParameter.getLabel())
                    .description(positionalParameter.getSynopsis())
                    .build());
        }
        return this;
    }

    public PicocliCommandLineBuilder addOption(Option option) {
        if (option != null &&
                StringUtils.isNotEmpty(option.getLongName()) && !option.getLongName().isBlank() &&
                StringUtils.isNotEmpty(option.getSynopsis()) && !option.getSynopsis().isBlank()) {
            OptionSpec.Builder optionSpecBuilder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(option);
            optionSpecBuilder.description(option.getSynopsis());
            OptionSpecBuilderUtility.setOptionSpecBuilderTypeForOption(option, optionSpecBuilder);
            commandSpec.addOption(optionSpecBuilder.build());
        }
        return this;
    }

    public PicocliCommandLineBuilder addVersionOption(String version) {
        if (StringUtils.isNotEmpty(version) && !version.isBlank()) {
            commandSpec.version(version);
            commandSpec.addOption(OptionSpec
                    .builder(DefaultOption.VERSION.getValue())
                    .description(DefaultOptionDescription.VERSION.getValue())
                    .versionHelp(true)
                    .build()
            );
        }
        return this;
    }

    public PicocliCommandLineBuilder addHelpOption() {
        commandSpec.addOption(OptionSpec
                .builder(DefaultOption.HELP_LONG.getValue(), DefaultOption.HELP_SHORT.getValue())
                .description(DefaultOptionDescription.HELP.getValue())
                .usageHelp(true)
                .build()
        );
        return this;
    }

    public PicocliCommandLineBuilder addDebugOption() {
        commandSpec.addOption(OptionSpec
                .builder(DefaultOption.DEBUG.getValue())
                .description(DefaultOptionDescription.DEBUG.getValue())
                .build()
        );
        return this;
    }

    public PicocliCommandLineBuilder addQuietOption() {
        commandSpec.addOption(OptionSpec
                .builder(DefaultOption.QUIET.getValue())
                .description(DefaultOptionDescription.QUIET.getValue())
                .build()
        );
        return this;
    }

    public PicocliCommandLineBuilder addNoInputOption() {
        commandSpec.addOption(OptionSpec
                .builder(DefaultOption.NO_INPUT.getValue())
                .description(DefaultOptionDescription.NO_INPUT.getValue())
                .build()
        );
        return this;
    }

    public PicocliCommandLineBuilder addSubcommand(CommandLine commandLine) {
        if (commandLine != null &&
                StringUtils.isNotEmpty(commandLine.getCommandName()) &&
                !commandLine.getCommandName().isBlank()) {
            commandSpec.addSubcommand(commandLine.getCommandName(), commandLine);
        }
        return this;
    }

    public CommandLine build() {
        return new CommandLine(commandSpec);
    }

    @Getter
    public enum DefaultOption {
        DEBUG("--debug"),
        HELP_LONG("--help"),
        HELP_SHORT("-h"),
        NO_INPUT("--no-input"),
        QUIET("--quiet"),
        VERSION("--version");

        private final String value;

        DefaultOption(String value) {
            this.value = value;
        }
    }

    @Getter
    public enum DefaultOptionDescription {
        DEBUG("Show debugging output."),
        HELP("Show this help message and exit."),
        NO_INPUT("Disable interactive prompts."),
        QUIET("Show minimal output."),
        VERSION("Show version information and exit.");

        private final String value;

        DefaultOptionDescription(String value) {
            this.value = value;
        }
    }
}
