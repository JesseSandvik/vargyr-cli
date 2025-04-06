package com.vargyr.command.execution.validator;

import com.vargyr.command.execution.CommandExecution;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VgrCommandExecutionValidator implements CommandExecutionValidator {
    private CommandExecutionValidationState state;

    public VgrCommandExecutionValidator() {
        this.state = CommandExecutionValidationState.INITIAL;
    }

    public void validate(CommandExecution commandExecution) {
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
