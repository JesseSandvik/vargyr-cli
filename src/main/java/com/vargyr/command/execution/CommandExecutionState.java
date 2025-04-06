package com.vargyr.command.execution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum CommandExecutionState {

    INITIAL {
        @Override
        public void processCurrentState(CommandExecution commandExecution) {}

        @Override
        public CommandExecutionState transitionToNextState() {
            return executeNextStateTransition(this, VALIDATE_COMMAND_EXECUTION);
        }
    },

//    TODO: Get metadata, parameters, options, & subcommands from REST API call or config file as fallback
//    TODO: Set metadata, parameters, options, & subcommands

    VALIDATE_COMMAND_EXECUTION {
        @Override
        public void processCurrentState(CommandExecution commandExecution) {
            commandExecution.getValidator().validate();
        }

        @Override
        public CommandExecutionState transitionToNextState() {
            return executeNextStateTransition(this, PARSE_COMMAND_LINE);
        }
    },

    PARSE_COMMAND_LINE {
        @Override
        public void processCurrentState(CommandExecution commandExecution) {
            commandExecution.getParser().parse();
        }

        @Override
        public CommandExecutionState transitionToNextState() {
            return executeNextStateTransition(this, CALL_INVOKED_COMMAND);
        }
    },

    CALL_INVOKED_COMMAND {
        @Override
        public void processCurrentState(CommandExecution commandExecution) {
            try {
                commandExecution.setExitCode(commandExecution.getInvokedCommand().call());
            } catch (Exception exception) {
                commandExecution.getErrorManager().addError(exception.getMessage());
            }
        }

        @Override
        public CommandExecutionState transitionToNextState() {
            return executeNextStateTransition(this, END);
        }
    },

    END {
        @Override
        public void processCurrentState(CommandExecution commandExecution) {}

        @Override
        public CommandExecutionState transitionToNextState() {
            return END;
        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecutionState.class.getName());

    private static CommandExecutionState executeNextStateTransition(
            CommandExecutionState currentState,
            CommandExecutionState nextState
    ) {
        LOGGER.debug("transitioning command execution state from: {} to: {}", currentState, nextState);
        return nextState;
    }

    public abstract void processCurrentState(CommandExecution commandExecution);
    public abstract CommandExecutionState transitionToNextState();
}
