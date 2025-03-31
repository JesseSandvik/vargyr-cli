package com.vargyr.command.orchestrator;

import com.vargyr.command.VgrCommand;
import com.vargyr.command.execution.CommandExecution;
import com.vargyr.command.execution.CommandExecutionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class CommandOrchestrator {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommandOrchestrator.class);
    private final CommandExecution commandExecution;

    public CommandOrchestrator(VgrCommand rootCommand, String[] originalArguments) {
        this.commandExecution = new CommandExecution(
                CommandExecutionState.INITIAL,
                originalArguments,
                rootCommand,
                rootCommand,
                0,
                new ArrayList<>()
        );
    }

    public Integer run() {
        while (!commandExecution.getState().name().equalsIgnoreCase(CommandExecutionState.END.name())) {
            commandExecution.getState().processCurrentState(commandExecution);

            if (commandExecution.getExitCode() != 0) {
                break;
            }

            commandExecution.setState(commandExecution.getState().transitionToNextState());
        }

        commandExecution.getErrors().forEach(error -> {
            if (error.getFatal()) {
                LOGGER.error(error.getDisplayMessage());
            }
        });
        return commandExecution.getExitCode();
    }
}
