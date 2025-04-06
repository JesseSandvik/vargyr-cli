package com.vargyr.command.execution.validator;

import com.vargyr.command.VgrCommand;
import com.vargyr.command.execution.CommandExecution;
import com.vargyr.command.execution.error.CommandExecutionErrorManager;
import com.vargyr.command.metadata.VgrCommandMetadata;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandExecutionValidationStateTest {
    private CommandExecution commandExecution;

    private final CommandExecutionErrorManager mockErrorManager = mock(CommandExecutionErrorManager.class);
    private final VgrCommand mockCommand = mock(VgrCommand.class);
    private final VgrCommandMetadata mockMetadata = mock(VgrCommandMetadata.class);

    @BeforeEach
    public void setUp() {
        commandExecution = new CommandExecution();
        commandExecution.setErrorManager(mockErrorManager);
    }

    @Test
    public void testInitialValidate() {
        CommandExecutionValidationState.INITIAL.validate(commandExecution);

        Assertions.assertNull(commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
    }

    @Test
    public void testInitialTransitionToNextState() {
        CommandExecutionValidationState expected = CommandExecutionValidationState.VALIDATE_ORIGINAL_ARGUMENTS;
        CommandExecutionValidationState actual = CommandExecutionValidationState.INITIAL.transitionToNextState();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testValidateOriginalArgumentsValidateWhenNull() {
        doNothing().when(mockErrorManager).addFatalError(anyString());
        CommandExecutionValidationState.VALIDATE_ORIGINAL_ARGUMENTS.validate(commandExecution);
        verify(mockErrorManager, times(1)).addFatalError(anyString());
    }

    @Test
    public void testValidateOriginalArgumentsValidateWhenEmpty() {
        commandExecution.setOriginalArguments(new String[0]);
        CommandExecutionValidationState.VALIDATE_ORIGINAL_ARGUMENTS.validate(commandExecution);

        Assertions.assertNull(commandExecution.getState());
        Assertions.assertNotNull(commandExecution.getOriginalArguments());
        Assertions.assertNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
    }

    @Test
    public void testValidateOriginalArgumentsValidateWhenNotEmpty() {
        String[] originalArguments = {"Hello", ",", "World!"};
        commandExecution.setOriginalArguments(originalArguments);
        CommandExecutionValidationState.VALIDATE_ORIGINAL_ARGUMENTS.validate(commandExecution);

        Assertions.assertNull(commandExecution.getState());
        Assertions.assertNotNull(commandExecution.getOriginalArguments());
        Assertions.assertNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
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
        doNothing().when(mockErrorManager).addFatalError(anyString());
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND.validate(commandExecution);
        verify(mockErrorManager, times(1)).addFatalError(anyString());
    }

    @Test
    public void testValidateRootCommandWhenNotNull() {
        commandExecution.setRootCommand(mockCommand);
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND.validate(commandExecution);

        Assertions.assertNull(commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNotNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
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
        commandExecution.setRootCommand(mockCommand);
        doNothing().when(mockErrorManager).addFatalError(anyString());
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA.validate(commandExecution);
        verify(mockErrorManager, times(1)).addFatalError(anyString());
    }

    @Test
    public void testValidateRootCommandMetadataWhenNameIsNull() {
        when(mockCommand.getMetadata()).thenReturn(mockMetadata);
        commandExecution.setRootCommand(mockCommand);
        doNothing().when(mockErrorManager).addFatalError(anyString());
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA.validate(commandExecution);
        verify(mockErrorManager, times(1)).addFatalError(anyString());
    }

    @Test
    public void testValidateRootCommandMetadataWhenNameIsEmpty() {
        when(mockMetadata.getName()).thenReturn("");
        when(mockCommand.getMetadata()).thenReturn(mockMetadata);
        commandExecution.setRootCommand(mockCommand);
        doNothing().when(mockErrorManager).addFatalError(anyString());
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA.validate(commandExecution);
        verify(mockErrorManager, times(1)).addFatalError(anyString());
    }

    @Test
    public void testValidateRootCommandMetadataWhenNameIsBlank() {
        when(mockMetadata.getName()).thenReturn("     ");
        when(mockCommand.getMetadata()).thenReturn(mockMetadata);
        commandExecution.setRootCommand(mockCommand);
        doNothing().when(mockErrorManager).addFatalError(anyString());
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA.validate(commandExecution);
        verify(mockErrorManager, times(1)).addFatalError(anyString());
    }

    @Test
    public void testValidateRootCommandMetadata() {
        when(mockMetadata.getName()).thenReturn("test command");
        when(mockCommand.getMetadata()).thenReturn(mockMetadata);
        commandExecution.setRootCommand(mockCommand);
        CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA.validate(commandExecution);

        Assertions.assertNull(commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNotNull(commandExecution.getRootCommand());
        Assertions.assertNotNull(commandExecution.getRootCommand().getMetadata());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
    }

    @Test
    public void testValidateRootCommandMetadataTransitionToNextState() {
        CommandExecutionValidationState expected = CommandExecutionValidationState.END;
        CommandExecutionValidationState actual =
                CommandExecutionValidationState.VALIDATE_ROOT_COMMAND_METADATA.transitionToNextState();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testEndValidate() {
        CommandExecutionValidationState.END.validate(commandExecution);

        Assertions.assertNull(commandExecution.getState());
        Assertions.assertNull(commandExecution.getOriginalArguments());
        Assertions.assertNull(commandExecution.getRootCommand());
        Assertions.assertNull(commandExecution.getInvokedCommand());
        Assertions.assertNull(commandExecution.getExitCode());
    }

    @Test
    public void testValidateEndTransitionToNextState() {
        CommandExecutionValidationState expected = CommandExecutionValidationState.END;
        CommandExecutionValidationState actual =
                CommandExecutionValidationState.END.transitionToNextState();
        Assertions.assertEquals(expected, actual);
    }
}
