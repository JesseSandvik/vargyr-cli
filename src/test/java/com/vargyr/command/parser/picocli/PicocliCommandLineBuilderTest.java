package com.vargyr.command.parser.picocli;

import com.vargyr.command.Option;
import com.vargyr.command.PositionalParameter;
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
    void testSetSynopsisWhenSynopsisNull() {
        builder.setSynopsis(null);
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetSynopsisWhenSynopsisIsEmpty() {
        builder.setSynopsis("");
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetSynopsisWhenSynopsisIsBlank() {
        builder.setSynopsis("   ");
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetSynopsisWhenSynopsisValid() {
        String synopsis = "test synopsis";
        when(commandSpec.usageMessage()).thenReturn(usageMessageSpec);
        builder.setSynopsis(synopsis);
        verify(commandSpec, times(1)).usageMessage();
    }

    @Test
    void testSetDescriptionWhenDescriptionNull() {
        builder.setDescription(null);
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetDescriptionWhenDescriptionEmpty() {
        builder.setDescription("");
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetDescriptionWhenDescriptionBlank() {
        builder.setDescription("   ");
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetDescriptionWhenDescriptionValid() {
        String description = "test description";
        when(commandSpec.usageMessage()).thenReturn(usageMessageSpec);
        builder.setDescription(description);
        verify(commandSpec, times(1)).usageMessage();
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterNull() {
        builder.addPositionalParameter(null);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterAttributesNull() {
        PositionalParameter positionalParameter = new PositionalParameter();
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterLabelEmpty() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterLabelBlank() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("    ");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterSynopsisEmpty() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setSynopsis("");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterSynopsisBlank() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setSynopsis("     ");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterLabelEmptySynopsisValid() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("");
        positionalParameter.setSynopsis("Test paramA synopsis");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterLabelBlankSynopsisValid() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("    ");
        positionalParameter.setSynopsis("Test paramA synopsis");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterLabelValidSynopsisEmpty() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("testA");
        positionalParameter.setSynopsis("");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterLabelValidSynopsisBlank() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("testA");
        positionalParameter.setSynopsis("     ");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterLabelValidSynopsisValid() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("testA");
        positionalParameter.setSynopsis("Test paramA synopsis");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(1)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddOptionWhenOptionNull() {
        builder.addOption(null);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddOptionWhenOptionAttributesNull() {
        Option option = new Option();
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddOptionWhenOptionLongNameEmpty() {
        Option option = new Option();
        option.setLongName("");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddOptionWhenOptionLongNameBlank() {
        Option option = new Option();
        option.setLongName("      ");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddOptionWhenOptionSynopsisEmpty() {
        Option option = new Option();
        option.setSynopsis("");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddOptionWhenOptionSynopsisBlank() {
        Option option = new Option();
        option.setSynopsis("      ");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddOptionWhenOptionLongNameEmptySynopsisValid() {
        Option option = new Option();
        option.setLongName("");
        option.setSynopsis("Test option-a synopsis");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddOptionWhenOptionLongNameBlankSynopsisValid() {
        Option option = new Option();
        option.setLongName("      ");
        option.setSynopsis("Test option-a synopsis");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddOptionWhenOptionLongNameValidSynopsisEmpty() {
        Option option = new Option();
        option.setLongName("option-a");
        option.setSynopsis("");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddOptionWhenOptionLongNameValidSynopsisBlank() {
        Option option = new Option();
        option.setLongName("option-a");
        option.setSynopsis("      ");
        builder.addOption(option);
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddOptionWhenOptionLongNameValidSynopsisValid() {
        Option option = new Option();
        option.setLongName("option-a");
        option.setSynopsis("Test option-a synopsis");
        builder.addOption(option);
        verify(commandSpec, times(1)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddVersionOptionWhenVersionNull() {
        builder.addVersionOption(null);
        verify(commandSpec, times(0)).version(any(String.class));
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddVersionOptionWhenVersionEmpty() {
        builder.addVersionOption("");
        verify(commandSpec, times(0)).version(any(String.class));
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddVersionOptionWhenVersionBlank() {
        builder.addVersionOption("    ");
        verify(commandSpec, times(0)).version(any(String.class));
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddVersionOptionWhenVersionValid() {
        builder.addVersionOption("1.2.3");
        verify(commandSpec, times(1)).version(any(String.class));
        verify(commandSpec, times(1)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddSubcommandWhenCommandLineNull() {
        builder.addSubcommand(null);
        verify(commandSpec, times(0)).addSubcommand(any(String.class), any(CommandLine.class));
    }

    @Test
    void testAddSubcommandWhenCommandLineNameEmpty() {
        CommandLine commandLine = new CommandLine(CommandSpec.create().name(""));
        builder.addSubcommand(commandLine);
        verify(commandSpec, times(0)).addSubcommand(any(String.class), any(CommandLine.class));
    }

    @Test
    void testAddSubcommandWhenCommandLineNameBlank() {
        CommandLine commandLine = new CommandLine(CommandSpec.create().name("     "));
        builder.addSubcommand(commandLine);
        verify(commandSpec, times(0)).addSubcommand(any(String.class), any(CommandLine.class));
    }

    @Test
    void testAddSubcommandWhenCommandLineNameValid() {
        String expected = "test-subcommand";
        CommandLine commandLine = new CommandLine(CommandSpec.create().name(expected));
        builder.addSubcommand(commandLine);
        verify(commandSpec, times(1)).addSubcommand(eq(expected), eq(commandLine));
    }
}
