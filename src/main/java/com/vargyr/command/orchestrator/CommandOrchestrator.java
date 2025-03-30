package com.vargyr.command.orchestrator;

import com.vargyr.command.VgrCommand;
import com.vargyr.command.execution.CommandExecution;
import com.vargyr.command.execution.CommandExecutionState;

import java.util.ArrayList;

public class CommandOrchestrator {
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
        while(commandExecution.getExitCode() == 0 &&
                !commandExecution.getState().name().equalsIgnoreCase(CommandExecutionState.END.name())) {
            commandExecution.getState().processCurrentState(commandExecution);
            commandExecution.setState(commandExecution.getState().transitionToNextState());
        }
        return commandExecution.getExitCode();
    }
}
