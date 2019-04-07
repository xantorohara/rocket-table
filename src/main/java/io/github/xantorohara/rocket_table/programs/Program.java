package io.github.xantorohara.rocket_table.programs;

import lombok.Value;

@Value
public class Program {
    private String name;
    private String cmd;
    private String input;
    private String result;
}
