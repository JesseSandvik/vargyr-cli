package com.vargyr.command.parser.picocli;

import com.vargyr.command.Option;
import com.vargyr.command.PositionalParameter;
import io.micronaut.core.util.StringUtils;
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
        if (positionalParameter.getLabel() != null && positionalParameter.getSynopsis() != null) {
            commandSpec.addPositional(PositionalParamSpec
                    .builder()
                    .paramLabel(positionalParameter.getLabel())
                    .description(positionalParameter.getSynopsis())
                    .build());
        }
        return this;
    }

    public PicocliCommandLineBuilder addOption(Option option) {
        if (option.getLongName() != null && option.getSynopsis() != null) {
            OptionSpec.Builder optionSpecBuilder = null;
            if (option.getShortName() != null) {
                optionSpecBuilder = OptionSpec.builder(option.getShortName(), option.getLongName());
            } else {
                optionSpecBuilder = OptionSpec.builder(option.getLongName());
            }
            optionSpecBuilder.description(option.getSynopsis());

            if (option.getArgumentLabel() != null) {
                optionSpecBuilder
                        .paramLabel(option.getArgumentLabel())
                        .type(String.class);
            } else {
                optionSpecBuilder.type(Boolean.class);
            }
            commandSpec.addOption(optionSpecBuilder.build());
        }
        return this;
    }

    public PicocliCommandLineBuilder addVersionOption(String version) {
        if (StringUtils.isNotEmpty(version) && !version.isBlank()) {
            commandSpec.version(version);
            commandSpec.addOption(OptionSpec
                    .builder("--version")
                    .description("Show version information and exit.")
                    .versionHelp(true)
                    .build()
            );
        }
        return this;
    }

    public PicocliCommandLineBuilder addHelpOption() {
        commandSpec.addOption(OptionSpec
                .builder("-h", "--help")
                .description("Show this help message and exit.")
                .usageHelp(true)
                .build()
        );
        return this;
    }

    public PicocliCommandLineBuilder addDebugOption() {
        commandSpec.addOption(CommandLine.Model.OptionSpec
                .builder("--debug")
                .description("Show debugging output.")
                .build()
        );
        return this;
    }

    public PicocliCommandLineBuilder addQuietOption() {
        commandSpec.addOption(CommandLine.Model.OptionSpec
                .builder("--quiet")
                .description("Show minimal output.")
                .build()
        );
        return this;
    }

    public PicocliCommandLineBuilder addNoInputOption() {
        commandSpec.addOption(CommandLine.Model.OptionSpec
                .builder("--no-input")
                .description("Disable interactive prompts.")
                .build()
        );
        return this;
    }

    public PicocliCommandLineBuilder addSubcommand(CommandLine commandLine) {
        commandSpec.addSubcommand(commandLine.getCommandName(), commandLine);
        return this;
    }

    public CommandLine build() {
        return new CommandLine(commandSpec);
    }
}
