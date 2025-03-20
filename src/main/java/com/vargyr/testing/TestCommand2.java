package com.vargyr.testing;

import com.vargyr.command.executable.VgrExecutableCommand;

public class TestCommand2 extends VgrExecutableCommand {

    @Override
    public Integer call() {
        System.out.println("Hello, Other World!");
        return 0;
    }
}
