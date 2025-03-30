package com.vargyr.command.executable;

import com.vargyr.command.Option;
import com.vargyr.command.PositionalParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class VgrExecutableServiceTest {

    @Test
    public void testAddPositionalParametersToCommandNoPositionalParameters() {
        List<PositionalParameter> positionalParameters = new ArrayList<>();
        List<String> command = new ArrayList<>();

        VgrExecutableService.addPositionalParametersToCommand(positionalParameters, command);
        Assertions.assertEquals(command.size(), 0);
    }

    @Test
    public void testAddPositionalParametersToCommandOnePositionalParameter() {
        List<PositionalParameter> positionalParameters = new ArrayList<>();
        PositionalParameter positionalParameterA = new PositionalParameter(
                "test A",
                "Test positional parameter A",
                "12345"
        );
        positionalParameters.add(positionalParameterA);
        List<String> command = new ArrayList<>();

        VgrExecutableService.addPositionalParametersToCommand(positionalParameters, command);
        Assertions.assertEquals(command.getFirst(), positionalParameterA.getValue());
    }

    @Test
    public void testAddPositionalParametersToCommandMultiplePositionalParameters() {
        List<PositionalParameter> positionalParameters = new ArrayList<>();
        PositionalParameter positionalParameterA = new PositionalParameter(
                "test A",
                "Test positional parameter A",
                "12345"
        );
        positionalParameters.add(positionalParameterA);
        PositionalParameter positionalParameterB = new PositionalParameter(
                "test A",
                "Test positional parameter B",
                "12345"
        );
        positionalParameters.add(positionalParameterA);
        List<String> command = new ArrayList<>();

        VgrExecutableService.addPositionalParametersToCommand(positionalParameters, command);
        Assertions.assertEquals(command.get(1), positionalParameterB.getValue());
    }

    @Test
    public void testAddOptionsToCommandNoOptions() {
        List<Option> options = new ArrayList<>();
        List<String> command = new ArrayList<>();

        VgrExecutableService.addOptionsToCommand(options, command);
        Assertions.assertEquals(command.size(), 0);
    }

    @Test
    public void testAddOptionsToCommandOneOption() {
        List<Option> options = new ArrayList<>();
        Option optionA = new Option(
                "optionA",
                "a",
                "Option A",
                "<value>",
                "12345"
        );
        options.add(optionA);
        List<String> command = new ArrayList<>();

        VgrExecutableService.addOptionsToCommand(options, command);
        Assertions.assertEquals(command.getFirst(), optionA.getLongName());
        Assertions.assertEquals(command.get(1), optionA.getValue().toString());
    }

    @Test
    public void testAddOptionsToCommandMultipleOptions() {
        List<Option> options = new ArrayList<>();
        Option optionA = new Option(
                "optionA",
                "a",
                "Option A",
                "<value>",
                "12345"
        );
        options.add(optionA);
        Option optionB = new Option(
                "optionB",
                "b",
                "Option B",
                "<value>",
                "98765"
        );
        options.add(optionB);
        List<String> command = new ArrayList<>();

        VgrExecutableService.addOptionsToCommand(options, command);
        Assertions.assertEquals(command.get(2), optionB.getLongName());
        Assertions.assertEquals(command.get(3), optionB.getValue().toString());
    }
}
