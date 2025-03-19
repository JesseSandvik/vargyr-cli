package com.vargyr.command.executable;

import com.vargyr.command.VgrCommand;
import com.vargyr.file.system.FileSystemExecutor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class VgrExecutable extends VgrCommand {
    private final static Logger LOGGER = LoggerFactory.getLogger(VgrExecutable.class);
    private PrintStream printStream = System.out;

    @Override
    public Integer call() throws IOException, InterruptedException {
        List<String> command = new ArrayList<>();
        command.add(getMetadata().getExecutableFilePath());
        VgrExecutableService.addPositionalParametersToCommand(getPositionalParameters(), command);
        VgrExecutableService.addOptionsToCommand(getOptions(), command);
        FileSystemExecutor fileSystemExecutor = new FileSystemExecutor(new ProcessBuilder());
        return fileSystemExecutor.executeCommand(command);
    }
}
