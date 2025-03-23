package com.vargyr.command.parser.picocli;

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
        when(commandSpec.usageMessage()).thenReturn(mock(UsageMessageSpec.class));
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
        when(commandSpec.usageMessage()).thenReturn(mock(UsageMessageSpec.class));
        builder.setDescription(description);
        verify(commandSpec, times(1)).usageMessage();
    }
}
