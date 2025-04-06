package com.vargyr.command.execution;

import com.vargyr.command.VgrCommand;
import com.vargyr.command.execution.error.CommandExecutionErrorManager;
import com.vargyr.command.execution.validator.CommandExecutionValidator;
import com.vargyr.command.parser.CommandLineParser;
import lombok.*;

import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandExecution {
    private CommandExecutionErrorManager errorManager;
    private CommandExecutionValidator validator;
    private CommandLineParser parser;
    private CommandExecutionState state;
    private String[] originalArguments;
    private VgrCommand rootCommand;
    private VgrCommand invokedCommand;
    private Duration invokedCommandDuration;
    private Duration duration;
    private Integer exitCode;
}
