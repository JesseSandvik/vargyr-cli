package com.vargyr.command.execution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandExecutionValidationStateTest {
    private CommandExecutionValidationState commandExecutionValidationState;
    private CommandExecution commandExecution;

    @BeforeEach
    public void setUp() {
        commandExecution = new CommandExecution();
    }

    @Test
    public void testInitialTransitionToNextState() {
        CommandExecutionValidationState expected = CommandExecutionValidationState.VALIDATE_ORIGINAL_ARGUMENTS;
        CommandExecutionValidationState actual = CommandExecutionValidationState.INITIAL.transitionToNextState();
        Assertions.assertEquals(expected, actual);
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
