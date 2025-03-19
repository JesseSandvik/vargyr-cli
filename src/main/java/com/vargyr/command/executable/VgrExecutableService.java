package com.vargyr.command.executable;

import com.vargyr.command.Option;
import com.vargyr.command.PositionalParameter;

import java.util.List;

public class VgrExecutableService {

    protected static void addPositionalParametersToCommand(
            List<PositionalParameter> positionalParameters, List<String> command) {
        if (!positionalParameters.isEmpty()) {
            positionalParameters.forEach(positionalParameter -> {
                if (positionalParameter.getValue() != null) {
                    command.add(positionalParameter.getValue().toString());
                }
            });
        }
    }

    protected static void addOptionsToCommand(List<Option> options, List<String> command) {
        if (!options.isEmpty()) {
            options.forEach(option -> {
                if (option.getValue() != null) {
                    command.add(option.getLongName());
                    command.add(option.getValue().toString());
                }
            });
        }
    }
}
