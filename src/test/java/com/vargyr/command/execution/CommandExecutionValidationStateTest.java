package com.vargyr.command.execution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void testValidateRootCommandTransitionToNextState() {
        CommandExecutionValidationState expected = CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA;
        CommandExecutionValidationState actual =
                CommandExecutionValidationState.VALIDATE_ROOT_COMMAND.transitionToNextState();
        Assertions.assertEquals(expected, actual);
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
