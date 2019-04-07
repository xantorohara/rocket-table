package io.github.xantorohara.rocket_table.engine;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.regex.Pattern;

@Data
@EqualsAndHashCode(of = {"string", "caseSensitive"})
public class SmartPattern {
    private String string;
    private Pattern pattern;
    private boolean caseSensitive;
    private boolean inverse;
}
