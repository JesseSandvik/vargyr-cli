package com.vargyr.command.execution.error;

import com.vargyr.command.execution.CommandExecution;
import com.vargyr.command.execution.CommandExecutionState;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class VgrCommandExecutionErrorManager implements CommandExecutionErrorManager {
    private final List<CommandExecutionError> errors;

    public VgrCommandExecutionErrorManager() {
        this.errors = new ArrayList<>();
    }

    @Override
    public Boolean fatalErrorOccurred() {
        if (!this.errors.isEmpty()) {
            for (CommandExecutionError commandExecutionError : this.errors) {
                if (commandExecutionError.getFatal()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void addError(CommandExecution commandExecution, String message) {
        CommandExecutionError error = new CommandExecutionError();
        error.setOccurredState(commandExecution.getState());
        error.setDisplayMessage(ErrorMessagePrefix.ERROR.getPrefix() + message);
        error.setDetails(ErrorMessagePrefix.ERROR.getPrefix() + message);
        error.setFatal(false);
        errors.add(error);
        commandExecution.setExitCode(1);
    }

    @Override
    public void addFatalError(CommandExecution commandExecution, String message) {
        CommandExecutionError error = new CommandExecutionError();
        error.setOccurredState(commandExecution.getState());
        error.setDisplayMessage(ErrorMessagePrefix.FATAL.getPrefix() + message);
        error.setDetails(ErrorMessagePrefix.FATAL.getPrefix() + message);
        error.setFatal(true);
        commandExecution.setState(CommandExecutionState.END);
        errors.add(error);
        commandExecution.setExitCode(1);
    }

    @Getter
    enum ErrorMessagePrefix {
        ERROR("a command execution error has occurred; "),
        FATAL("a fatal command execution error has occurred; ");

        private final String prefix;

        ErrorMessagePrefix(String prefix) {
            this.prefix = prefix;
        }
    }
}
