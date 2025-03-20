package com.vargyr.command.execution;

import com.vargyr.command.parser.picocli.PicocliCommandLineParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum CommandExecutionState {

    INITIAL {
        @Override
        public void processCurrentState(CommandExecution commandExecution) {
            LOGGER.info("initialized command processor");
        }

        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.info("transitioning to next command processor state");
            return PARSE_COMMAND_LINE;
        }
    },

//    TODO: Get metadata, parameters, options, & subcommands from REST API call or config file as fallback
//    TODO: Set metadata, parameters, options, & subcommands

    PARSE_COMMAND_LINE {
        @Override
        public void processCurrentState(CommandExecution commandExecution) {
            LOGGER.info("parsing command line");
            PicocliCommandLineParser parser = new PicocliCommandLineParser(commandExecution.getExecutedCommand());
            parser.parse(commandExecution);
        }

        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.info("transitioning to call command state");
            return CALL_COMMAND;
        }
    },

    CALL_COMMAND {
        @Override
        public void processCurrentState(CommandExecution commandExecution) {
            try {
                LOGGER.info("calling command");
                commandExecution.setExitCode(commandExecution.getExecutedCommand().call());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.info("transitioning to end state");
            return END;
        }
    },

    END {
        @Override
        public void processCurrentState(CommandExecution commandExecution) {
            LOGGER.info("reached command processor end state");
        }

        @Override
        public CommandExecutionState transitionToNextState() {
            LOGGER.info("we done.");
            return END;
        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecutionState.class.getName());

    public abstract void processCurrentState(CommandExecution commandExecution);
    public abstract CommandExecutionState transitionToNextState();
}
