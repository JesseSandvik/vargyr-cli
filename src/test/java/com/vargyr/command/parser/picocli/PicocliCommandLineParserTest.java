package com.vargyr.command.parser.picocli;

import com.vargyr.command.VgrCommand;
import com.vargyr.command.metadata.VgrCommandMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.OptionSpec;
import picocli.CommandLine.Model.UsageMessageSpec;

import static org.mockito.Mockito.*;

class MockVgrCommand extends VgrCommand {
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}

@ExtendWith(MockitoExtension.class)
public class PicocliCommandLineParserTest {
    private VgrCommand command;
    private final CommandSpec commandSpec = mock(CommandSpec.class);

    @BeforeEach
    void setUp() {
        this.command = new MockVgrCommand();
    }

    @Test
    void testSetSynopsisNoMetadata() {
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.setSynopsis();
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetSynopsisMetadataMissingSynopsis() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        this.command.setMetadata(metadata);
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.setSynopsis();
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetSynopsisMetadataEmptySynopsis() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        String expected = "";
        metadata.setSynopsis(expected);
        this.command.setMetadata(metadata);
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.setSynopsis();
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetSynopsisMetadataBlankSynopsis() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        String expected = "     ";
        metadata.setSynopsis(expected);
        this.command.setMetadata(metadata);
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.setSynopsis();
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetSynopsis() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        String expected = "Test synopsis for mock command.";
        metadata.setSynopsis(expected);
        this.command.setMetadata(metadata);
        when(commandSpec.usageMessage()).thenReturn(mock(UsageMessageSpec.class));
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.setSynopsis();
        verify(commandSpec, times(1)).usageMessage();
    }

    @Test
    void testSetDescriptionNoMetadata() {
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.setDescription();
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetDescriptionMetadataMissingDescription() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        this.command.setMetadata(metadata);
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.setDescription();
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetDescriptionMetadataEmptyDescription() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        String expected = "";
        metadata.setDescription(expected);
        this.command.setMetadata(metadata);
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.setDescription();
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetDescriptionMetadataBlankDescription() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        String expected = "     ";
        metadata.setDescription(expected);
        this.command.setMetadata(metadata);
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.setDescription();
        verify(commandSpec, times(0)).usageMessage();
    }

    @Test
    void testSetDescription() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        String expected = "Test description for mock command.";
        metadata.setDescription(expected);
        this.command.setMetadata(metadata);
        when(commandSpec.usageMessage()).thenReturn(mock(UsageMessageSpec.class));
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.setDescription();
        verify(commandSpec, times(1)).usageMessage();
    }

    @Test
    void testAddVersionOptionNoMetadata() {
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.addVersionOption();
        verify(commandSpec, times(0)).version();
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddVersionOptionMetadataMissingVersion() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        this.command.setMetadata(metadata);
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.addVersionOption();
        verify(commandSpec, times(0)).version();
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddVersionOptionMetadataEmptyVersion() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        metadata.setVersion("");
        this.command.setMetadata(metadata);
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.addVersionOption();
        verify(commandSpec, times(0)).version();
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddVersionOptionMetadataBlankVersion() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        metadata.setVersion("     ");
        this.command.setMetadata(metadata);
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.addVersionOption();
        verify(commandSpec, times(0)).version();
        verify(commandSpec, times(0)).addOption(any(OptionSpec.class));
    }

    @Test
    void testAddVersionOption() {
        VgrCommandMetadata metadata = new VgrCommandMetadata();
        String expected = "1.0.2";
        metadata.setVersion(expected);
        this.command.setMetadata(metadata);
        PicocliCommandLineParser parser = new PicocliCommandLineParser(this.command, commandSpec);
        parser.addVersionOption();
        verify(commandSpec, times(1)).version(eq(expected));
        verify(commandSpec, times(1)).addOption(any(OptionSpec.class));
    }
}
