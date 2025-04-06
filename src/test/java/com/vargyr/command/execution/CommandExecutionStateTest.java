package com.vargyr.command.execution;

import com.vargyr.command.VgrCommand;
import com.vargyr.command.execution.error.CommandExecutionErrorManager;
import com.vargyr.command.execution.validator.CommandExecutionValidator;
import com.vargyr.command.execution.validator.VgrCommandExecutionValidator;
import com.vargyr.command.parser.CommandLineParser;
import com.vargyr.command.parser.picocli.PicocliCommandLineParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandExecutionStateTest {
    private CommandExecution commandExecution;

    private final CommandExecutionErrorManager mockErrorManager = mock(CommandExecutionErrorManager.class);
    private final VgrCommand mockCommand = mock(VgrCommand.class);

    @BeforeEach
    public void setUp() {
        commandExecution = new CommandExecution();
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
    public void testValidateCommandExecutionProcess() {
        CommandExecutionValidator validator = mock(VgrCommandExecutionValidator.class);
        doNothing().when(validator).validate();
        commandExecution.setValidator(validator);

        CommandExecutionState.VALIDATE_COMMAND_EXECUTION.processCurrentState(commandExecution);
        verify(validator, times(1)).validate();
    }

    @Test
    public void testValidateCommandExecutionTransitionToNextState() {
        CommandExecutionState expected = CommandExecutionState.PARSE_COMMAND_LINE;
        CommandExecutionState actual = CommandExecutionState.VALIDATE_COMMAND_EXECUTION.transitionToNextState();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testParseCommandLineProcess() {
        CommandLineParser parser = mock(PicocliCommandLineParser.class);
        doNothing().when(parser).parse(eq(commandExecution));
        commandExecution.setParser(parser);

        CommandExecutionState.PARSE_COMMAND_LINE.processCurrentState(commandExecution);
        verify(parser, times(1)).parse(eq(commandExecution));
    }

    @Test
    public void testParseCommandLineTransitionToNextState() {
        CommandExecutionState expected = CommandExecutionState.CALL_INVOKED_COMMAND;
        CommandExecutionState actual = CommandExecutionState.PARSE_COMMAND_LINE.transitionToNextState();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testCallInvokedCommandProcessWhenException() throws Exception {
        doNothing().when(mockErrorManager).addError(anyString());
        commandExecution.setErrorManager(mockErrorManager);

        when(mockCommand.call()).thenThrow(new Exception("error message"));
        commandExecution.setInvokedCommand(mockCommand);
        commandExecution.setState(CommandExecutionState.CALL_INVOKED_COMMAND);

        CommandExecutionState.CALL_INVOKED_COMMAND.processCurrentState(commandExecution);
        verify(mockErrorManager, times(1)).addError(anyString());
        verify(mockCommand, times(1)).call();
    }

    @Test
    public void testCallInvokedCommandProcess() throws Exception {
        when(mockCommand.call()).thenReturn(0);
        commandExecution.setInvokedCommand(mockCommand);

        CommandExecutionState.CALL_INVOKED_COMMAND.processCurrentState(commandExecution);
        verify(mockCommand, times(1)).call();
    }

    @Test
    public void testCallInvokedCommandTransitionToNextState() {
        CommandExecutionState expected = CommandExecutionState.END;
        CommandExecutionState actual = CommandExecutionState.CALL_INVOKED_COMMAND.transitionToNextState();

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
