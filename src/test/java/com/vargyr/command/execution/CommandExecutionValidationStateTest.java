package com.vargyr.command.execution;

import com.vargyr.command.VgrCommand;
import com.vargyr.command.metadata.VgrCommandMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class MockCommand extends VgrCommand {
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}

public class CommandExecutionValidationStateTest {
    private CommandExecution commandExecution;

    @BeforeEach
    public void setUp() {
        commandExecution = new CommandExecution();
    }

    @Test
    public void testInitialValidate() {
        CommandExecutionValidationState.INITIAL.validate(commandExecution);

        Assertions.assertNull(commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
        Assertions.assertNull(commandExecution.getErrors());
    }

    @Test
    public void testInitialTransitionToNextState() {
        CommandExecutionValidationState expected = CommandExecutionValidationState.VALIDATE_ORIGINAL_ARGUMENTS;
        CommandExecutionValidationState actual = CommandExecutionValidationState.INITIAL.transitionToNextState();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testValidateOriginalArgumentsValidateWhenNull() {
        commandExecution.setErrors(new ArrayList<>());
        CommandExecutionValidationState.VALIDATE_ORIGINAL_ARGUMENTS.validate(commandExecution);

        Assertions.assertEquals(CommandExecutionState.END, commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNotEquals(0, commandExecution.getExitCode());

        CommandExecutionError error = commandExecution.getErrors().getFirst();
        Assertions.assertNull(error.getOccurredState());
        Assertions.assertTrue(error.getFatal());
        Assertions.assertTrue(error.getDisplayMessage().toLowerCase().contains("original arguments"));
        Assertions.assertTrue(error.getDetails().toLowerCase().contains("original arguments"));
    }

    @Test
    public void testValidateOriginalArgumentsValidateWhenEmpty() {
        commandExecution.setOriginalArguments(new String[0]);
        commandExecution.setErrors(new ArrayList<>());
        CommandExecutionValidationState.VALIDATE_ORIGINAL_ARGUMENTS.validate(commandExecution);

        Assertions.assertNull(commandExecution.getState());
        Assertions.assertNotNull(commandExecution.getOriginalArguments());
        Assertions.assertNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
        Assertions.assertEquals(0, commandExecution.getErrors().size());
    }

    @Test
    public void testValidateOriginalArgumentsValidateWhenNotEmpty() {
        String[] originalArguments = {"Hello", ",", "World!"};
        commandExecution.setOriginalArguments(originalArguments);
        commandExecution.setErrors(new ArrayList<>());
        CommandExecutionValidationState.VALIDATE_ORIGINAL_ARGUMENTS.validate(commandExecution);

        Assertions.assertNull(commandExecution.getState());
        Assertions.assertNotNull(commandExecution.getOriginalArguments());
        Assertions.assertNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
        Assertions.assertEquals(0, commandExecution.getErrors().size());
    }

    @Test
    public void testValidateOriginalArgumentsTransitionToNextState() {
        CommandExecutionValidationState expected = CommandExecutionValidationState.VALIDATE_ROOT_COMMAND;
        CommandExecutionValidationState actual =
                CommandExecutionValidationState.VALIDATE_ORIGINAL_ARGUMENTS.transitionToNextState();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testValidateRootCommandWhenNull() {
        commandExecution.setErrors(new ArrayList<>());
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND.validate(commandExecution);

        Assertions.assertEquals(CommandExecutionState.END, commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNotEquals(0, commandExecution.getExitCode());

        CommandExecutionError error = commandExecution.getErrors().getFirst();
        Assertions.assertNull(error.getOccurredState());
        Assertions.assertTrue(error.getFatal());
        Assertions.assertTrue(error.getDisplayMessage().toLowerCase().contains("root command"));
        Assertions.assertTrue(error.getDetails().toLowerCase().contains("root command"));
    }

    @Test
    public void testValidateRootCommandWhenNotNull() {
        VgrCommand rootCommand = new MockCommand();
        commandExecution.setRootCommand(rootCommand);
        commandExecution.setErrors(new ArrayList<>());
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND.validate(commandExecution);

        Assertions.assertNull(commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNotNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
        Assertions.assertEquals(0, commandExecution.getErrors().size());
    }

    @Test
    public void testValidateRootCommandTransitionToNextState() {
        CommandExecutionValidationState expected = CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA;
        CommandExecutionValidationState actual =
                CommandExecutionValidationState.VALIDATE_ROOT_COMMAND.transitionToNextState();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testValidateRootCommandMetadataWhenNull() {
        VgrCommand rootCommand = new MockCommand();
        commandExecution.setRootCommand(rootCommand);
        commandExecution.setErrors(new ArrayList<>());
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA.validate(commandExecution);

        Assertions.assertEquals(CommandExecutionState.END, commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNotNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getRootCommand().getMetadata());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNotEquals(0, commandExecution.getExitCode());

        CommandExecutionError error = commandExecution.getErrors().getFirst();
        Assertions.assertNull(error.getOccurredState());
        Assertions.assertTrue(error.getFatal());
        Assertions.assertTrue(error.getDisplayMessage().toLowerCase().contains("metadata"));
        Assertions.assertTrue(error.getDisplayMessage().toLowerCase().contains("root command"));
        Assertions.assertTrue(error.getDetails().toLowerCase().contains("metadata"));
        Assertions.assertTrue(error.getDetails().toLowerCase().contains("root command"));
    }

    @Test
    public void testValidateRootCommandMetadataWhenNameIsNull() {
        VgrCommand rootCommand = new MockCommand();
        rootCommand.setMetadata(new VgrCommandMetadata());
        commandExecution.setRootCommand(rootCommand);
        commandExecution.setErrors(new ArrayList<>());
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA.validate(commandExecution);

        Assertions.assertEquals(CommandExecutionState.END, commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNotNull(commandExecution.getRootCommand());
        Assertions.assertNotNull(commandExecution.getRootCommand().getMetadata());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNotEquals(0, commandExecution.getExitCode());

        CommandExecutionError error = commandExecution.getErrors().getFirst();
        Assertions.assertNull(error.getOccurredState());
        Assertions.assertTrue(error.getFatal());
        Assertions.assertTrue(error.getDisplayMessage().toLowerCase().contains("metadata"));
        Assertions.assertTrue(error.getDisplayMessage().toLowerCase().contains("root command"));
        Assertions.assertTrue(error.getDetails().toLowerCase().contains("metadata"));
        Assertions.assertTrue(error.getDetails().toLowerCase().contains("root command"));
    }

    @Test
    public void testValidateRootCommandMetadataWhenNameIsEmpty() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        metadata.setName("");

        VgrCommand rootCommand = new MockCommand();
        rootCommand.setMetadata(metadata);
        commandExecution.setRootCommand(rootCommand);
        commandExecution.setErrors(new ArrayList<>());
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA.validate(commandExecution);

        Assertions.assertEquals(CommandExecutionState.END, commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNotNull(commandExecution.getRootCommand());
        Assertions.assertNotNull(commandExecution.getRootCommand().getMetadata());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNotEquals(0, commandExecution.getExitCode());

        CommandExecutionError error = commandExecution.getErrors().getFirst();
        Assertions.assertNull(error.getOccurredState());
        Assertions.assertTrue(error.getFatal());
        Assertions.assertTrue(error.getDisplayMessage().toLowerCase().contains("metadata"));
        Assertions.assertTrue(error.getDisplayMessage().toLowerCase().contains("root command"));
        Assertions.assertTrue(error.getDetails().toLowerCase().contains("metadata"));
        Assertions.assertTrue(error.getDetails().toLowerCase().contains("root command"));
    }

    @Test
    public void testValidateRootCommandMetadataWhenNameIsBlank() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        metadata.setName("     ");

        VgrCommand rootCommand = new MockCommand();
        rootCommand.setMetadata(metadata);
        commandExecution.setRootCommand(rootCommand);
        commandExecution.setErrors(new ArrayList<>());
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA.validate(commandExecution);

        Assertions.assertEquals(CommandExecutionState.END, commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNotNull(commandExecution.getRootCommand());
        Assertions.assertNotNull(commandExecution.getRootCommand().getMetadata());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNotEquals(0, commandExecution.getExitCode());

        CommandExecutionError error = commandExecution.getErrors().getFirst();
        Assertions.assertNull(error.getOccurredState());
        Assertions.assertTrue(error.getFatal());
        Assertions.assertTrue(error.getDisplayMessage().toLowerCase().contains("metadata"));
        Assertions.assertTrue(error.getDisplayMessage().toLowerCase().contains("root command"));
        Assertions.assertTrue(error.getDetails().toLowerCase().contains("metadata"));
        Assertions.assertTrue(error.getDetails().toLowerCase().contains("root command"));
    }

    @Test
    public void testValidateRootCommandMetadata() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        metadata.setName("test command");

        VgrCommand rootCommand = new MockCommand();
        rootCommand.setMetadata(metadata);
        commandExecution.setRootCommand(rootCommand);
        commandExecution.setErrors(new ArrayList<>());
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA.validate(commandExecution);

        Assertions.assertNull(commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNotNull(commandExecution.getRootCommand());
        Assertions.assertNotNull(commandExecution.getRootCommand().getMetadata());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
        Assertions.assertEquals(0, commandExecution.getErrors().size());
    }

    @Test
    public void testValidateRootCommandMetadataTransitionToNextState() {
        CommandExecutionValidationState expected = CommandExecutionValidationState.END;
        CommandExecutionValidationState actual =
                CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA.transitionToNextState();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testValidateEndTransitionToNextState() {
        CommandExecutionValidationState expected = CommandExecutionValidationState.END;
        CommandExecutionValidationState actual =
                CommandExecutionValidationState.END.transitionToNextState();
        Assertions.assertEquals(expected, actual);
    }
}
