package com.vargyr.command.parser.picocli;

import com.vargyr.command.PositionalParameter;
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

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PicocliCommandLineParserTest {
    private CommandLineParser parser;

    private final CommandExecution mockCommandExecution = mock(CommandExecution.class);
    private final CommandExecutionErrorManager mockErrorManager = mock(CommandExecutionErrorManager.class);

    private final VgrCommand mockCommand = mock(VgrCommand.class);
    private final VgrCommandMetadata mockMetadata = mock(VgrCommandMetadata.class);
    private final String mockCommandName = "test-command";

    private final VgrCommand mockSubcommand = mock(VgrCommand.class);
    private final VgrCommandMetadata mockSubcommandMetadata = mock(VgrCommandMetadata.class);
    private final String mockSubcommandName = "test-subcommand";

    private final VgrCommand mockNestedSubcommand = mock(VgrCommand.class);
    private final VgrCommandMetadata mockNestedSubcommandMetadata = mock(VgrCommandMetadata.class);
    private final String mockNestedSubcommandName = "test-nested-subcommand";

    private final PositionalParameter mockPositionalParameter = mock(PositionalParameter.class);
    private final String mockPositionalParameterLabel = "test-a";
    private final String mockPositionalParameterSynopsis = "A test positional parameter.";
    private final String mockPositionalParameterValue = "value a";

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

    @Test
    public void testParseWhenCommandNotFound() {
        doNothing().when(mockErrorManager).addFatalError(anyString());
        when(mockCommandExecution.getErrorManager()).thenReturn(mockErrorManager);

        String[] arguments = {"bad-command"};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);

        parser.parse();
        verify(mockErrorManager, times(1)).addFatalError(anyString());
    }

    @Test
    public void testParseWhenNoArgumentsAndCommandExecutesWithoutArguments() {
        when(mockMetadata.getName()).thenReturn(mockCommandName);
        when(mockMetadata.getExecutesWithoutArguments()).thenReturn(true);

        String[] arguments = {};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);
        when(mockCommandExecution.getRootCommand()).thenReturn(mockCommand);
        doNothing().when(mockCommandExecution).setInvokedCommand(eq(mockCommand));

        parser.parse();
        verify(mockCommandExecution, times(1)).setInvokedCommand(eq(mockCommand));
    }

    @Test
    public void testParseWhenNoArgumentsAndCommandDoesNotExecuteWithoutArguments() {
        when(mockMetadata.getName()).thenReturn(mockCommandName);
        when(mockMetadata.getExecutesWithoutArguments()).thenReturn(false);

        String[] arguments = {};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);
        doNothing().when(mockCommandExecution).setState(eq(CommandExecutionState.END));
        doNothing().when(mockCommandExecution).setExitCode(eq(0));

        parser.parse();
        verify(mockCommandExecution, times(1)).setState(eq(CommandExecutionState.END));
        verify(mockCommandExecution, times(1)).setExitCode(eq(0));
    }

    @Test
    public void testParseWhenPositionalParameter() {
        when(mockMetadata.getName()).thenReturn(mockCommandName);
        when(mockCommand.getPositionalParameters()).thenReturn(List.of(mockPositionalParameter));

        when(mockPositionalParameter.getLabel()).thenReturn(mockPositionalParameterLabel);
        when(mockPositionalParameter.getSynopsis()).thenReturn(mockPositionalParameterSynopsis);
        doNothing().when(mockPositionalParameter).setValue(eq(mockPositionalParameterValue));

        String[] arguments = {mockPositionalParameterValue};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);
        when(mockCommandExecution.getRootCommand()).thenReturn(mockCommand);
        doNothing().when(mockCommandExecution).setInvokedCommand(eq(mockCommand));

        parser.parse();
        verify(mockCommandExecution, times(1)).setInvokedCommand(eq(mockCommand));
        verify(mockPositionalParameter, times(1)).setValue(eq(mockPositionalParameterValue));
    }

    @Test
    public void testParseWhenSubcommandWithNoArgumentsAndSubcommandExecutesWithoutArguments() {
        when(mockMetadata.getName()).thenReturn(mockCommandName);
        when(mockCommand.getSubcommands()).thenReturn(List.of(mockSubcommand));

        when(mockSubcommand.getMetadata()).thenReturn(mockSubcommandMetadata);
        when(mockSubcommandMetadata.getName()).thenReturn(mockSubcommandName);
        when(mockSubcommandMetadata.getExecutesWithoutArguments()).thenReturn(true);

        when(mockCommandExecution.getRootCommand()).thenReturn(mockCommand);
        doNothing().when(mockCommandExecution).setInvokedCommand(eq(mockSubcommand));

        String[] arguments = {mockSubcommandName};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);

        parser.parse();
        verify(mockCommandExecution, times(1)).setInvokedCommand(eq(mockSubcommand));
    }

    @Test
    public void testParseWhenSubcommandWithNoArgumentsAndSubcommandDoesNotExecuteWithoutArguments() {
        when(mockMetadata.getName()).thenReturn(mockCommandName);
        when(mockCommand.getSubcommands()).thenReturn(List.of(mockSubcommand));

        when(mockSubcommand.getMetadata()).thenReturn(mockSubcommandMetadata);
        when(mockSubcommandMetadata.getName()).thenReturn(mockSubcommandName);
        when(mockSubcommandMetadata.getExecutesWithoutArguments()).thenReturn(false);

        String[] arguments = {mockSubcommandName};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);
        doNothing().when(mockCommandExecution).setState(eq(CommandExecutionState.END));
        doNothing().when(mockCommandExecution).setExitCode(eq(0));

        parser.parse();
        verify(mockCommandExecution, times(1)).setState(eq(CommandExecutionState.END));
        verify(mockCommandExecution, times(1)).setExitCode(eq(0));
    }

    @Test
    public void testParseWhenSubcommandWithPositionalParameter() {
        when(mockMetadata.getName()).thenReturn(mockCommandName);
        when(mockCommand.getSubcommands()).thenReturn(List.of(mockSubcommand));

        when(mockSubcommand.getMetadata()).thenReturn(mockSubcommandMetadata);
        when(mockSubcommandMetadata.getName()).thenReturn(mockSubcommandName);
        when(mockSubcommand.getPositionalParameters()).thenReturn(List.of(mockPositionalParameter));

        when(mockPositionalParameter.getLabel()).thenReturn(mockPositionalParameterLabel);
        when(mockPositionalParameter.getSynopsis()).thenReturn(mockPositionalParameterSynopsis);
        doNothing().when(mockPositionalParameter).setValue(eq(mockPositionalParameterValue));

        String[] arguments = {mockSubcommandName, mockPositionalParameterValue};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);
        when(mockCommandExecution.getRootCommand()).thenReturn(mockCommand);
        doNothing().when(mockCommandExecution).setInvokedCommand(eq(mockSubcommand));

        parser.parse();
        verify(mockCommandExecution, times(1)).setInvokedCommand(eq(mockSubcommand));
        verify(mockPositionalParameter, times(1)).setValue(eq(mockPositionalParameterValue));
    }

    @Test
    public void testParseWhenNestedSubcommandWithNoArgumentsAndNestedSubcommandExecutesWithoutArguments() {
        when(mockMetadata.getName()).thenReturn(mockCommandName);
        when(mockCommand.getSubcommands()).thenReturn(List.of(mockSubcommand));

        when(mockSubcommand.getMetadata()).thenReturn(mockSubcommandMetadata);
        when(mockSubcommandMetadata.getName()).thenReturn(mockSubcommandName);
        when(mockSubcommand.getSubcommands()).thenReturn(List.of(mockNestedSubcommand));

        when(mockNestedSubcommand.getMetadata()).thenReturn(mockNestedSubcommandMetadata);
        when(mockNestedSubcommandMetadata.getName()).thenReturn(mockNestedSubcommandName);
        when(mockNestedSubcommandMetadata.getExecutesWithoutArguments()).thenReturn(true);

        when(mockCommandExecution.getRootCommand()).thenReturn(mockCommand);
        doNothing().when(mockCommandExecution).setInvokedCommand(eq(mockNestedSubcommand));

        String[] arguments = {mockSubcommandName, mockNestedSubcommandName};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);

        parser.parse();
        verify(mockCommandExecution, times(1)).setInvokedCommand(eq(mockNestedSubcommand));
    }

    @Test
    public void testParseWhenNestedSubcommandWithNoArgumentsAndNestedSubcommandDoesNotExecuteWithoutArguments() {
        when(mockMetadata.getName()).thenReturn(mockCommandName);
        when(mockCommand.getSubcommands()).thenReturn(List.of(mockSubcommand));

        when(mockSubcommand.getMetadata()).thenReturn(mockSubcommandMetadata);
        when(mockSubcommandMetadata.getName()).thenReturn(mockSubcommandName);
        when(mockSubcommand.getSubcommands()).thenReturn(List.of(mockNestedSubcommand));

        when(mockNestedSubcommand.getMetadata()).thenReturn(mockNestedSubcommandMetadata);
        when(mockNestedSubcommandMetadata.getName()).thenReturn(mockNestedSubcommandName);
        when(mockNestedSubcommandMetadata.getExecutesWithoutArguments()).thenReturn(false);

        String[] arguments = {mockSubcommandName, mockNestedSubcommandName};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);
        doNothing().when(mockCommandExecution).setState(eq(CommandExecutionState.END));
        doNothing().when(mockCommandExecution).setExitCode(eq(0));

        parser.parse();
        verify(mockCommandExecution, times(1)).setState(eq(CommandExecutionState.END));
        verify(mockCommandExecution, times(1)).setExitCode(eq(0));
    }

    @Test
    public void testParseWhenNestedSubcommandWithPositionalParameter() {
        when(mockMetadata.getName()).thenReturn(mockCommandName);
        when(mockCommand.getSubcommands()).thenReturn(List.of(mockSubcommand));

        when(mockSubcommand.getMetadata()).thenReturn(mockSubcommandMetadata);
        when(mockSubcommandMetadata.getName()).thenReturn(mockSubcommandName);
        when(mockSubcommand.getSubcommands()).thenReturn(List.of(mockNestedSubcommand));

        when(mockNestedSubcommand.getMetadata()).thenReturn(mockNestedSubcommandMetadata);
        when(mockNestedSubcommandMetadata.getName()).thenReturn(mockNestedSubcommandName);
        when(mockNestedSubcommand.getPositionalParameters()).thenReturn(List.of(mockPositionalParameter));

        when(mockPositionalParameter.getLabel()).thenReturn(mockPositionalParameterLabel);
        when(mockPositionalParameter.getSynopsis()).thenReturn(mockPositionalParameterSynopsis);
        doNothing().when(mockPositionalParameter).setValue(eq(mockPositionalParameterValue));

        String[] arguments = {mockSubcommandName, mockNestedSubcommandName, mockPositionalParameterValue};
        when(mockCommandExecution.getOriginalArguments()).thenReturn(arguments);
        when(mockCommandExecution.getRootCommand()).thenReturn(mockCommand);
        doNothing().when(mockCommandExecution).setInvokedCommand(eq(mockNestedSubcommand));

        parser.parse();
        verify(mockCommandExecution, times(1)).setInvokedCommand(eq(mockNestedSubcommand));
        verify(mockPositionalParameter, times(1)).setValue(eq(mockPositionalParameterValue));
    }
}
