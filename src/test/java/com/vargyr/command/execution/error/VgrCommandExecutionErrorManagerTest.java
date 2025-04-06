package com.vargyr.command.execution.error;

import com.vargyr.command.execution.CommandExecution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VgrCommandExecutionErrorManagerTest {
    private CommandExecutionErrorManager errorManager;

    private final CommandExecution mockCommandExecution = mock(CommandExecution.class);

    @BeforeEach
    public void setUp() {
        this.errorManager = new VgrCommandExecutionErrorManager(mockCommandExecution);
    }

    @Test
    public void testFatalErrorOccurredWhenErrorsEmpty() {
        Assertions.assertEquals(0, errorManager.getErrors().size());
        Assertions.assertFalse(errorManager.fatalErrorOccurred());
    }

    @Test
    public void testFatalErrorOccurredWhenError() {
        errorManager.addError("test error");
        Assertions.assertFalse(errorManager.fatalErrorOccurred());
    }

    @Test
    public void testFatalErrorOccurredWhenFatal() {
        errorManager.addFatalError("test fatal error");
        Assertions.assertTrue(errorManager.fatalErrorOccurred());
    }
}
