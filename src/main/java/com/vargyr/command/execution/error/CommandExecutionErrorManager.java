package com.vargyr.command.execution.error;

import com.vargyr.command.execution.CommandExecution;

import java.util.List;

public interface CommandExecutionErrorManager {
    List<CommandExecutionError> getErrors();
    Boolean fatalErrorOccurred();
    void addError(String message);
    void addFatalError(String message);
}
