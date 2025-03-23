package com.vargyr.command.parser.picocli;

import com.vargyr.command.Option;
import io.micronaut.core.util.StringUtils;
import picocli.CommandLine.Model.OptionSpec;

public class OptionSpecBuilderUtility {

    public static OptionSpec.Builder getOptionSpecBuilderForOption(Option option) {
        if (option == null || StringUtils.isEmpty(option.getLongName()) || option.getLongName().isBlank()) {
            return null;
        }

        if (StringUtils.isNotEmpty(option.getShortName()) && !option.getShortName().isBlank()) {
            return OptionSpec.builder(option.getShortName(), option.getLongName());
        }
        return OptionSpec.builder(option.getLongName());
    }

    public static void setOptionSpecBuilderTypeForOption(Option option, OptionSpec.Builder optionSpecBuilder) {
        if (option != null &&
                StringUtils.isNotEmpty(option.getArgumentLabel()) && !option.getArgumentLabel().isBlank()) {
            optionSpecBuilder
                    .paramLabel(option.getArgumentLabel())
                    .type(String.class);
        } else {
            optionSpecBuilder.type(Boolean.class);
        }
    }
}
