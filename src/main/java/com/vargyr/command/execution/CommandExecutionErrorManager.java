package com.vargyr.command.execution;

import lombok.Getter;

public class CommandExecutionErrorManager {

    public static void setFatal(
            CommandExecution commandExecution,
            String errorMessage
    ) {
        CommandExecutionError error = new CommandExecutionError();
        error.setDisplayMessage(ErrorMessagePrefix.FATAL.getPrefix() + errorMessage);
        error.setDetails(ErrorMessagePrefix.FATAL.getPrefix() + errorMessage);
        error.setFatal(true);
        commandExecution.setState(CommandExecutionState.END);
        commandExecution.getErrors().add(error);
        commandExecution.setExitCode(1);
    }

    @Getter
    public enum ErrorMessagePrefix {
        FATAL("a fatal command execution error has occurred; ");

        private final String prefix;

        ErrorMessagePrefix(String prefix) {
            this.prefix = prefix;
        }
    }
}
