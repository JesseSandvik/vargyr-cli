package com.vargyr.command.parser.picocli;

import com.vargyr.command.VgrCommand;
import com.vargyr.command.parser.CommandLineParser;

import io.micronaut.core.util.StringUtils;
import lombok.Getter;
import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;

@Getter
public class PicocliCommandLineParser implements CommandLineParser {
    private final VgrCommand vgrCommand;
    private final CommandSpec commandSpec;

    public PicocliCommandLineParser(VgrCommand vgrCommand, CommandSpec commandSpec) {
        this.vgrCommand = vgrCommand;
        this.commandSpec = commandSpec;
    }

    protected void formatUsageHelp() {
        commandSpec.usageMessage()
                .abbreviateSynopsis(true)
                .parameterListHeading("%nPositional Parameters:%n")
                .optionListHeading("%nOptions:%n")
                .commandListHeading("%nCommands:%n");
    }

    protected void setSynopsis() {
        if (vgrCommand.getMetadata() != null &&
                StringUtils.isNotEmpty(vgrCommand.getMetadata().getSynopsis()) &&
                !vgrCommand.getMetadata().getSynopsis().isBlank()) {
            commandSpec.usageMessage().description(vgrCommand.getMetadata().getSynopsis());
        }
    }

    protected void setDescription() {
        if (vgrCommand.getMetadata() != null &&
                StringUtils.isNotEmpty(vgrCommand.getMetadata().getDescription()) &&
                !vgrCommand.getMetadata().getDescription().isBlank()) {
            commandSpec.usageMessage().footer("%n" + vgrCommand.getMetadata().getDescription() + "%n");
        }
    }

    protected void addVersionOption() {
        if (vgrCommand.getMetadata() != null &&
                StringUtils.isNotEmpty(vgrCommand.getMetadata().getVersion()) &&
                !vgrCommand.getMetadata().getVersion().isBlank()) {
            commandSpec.version(vgrCommand.getMetadata().getVersion());
            commandSpec.addOption(OptionSpec
                    .builder("--version")
                    .description("Show version information and exit.")
                    .versionHelp(true)
                    .build()
            );
        }
    }

    protected void addHelpOption() {
        commandSpec.addOption(OptionSpec
                .builder("-h", "--help")
                .description("Show this help message and exit.")
                .usageHelp(true)
                .build()
        );
    }

    protected void addDebugOption() {
        commandSpec.addOption(OptionSpec
                .builder("--debug")
                .description("Show debugging output.")
                .build()
        );
    }

    protected void addQuietOption() {
        commandSpec.addOption(OptionSpec
                .builder("--quiet")
                .description("Show minimal output.")
                .build()
        );
    }

    protected void addNoInputOption() {
        commandSpec.addOption(OptionSpec
                .builder("--no-input")
                .description("Disable interactive prompts.")
                .build()
        );
    }

    @Override
    public Integer parse() {
        if (vgrCommand.getOriginalArguments() == null) {
            return 1;
        }

        formatUsageHelp();
        setSynopsis();
        setDescription();
        addHelpOption();
        addVersionOption();
        addDebugOption();
        addQuietOption();
        addNoInputOption();
        CommandLine commandLine = new CommandLine(commandSpec);
        return commandLine.execute(vgrCommand.getOriginalArguments());
    }
}
