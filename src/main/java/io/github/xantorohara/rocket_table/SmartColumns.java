package io.github.xantorohara.rocket_table;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SmartColumns {
    private String[] names;
    private Class[] types;

    public String[] getNames() {
        return names;
    }

    public Class getType(int columnNumber) {
        return types[columnNumber];
    }

    public int getCount() {
        return names.length;
    }

    public SmartColumns(String[] names) {
        this.names = names;
        this.types = new Class[names.length];
    }

    private SmartColumns(String[] names, Class[] types) {
        this.names = names;
        this.types = types;
    }

    public SmartColumns map(int... columnNumbers) {
        return new SmartColumns(
                IntStream.of(columnNumbers).mapToObj(c -> names[c]).toArray(String[]::new),
                IntStream.of(columnNumbers).mapToObj(c -> types[c]).toArray(Class[]::new));
    }

    /**
     * String type win
     */
    public void setType(int columnNumber, Class type) {
        if (types[columnNumber] != String.class) {
            types[columnNumber] = type == String.class ? String.class : type;
        }
    }

    public boolean equals(Object o) {
        return this == o || (getClass() == o.getClass() && Arrays.equals(names, ((SmartColumns) o).names));
    }

    public int hashCode() {
        return Arrays.hashCode(names);
    }

    public String toString() {
        return Arrays.toString(names);
    }
}
