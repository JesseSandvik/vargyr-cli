package com.vargyr.testing;

import com.vargyr.command.executable.VgrExecutableCommand;

public class TestCommand extends VgrExecutableCommand {

    @Override
    public Integer call() {
        System.out.println("Hello, World!");
        return 0;
    }
}
