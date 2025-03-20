package com.vargyr.command.executable;

import com.vargyr.command.VgrCommand;
import com.vargyr.file.system.FileSystemExecutor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class VgrExecutableCommand extends VgrCommand {

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
