package com.vargyr.command.execution.validator;

import com.vargyr.command.execution.CommandExecution;
import com.vargyr.command.execution.CommandExecutionState;
import io.micronaut.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum CommandExecutionValidationState {

    INITIAL {
        @Override
        public void validate(CommandExecution commandExecution) {}

        @Override
        public CommandExecutionValidationState transitionToNextState() {
            return executeNextStateTransition(this, VALIDATE_ORIGINAL_ARGUMENTS);
        }
    },

    VALIDATE_ORIGINAL_ARGUMENTS {
        @Override
        public void validate(CommandExecution commandExecution) {
            if (commandExecution.getOriginalArguments() == null) {
                commandExecution.getErrorManager().addFatalError("original arguments not set");
            }
        }

        @Override
        public CommandExecutionValidationState transitionToNextState() {
            return executeNextStateTransition(this, VALIDATE_ROOT_COMMAND);
        }
    },

    VALIDATE_ROOT_COMMAND {
        @Override
        public void validate(CommandExecution commandExecution) {
            if (commandExecution.getRootCommand() == null) {
                commandExecution.getErrorManager().addFatalError("root command not set");
            }
        }

        @Override
        public CommandExecutionValidationState transitionToNextState() {
            return executeNextStateTransition(this, VALIDATE_ROOT_COMMAND_METADATA);
        }
    },

    VALIDATE_ROOT_COMMAND_METADATA {
        @Override
        public void validate(CommandExecution commandExecution) {
            if (commandExecution.getRootCommand().getMetadata() == null) {
                commandExecution.getErrorManager().addFatalError("metadata not set for root command");
                return;
            }

            if (StringUtils.isEmpty(commandExecution.getRootCommand().getMetadata().getName()) ||
            commandExecution.getRootCommand().getMetadata().getName().isBlank()) {
                commandExecution.getErrorManager().addFatalError("'name' not set for root command metadata");
            }
        }

        @Override
        public CommandExecutionValidationState transitionToNextState() {
            return executeNextStateTransition(this, END);
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecutionState.class.getName());

    private static CommandExecutionValidationState executeNextStateTransition(
            CommandExecutionValidationState currentState,
            CommandExecutionValidationState nextState
    ) {
        LOGGER.debug("transitioning command execution validation state from: {} to: {}", currentState, nextState);
        return nextState;
    }

    public abstract void validate(CommandExecution commandExecution);
    public abstract CommandExecutionValidationState transitionToNextState();
}
