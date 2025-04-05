package com.vargyr.command.execution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CommandExecutionStateTest {
    private CommandExecution commandExecution;

    @BeforeEach
    public void setUp() {
        commandExecution = new CommandExecution();
        commandExecution.setErrors(new ArrayList<>());
    }

    @Test
    public void testInitialProcess() {
        CommandExecutionState.INITIAL.processCurrentState(commandExecution);

        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
    }

    @Test
    public void testInitialTransitionToNextState() {
        CommandExecutionState expected = CommandExecutionState.VALIDATE_COMMAND_EXECUTION;
        CommandExecutionState actual = CommandExecutionState.INITIAL.transitionToNextState();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testEndProcess() {
        CommandExecutionState.END.processCurrentState(commandExecution);

        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
    }

    @Test
    public void testEndTransitionToNextState() {
        CommandExecutionState expected = CommandExecutionState.END;
        CommandExecutionState actual = CommandExecutionState.END.transitionToNextState();

        Assertions.assertEquals(expected, actual);
    }
}
