package com.vargyr;

import com.vargyr.command.Option;
import com.vargyr.command.PositionalParameter;
import com.vargyr.command.VgrCommand;
import com.vargyr.command.executable.VgrExecutableCommand;
import com.vargyr.command.metadata.VgrCommandMetadata;
import com.vargyr.command.orchestrator.CommandOrchestrator;
import com.vargyr.testing.TestCommand;
import com.vargyr.testing.TestCommand2;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        metadata.setName("testing");
        metadata.setVersion("1.0.2");
        metadata.setDescription("Testing a fake command.");
        metadata.setSynopsis("A fake command.");

        VgrExecutableCommand command = new TestCommand();
        command.setMetadata(metadata);

//        List<PositionalParameter> positionalParameters = new ArrayList<>();
//        PositionalParameter p = new PositionalParameter(
//                "abcd",
//                "A test positional parameter.",
//                null
//        );
//        positionalParameters.add(p);
//        command.setPositionalParameters(positionalParameters);

        List<Option> options = new ArrayList<>();
        Option o = new Option(
                "--test-opt-a",
                "a",
                "A test option.",
                "<labelA>",
                null
        );
        options.add(o);
        command.setOptions(options);

        List<VgrCommand> subcommands = new ArrayList<>();
        VgrCommand subcommand = new TestCommand2();
        VgrCommandMetadata m2 = new VgrCommandMetadata();
        m2.setName("subcommand-a");
        m2.setVersion("1.2.3");
        m2.setSynopsis("Subcommand synopsis");
        m2.setDescription("Subcommand description");
        m2.setExecutesWithoutArguments(false);
        subcommand.setMetadata(m2);
        subcommands.add(subcommand);
        command.setSubcommands(subcommands);

        command.setOriginalArguments(args);
        CommandOrchestrator processor = new CommandOrchestrator(command);
        int exitCode = processor.run();
        System.exit(exitCode);
    }
}
