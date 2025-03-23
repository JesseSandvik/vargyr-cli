package com.vargyr.command.parser.picocli;

import com.vargyr.command.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import picocli.CommandLine.Model.OptionSpec;

public class OptionSpecBuilderUtilityTest {

    @Test
    void testGetOptionSpecBuilderForOptionWhenOptionNull() {
        OptionSpec.Builder builder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(null);
        Assertions.assertNull(builder);
    }

    @Test
    void testGetOptionSpecBuilderForOptionWhenOptionAttributesNull() {
        Option option = new Option();
        OptionSpec.Builder builder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(option);
        Assertions.assertNull(builder);
    }

    @Test
    void testGetOptionSpecBuilderForOptionWhenOptionLongNameEmpty() {
        Option option = new Option();
        option.setLongName("");
        OptionSpec.Builder builder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(option);
        Assertions.assertNull(builder);
    }

    @Test
    void testGetOptionSpecBuilderForOptionWhenOptionLongNameBlank() {
        Option option = new Option();
        option.setLongName("      ");
        OptionSpec.Builder builder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(option);
        Assertions.assertNull(builder);
    }

    @Test
    void testGetOptionSpecBuilderForOptionWhenOptionLongNameValidNoShortName() {
        String expected = "--option-a";
        Option option = new Option();
        option.setLongName(expected);
        OptionSpec.Builder builder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(option);
        OptionSpec optionSpec = builder.build();
        String actual = optionSpec.shortestName();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetOptionSpecBuilderForOptionWhenOptionLongNameValidShortNameEmpty() {
        String expected = "--option-a";
        Option option = new Option();
        option.setLongName(expected);
        option.setShortName("");
        OptionSpec.Builder builder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(option);
        OptionSpec optionSpec = builder.build();
        String actual = optionSpec.shortestName();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetOptionSpecBuilderForOptionWhenOptionLongNameValidShortNameBlank() {
        String expected = "--option-a";
        Option option = new Option();
        option.setLongName(expected);
        option.setShortName("     ");
        OptionSpec.Builder builder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(option);
        OptionSpec optionSpec = builder.build();
        String actual = optionSpec.shortestName();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetOptionSpecBuilderForOptionWhenOptionLongNameValidShortNameValid() {
        String expectedShort = "-a";
        String expectedLong = "--option-a";
        Option option = new Option();
        option.setLongName(expectedLong);
        option.setShortName(expectedShort);
        OptionSpec.Builder builder = OptionSpecBuilderUtility.getOptionSpecBuilderForOption(option);
        OptionSpec optionSpec = builder.build();
        String actualLong = optionSpec.longestName();
        String actualShort = optionSpec.shortestName();
        Assertions.assertEquals(expectedLong, actualLong);
        Assertions.assertEquals(expectedShort, actualShort);
    }

    @Test
    void testSetOptionSpecBuilderTypeForOptionWhenOptionNull() {
        OptionSpec.Builder builder = OptionSpec.builder("--test");
        OptionSpecBuilderUtility.setOptionSpecBuilderTypeForOption(null, builder);
        OptionSpec optionSpec = builder.build();
        Assertions.assertEquals(optionSpec.type(), Boolean.class);
    }

    @Test
    void testSetOptionSpecBuilderTypeForOptionWhenOptionAttributesNull() {
        OptionSpec.Builder builder = OptionSpec.builder("--test");
        OptionSpecBuilderUtility.setOptionSpecBuilderTypeForOption(new Option(), builder);
        OptionSpec optionSpec = builder.build();
        Assertions.assertEquals(optionSpec.type(), Boolean.class);
    }

    @Test
    void testSetOptionSpecBuilderTypeForOptionWhenOptionArgumentLabelEmpty() {
        OptionSpec.Builder builder = OptionSpec.builder("--test");
        Option option = new Option();
        option.setArgumentLabel("");
        OptionSpecBuilderUtility.setOptionSpecBuilderTypeForOption(new Option(), builder);
        OptionSpec optionSpec = builder.build();
        Assertions.assertEquals(optionSpec.type(), Boolean.class);
    }

    @Test
    void testSetOptionSpecBuilderTypeForOptionWhenOptionArgumentLabelBlank() {
        OptionSpec.Builder builder = OptionSpec.builder("--test");
        Option option = new Option();
        option.setArgumentLabel("     ");
        OptionSpecBuilderUtility.setOptionSpecBuilderTypeForOption(new Option(), builder);
        OptionSpec optionSpec = builder.build();
        Assertions.assertEquals(optionSpec.type(), Boolean.class);
    }

    @Test
    void testSetOptionSpecBuilderTypeForOptionWhenOptionArgumentLabelValid() {
        String expected = "<label>";
        OptionSpec.Builder builder = OptionSpec.builder("--test");
        Option option = new Option();
        option.setArgumentLabel(expected);
        OptionSpecBuilderUtility.setOptionSpecBuilderTypeForOption(option, builder);
        OptionSpec optionSpec = builder.build();
        String actual = optionSpec.paramLabel();
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(optionSpec.type(), String.class);
    }
}
