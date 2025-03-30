package com.vargyr.command.execution;

import com.vargyr.command.VgrCommand;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandExecution {
    private CommandExecutionState state;
    private String[] originalArguments;
    private VgrCommand rootCommand;
    private VgrCommand invokedCommand;
    private Integer exitCode;
    private List<CommandExecutionError> errors;
}
