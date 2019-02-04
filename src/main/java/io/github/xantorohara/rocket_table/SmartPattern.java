package io.github.xantorohara.rocket_table;

import java.util.regex.Pattern;

public class SmartPattern {
    private String string;
    private Pattern pattern;
    private boolean caseSensitive;
    private boolean inverse;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public boolean isInverse() {
        return inverse;
    }

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmartPattern that = (SmartPattern) o;

        return caseSensitive == that.caseSensitive && string.equals(that.string);
    }

    public int hashCode() {
        return string.hashCode() + (caseSensitive ? 1 : 0);
    }
}
