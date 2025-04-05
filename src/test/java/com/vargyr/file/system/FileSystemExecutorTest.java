package com.vargyr.file.system;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileSystemExecutorTest {

    @InjectMocks
    private FileSystemExecutor fileSystemExecutor;

    @Mock
    private ProcessBuilder processBuilder;

    @Test
    void testExecuteCommand() throws IOException, InterruptedException {
        Process process = mock(Process.class);
        when(processBuilder.start()).thenReturn(process);
        when(process.waitFor()).thenReturn(0);
        fileSystemExecutor.executeCommand(new ArrayList<>());
        verify(process, times(1)).exitValue();
    }
}
