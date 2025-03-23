package com.vargyr.command.parser.picocli;

import com.vargyr.command.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine.Model.OptionSpec;

public class OptionSpecBuilderUtilityTest {

    @Test
    void testGetOptionSpecBuilderForOptionOptionNull() {
        OptionSpec.Builder builder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(null);
        Assertions.assertNull(builder);
    }

    @Test
    void testGetOptionSpecBuilderForOptionOptionAttributesNull() {
        Option option = new Option();
        OptionSpec.Builder builder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(option);
        Assertions.assertNull(builder);
    }

    @Test
    void testGetOptionSpecBuilderForOptionOptionLongNameEmpty() {
        Option option = new Option();
        option.setLongName("");
        OptionSpec.Builder builder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(option);
        Assertions.assertNull(builder);
    }

    @Test
    void testGetOptionSpecBuilderForOptionOptionLongNameBlank() {
        Option option = new Option();
        option.setLongName("      ");
        OptionSpec.Builder builder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(option);
        Assertions.assertNull(builder);
    }
}
