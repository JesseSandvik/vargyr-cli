package com.vargyr.command.execution.validator;

public interface CommandExecutionValidator {
    CommandExecutionValidationState getState();
    void setState(CommandExecutionValidationState state);
    void validate();
}
