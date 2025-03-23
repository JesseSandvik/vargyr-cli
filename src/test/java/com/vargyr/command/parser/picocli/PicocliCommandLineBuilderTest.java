package com.vargyr.command.parser.picocli;

import com.vargyr.command.PositionalParameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    void testSetSynopsisWhenSynopsisIsNull() {
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
    void testSetSynopsis() {
        String synopsis = "test synopsis";
        when(commandSpec.usageMessage()).thenReturn(usageMessageSpec);
        builder.setSynopsis(synopsis);
        verify(commandSpec, times(1)).usageMessage();
    }

    @Test
    void testSetDescriptionWhenDescriptionIsNull() {
        builder.setDescription(null);
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetDescriptionWhenDescriptionIsEmpty() {
        builder.setDescription("");
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetDescriptionWhenDescriptionIsBlank() {
        builder.setDescription("   ");
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetDescription() {
        String description = "test description";
        when(commandSpec.usageMessage()).thenReturn(usageMessageSpec);
        builder.setDescription(description);
        verify(commandSpec, times(1)).usageMessage();
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterIsNull() {
        builder.addPositionalParameter(null);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterAttributesAreNull() {
        PositionalParameter positionalParameter = new PositionalParameter();
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterLabelIsEmpty() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterLabelIsBlank() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("    ");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterSynopsisIsEmpty() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setSynopsis("");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterSynopsisIsBlank() {
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
    void testAddPositionalParameterWhenPositionalParameterLabelValidSynopsisIsEmpty() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("testA");
        positionalParameter.setSynopsis("");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameterWhenPositionalParameterLabelValidSynopsisIsBlank() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("testA");
        positionalParameter.setSynopsis("     ");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(0)).addPositional(any(PositionalParamSpec.class));
    }

    @Test
    void testAddPositionalParameter() {
        PositionalParameter positionalParameter = new PositionalParameter();
        positionalParameter.setLabel("testA");
        positionalParameter.setSynopsis("Test paramA synopsis");
        builder.addPositionalParameter(positionalParameter);
        verify(commandSpec, times(1)).addPositional(any(PositionalParamSpec.class));
    }
}
