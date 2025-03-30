package com.vargyr.command.execution;

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
