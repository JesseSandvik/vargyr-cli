package com.vargyr.file.system;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.List;

@Getter
@Setter
public class FileSystemExecutor {
    private final ProcessBuilder processBuilder;
    private Boolean printEnabled = true;
    private PrintStream printStream = System.out;

    public FileSystemExecutor(ProcessBuilder processBuilder) {
        this.processBuilder = processBuilder;
    }

    protected void printStream(Process process) throws IOException {
        if (process.getInputStream() != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                printStream.println(line);
            }
        }
    }

    public Integer executeCommand(List<String> command) throws IOException, InterruptedException {
        this.processBuilder.command(command);
        Process process = processBuilder.start();

        if (printEnabled && printStream != null) {
            printStream(process);
        }

        process.waitFor();
        return process.exitValue();
    }
}
