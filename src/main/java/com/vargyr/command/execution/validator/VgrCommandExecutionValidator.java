package com.vargyr.command.execution.validator;

import com.vargyr.command.execution.CommandExecution;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VgrCommandExecutionValidator implements CommandExecutionValidator {
    private final CommandExecution commandExecution;
    private CommandExecutionValidationState state;

    public VgrCommandExecutionValidator(CommandExecution commandExecution) {
        this.commandExecution = commandExecution;
        this.state = CommandExecutionValidationState.INITIAL;
    }

    public void validate() {
        while (!state.name().equalsIgnoreCase(CommandExecutionValidationState.END.name())) {
            state.validate(commandExecution);

            if (commandExecution.getErrorManager().fatalErrorOccurred()) {
                state = CommandExecutionValidationState.END;
            } else {
                state = state.transitionToNextState();
            }
        }
    }
}
