package com.vargyr.command.execution.validator;

import com.vargyr.command.execution.CommandExecution;

public interface CommandExecutionValidator {
    CommandExecutionValidationState getState();
    void setState(CommandExecutionValidationState state);
    void validate(CommandExecution commandExecution);
}
