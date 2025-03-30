package com.vargyr.command.execution;

import io.micronaut.core.util.StringUtils;

public enum CommandExecutionValidationState {

    INITIAL {
        @Override
        public void validate(CommandExecution commandExecution) {}

        @Override
        public CommandExecutionValidationState transitionToNextState() {
            return ORIGINAL_ARGUMENTS;
        }
    },

    ORIGINAL_ARGUMENTS {
        @Override
        public void validate(CommandExecution commandExecution) {
            if (commandExecution.getOriginalArguments() == null) {
                CommandExecutionErrorManager.setFatal(
                        commandExecution,
                        "original arguments not set for command execution"
                );
            }
        }

        @Override
        public CommandExecutionValidationState transitionToNextState() {
            return ROOT_COMMAND;
        }
    },

    ROOT_COMMAND {
        @Override
        public void validate(CommandExecution commandExecution) {
            if (commandExecution.getRootCommand() == null) {
                CommandExecutionErrorManager.setFatal(
                        commandExecution,
                        "root command not set for command execution"
                );
            }
        }

        @Override
        public CommandExecutionValidationState transitionToNextState() {
            return ROOT_COMMAND_METADATA;
        }
    },

    ROOT_COMMAND_METADATA {
        @Override
        public void validate(CommandExecution commandExecution) {
            if (commandExecution.getRootCommand() == null) {
                CommandExecutionErrorManager.setFatal(
                        commandExecution,
                        "metadata not set for command execution root command"
                );
                return;
            }

            if (StringUtils.isEmpty(commandExecution.getRootCommand().getMetadata().getName()) ||
            commandExecution.getRootCommand().getMetadata().getName().isBlank()) {
                CommandExecutionErrorManager.setFatal(
                        commandExecution,
                        "root command metadata 'name' not set for command execution root command"
                );
            }
        }

        @Override
        public CommandExecutionValidationState transitionToNextState() {
            return END;
        }
    },

    END {
        @Override
        public void validate(CommandExecution commandExecution) {}

        @Override
        public CommandExecutionValidationState transitionToNextState() {
            return END;
        }
    };

    public abstract void validate(CommandExecution commandExecution);
    public abstract CommandExecutionValidationState transitionToNextState();
}
