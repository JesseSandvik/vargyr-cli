package com.vargyr.command.parser.picocli;

import com.vargyr.command.Option;
import com.vargyr.command.PositionalParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine;
import picocli.CommandLine.Model.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PicocliCommandLineBuilderTest {

    @InjectMocks
    private PicocliCommandLineBuilder builder;

    @Mock
    private CommandSpec commandSpec;

    private final UsageMessageSpec usageMessageSpec = mock(UsageMessageSpec.class);

    @Test
    public void testFormatUsageHelp() {
        UsageMessageSpec usageMessageSpec = mock(UsageMessageSpec.class, RETURNS_DEEP_STUBS);
        when(commandSpec.usageMessage()).thenReturn(usageMessageSpec);
        when(usageMessageSpec
                .abbreviateSynopsis(eq(true))
                .parameterListHeading(anyString())
                .optionListHeading(anyString())
                .commandListHeading(anyString()))
                .thenReturn(usageMessageSpec);

        builder.formatUsageHelp();
        verify(commandSpec, times(1)).usageMessage();
    }

    @Test
    public void testSetSynopsisWhenSynopsisNull() {
        builder.setSynopsis(null);
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    public void testSetSynopsisWhenSynopsisIsEmpty() {
        builder.setSynopsis("");
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    public void testSetSynopsisWhenSynopsisIsBlank() {
        builder.setSynopsis("   ");
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    public void testSetSynopsisWhenSynopsisValid() {
        String synopsis = "test synopsis";
        when(commandSpec.usageMessage()).thenReturn(usageMessageSpec);
        builder.setSynopsis(synopsis);
        verify(commandSpec, times(1)).usageMessage();
    }

    @Test
    public void testSetDescriptionWhenDescriptionNull() {
        builder.setDescription(null);
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    public void testSetDescriptionWhenDescriptionEmpty() {
        builder.setDescription("");
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    public void testSetDescriptionWhenDescriptionBlank() {
        builder.setDescription("   ");
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    public void testSetDescriptionWhenDescriptionValid() {
        String description = "test description";
        when(commandSpec.usageMessage()).thenReturn(usageMessageSpec);
        builder.setDescription(description);
        verify(commandSpec, times(1)).usageMessage();
    }

    @Test
    public void testAddPositionalParameterWhenPositionalParameterNull() {
        builder.addPositionalParameter(null);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    public void testAddPositionalParameterWhenPositionalParameterAttributesNull() {
        PositionalParameter positionalParameter = new PositionalParameter();
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    public void testAddPositionalParameterWhenPositionalParameterLabelEmpty() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    public void testAddPositionalParameterWhenPositionalParameterLabelBlank() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("    ");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    public void testAddPositionalParameterWhenPositionalParameterSynopsisEmpty() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setSynopsis("");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    public void testAddPositionalParameterWhenPositionalParameterSynopsisBlank() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setSynopsis("     ");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    public void testAddPositionalParameterWhenPositionalParameterLabelEmptySynopsisValid() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("");
        positionalParameter.setSynopsis("Test paramA synopsis");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    public void testAddPositionalParameterWhenPositionalParameterLabelBlankSynopsisValid() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("    ");
        positionalParameter.setSynopsis("Test paramA synopsis");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    public void testAddPositionalParameterWhenPositionalParameterLabelValidSynopsisEmpty() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("testA");
        positionalParameter.setSynopsis("");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    public void testAddPositionalParameterWhenPositionalParameterLabelValidSynopsisBlank() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("testA");
        positionalParameter.setSynopsis("     ");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    public void testAddPositionalParameterWhenPositionalParameterLabelValidSynopsisValid() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("testA");
        positionalParameter.setSynopsis("Test paramA synopsis");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(1)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    public void testAddOptionWhenOptionNull() {
        builder.addOption(null);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddOptionWhenOptionAttributesNull() {
        Option option = new Option();
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddOptionWhenOptionLongNameEmpty() {
        Option option = new Option();
        option.setLongName("");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddOptionWhenOptionLongNameBlank() {
        Option option = new Option();
        option.setLongName("      ");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddOptionWhenOptionSynopsisEmpty() {
        Option option = new Option();
        option.setSynopsis("");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddOptionWhenOptionSynopsisBlank() {
        Option option = new Option();
        option.setSynopsis("      ");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddOptionWhenOptionLongNameEmptySynopsisValid() {
        Option option = new Option();
        option.setLongName("");
        option.setSynopsis("Test option-a synopsis");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddOptionWhenOptionLongNameBlankSynopsisValid() {
        Option option = new Option();
        option.setLongName("      ");
        option.setSynopsis("Test option-a synopsis");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddOptionWhenOptionLongNameValidSynopsisEmpty() {
        Option option = new Option();
        option.setLongName("option-a");
        option.setSynopsis("");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddOptionWhenOptionLongNameValidSynopsisBlank() {
        Option option = new Option();
        option.setLongName("option-a");
        option.setSynopsis("      ");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddOptionWhenOptionLongNameValidSynopsisValid() {
        Option option = new Option();
        option.setLongName("option-a");
        option.setSynopsis("Test option-a synopsis");
        builder.addOption(option);
        verify(commandSpec, times(1)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddVersionOptionWhenVersionNull() {
        builder.addVersionOption(null);
        verify(commandSpec, times(0)).version(any(String.class));
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddVersionOptionWhenVersionEmpty() {
        builder.addVersionOption("");
        verify(commandSpec, times(0)).version(any(String.class));
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddVersionOptionWhenVersionBlank() {
        builder.addVersionOption("    ");
        verify(commandSpec, times(0)).version(any(String.class));
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddVersionOptionWhenVersionValid() {
        builder.addVersionOption("1.2.3");
        verify(commandSpec, times(1)).version(any(String.class));
        verify(commandSpec, times(1)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddHelpOption() {
        builder.addHelpOption();
        verify(commandSpec, times(1)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddDebugOption() {
        builder.addDebugOption();
        verify(commandSpec, times(1)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddQuietOption() {
        builder.addQuietOption();
        verify(commandSpec, times(1)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddNoInputOption() {
        builder.addNoInputOption();
        verify(commandSpec, times(1)).addOption(any(OptionSpec.class));
    }

    @Test
    public void testAddSubcommandWhenCommandLineNull() {
        builder.addSubcommand(null);
        verify(commandSpec, times(0)).addSubcommand(any(String.class), any(CommandLine.class));
    }

    @Test
    public void testAddSubcommandWhenCommandLineNameEmpty() {
        CommandLine commandLine = new CommandLine(CommandSpec.create().name(""));
        builder.addSubcommand(commandLine);
        verify(commandSpec, times(0)).addSubcommand(any(String.class), any(CommandLine.class));
    }

    @Test
    public void testAddSubcommandWhenCommandLineNameBlank() {
        CommandLine commandLine = new CommandLine(CommandSpec.create().name("     "));
        builder.addSubcommand(commandLine);
        verify(commandSpec, times(0)).addSubcommand(any(String.class), any(CommandLine.class));
    }

    @Test
    public void testAddSubcommandWhenCommandLineNameValid() {
        String expected = "test-subcommand";
        CommandLine commandLine = new CommandLine(CommandSpec.create().name(expected));
        builder.addSubcommand(commandLine);
        verify(commandSpec, times(1)).addSubcommand(eq(expected), eq(commandLine));
    }
}
