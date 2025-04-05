package com.vargyr.command.execution;

import com.vargyr.command.VgrCommand;

public class MockCommand extends VgrCommand {
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
