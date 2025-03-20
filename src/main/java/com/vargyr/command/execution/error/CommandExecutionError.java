package com.vargyr.command.execution.error;

import com.vargyr.command.execution.CommandExecutionState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandExecutionError {
    private CommandExecutionState occurredState;
    private Boolean fatal;
    private String displayMessage;
    private String details;
}
