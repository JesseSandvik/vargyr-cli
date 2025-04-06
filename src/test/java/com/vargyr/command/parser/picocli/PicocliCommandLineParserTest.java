package com.vargyr.command.parser.picocli;

import com.vargyr.command.VgrCommand;
import com.vargyr.command.execution.CommandExecution;
import com.vargyr.command.execution.CommandExecutionState;
import com.vargyr.command.execution.error.CommandExecutionErrorManager;
import com.vargyr.command.metadata.VgrCommandMetadata;
import com.vargyr.command.parser.CommandLineParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PicocliCommandLineParserTest {
    private CommandLineParser parser;

    private final CommandExecution mockCommandExecution = mock(CommandExecution.class);
    private final CommandExecutionErrorManager mockErrorManager = mock(CommandExecutionErrorManager.class);
    private final VgrCommand mockCommand = mock(VgrCommand.class);
    private final VgrCommandMetadata mockMetadata = mock(VgrCommandMetadata.class);

    @BeforeEach
    public void setUp() {
        this.parser = new PicocliCommandLineParser(mockCommandExecution);
        when(mockCommand.getMetadata()).thenReturn(mockMetadata);
        when(mockCommandExecution.getRootCommand()).thenReturn(mockCommand);
    }

    @Test
    public void testParseWhenUsageHelpLongOption() {
        String[] arguments = {"--help"};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);
        doNothing().when(mockCommandExecution).setState(eq(CommandExecutionState.END));
        doNothing().when(mockCommandExecution).setExitCode(eq(0));
        parser.parse();
        verify(mockCommandExecution, times(1)).setState(eq(CommandExecutionState.END));
        verify(mockCommandExecution, times(1)).setExitCode(eq(0));
    }

    @Test
    public void testParseWhenUsageHelpShortOption() {
        String[] arguments = {"-h"};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);
        doNothing().when(mockCommandExecution).setState(eq(CommandExecutionState.END));
        doNothing().when(mockCommandExecution).setExitCode(eq(0));
        parser.parse();
        verify(mockCommandExecution, times(1)).setState(eq(CommandExecutionState.END));
        verify(mockCommandExecution, times(1)).setExitCode(eq(0));
    }

    @Test
    public void testParseWhenVersionHelpOptionVersionNull() {
        doNothing().when(mockErrorManager).addFatalError(anyString());
        when(mockCommandExecution.getErrorManager()).thenReturn(mockErrorManager);

        String[] arguments = {"--version"};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);

        parser.parse();
        verify(mockErrorManager, times(1)).addFatalError(anyString());
    }

    @Test
    public void testParseWhenVersionHelpOptionVersionEmpty() {
        when(mockMetadata.getVersion()).thenReturn("");

        doNothing().when(mockErrorManager).addFatalError(anyString());
        when(mockCommandExecution.getErrorManager()).thenReturn(mockErrorManager);

        String[] arguments = {"--version"};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);

        parser.parse();
        verify(mockErrorManager, times(1)).addFatalError(anyString());
    }

    @Test
    public void testParseWhenVersionHelpOptionVersionBlank() {
        when(mockMetadata.getVersion()).thenReturn("      ");

        doNothing().when(mockErrorManager).addFatalError(anyString());
        when(mockCommandExecution.getErrorManager()).thenReturn(mockErrorManager);

        String[] arguments = {"--version"};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);

        parser.parse();
        verify(mockErrorManager, times(1)).addFatalError(anyString());
    }

    @Test
    public void testParseWhenVersionHelpOptionVersion() {
        when(mockMetadata.getVersion()).thenReturn("1.2.3");

        String[] arguments = {"--version"};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);
        doNothing().when(mockCommandExecution).setState(eq(CommandExecutionState.END));
        doNothing().when(mockCommandExecution).setExitCode(eq(0));
        parser.parse();
        verify(mockCommandExecution, times(1)).setState(eq(CommandExecutionState.END));
        verify(mockCommandExecution, times(1)).setExitCode(eq(0));
    }
}
