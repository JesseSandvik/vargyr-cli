package com.vargyr.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Option {
    private String longName;
    private String shortName;
    private String synopsis;
    private String argumentLabel;
    private Object value;
}
