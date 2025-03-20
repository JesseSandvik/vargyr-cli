package com.vargyr.command.execution;

import com.vargyr.command.VgrCommand;
import com.vargyr.command.execution.error.CommandExecutionError;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandExecution {
    private CommandExecutionState state;
    private VgrCommand baseCommand;
    private VgrCommand executedCommand;
    private Integer exitCode;
    private List<CommandExecutionError> errors;
}
