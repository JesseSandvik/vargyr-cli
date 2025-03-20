package com.vargyr.command;

import com.vargyr.command.metadata.VgrCommandMetadata;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.Callable;

@Getter
@Setter
public abstract class VgrCommand implements Callable<Integer> {
    private VgrCommandMetadata metadata;
    private List<PositionalParameter> positionalParameters;
    private List<Option> options;
    private List<VgrCommand> subcommands;
    private String[] originalArguments;
}
