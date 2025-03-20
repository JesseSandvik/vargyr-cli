package com.vargyr.command.parser;

import com.vargyr.command.execution.CommandExecution;

public interface CommandLineParser {
    void parse(CommandExecution commandExecution);
}
