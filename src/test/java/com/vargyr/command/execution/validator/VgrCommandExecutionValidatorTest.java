package com.vargyr.command.execution.validator;

import com.vargyr.command.execution.CommandExecution;
import com.vargyr.command.execution.error.CommandExecutionErrorManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VgrCommandExecutionValidatorTest {
    private CommandExecutionValidator validator;

    private final CommandExecutionErrorManager mockErrorManager = mock(CommandExecutionErrorManager.class);
    private final CommandExecution mockCommandExecution = mock(CommandExecution.class);

    @BeforeEach
    public void setUp() {
        validator = new VgrCommandExecutionValidator(mockCommandExecution);
        when(mockCommandExecution.getErrorManager()).thenReturn(mockErrorManager);
    }

    @Test
    public void testValidateWhenFatalErrorOccurred() {
        when(mockErrorManager.fatalErrorOccurred()).thenReturn(true);
        validator.validate();
        verify(mockErrorManager, times(1)).fatalErrorOccurred();
        Assertions.assertEquals(CommandExecutionValidationState.END, validator.getState());
    }
}
