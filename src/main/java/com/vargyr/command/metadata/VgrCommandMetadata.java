package com.vargyr.command.metadata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VgrCommandMetadata {
    private String name;
    private String version;
    private String synopsis;
    private String description;
    private String executableFilePath;
    private Boolean executesWithoutArguments = true;
}
