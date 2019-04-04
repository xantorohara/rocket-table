package io.github.xantorohara.rocket_table.engine;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "values")
public class SmartRow {
    private static final int MAX_MATCH_COLUMNS = 64;
    private int id;
    private String[] values;
    private long mask;
    private boolean matched;

    public SmartRow(int id, String[] values) {
        this.id = id;
        this.values = values;
    }

    private SmartRow(int id, String[] values, long mask, boolean matched) {
        this.id = id;
        this.values = values;
        this.mask = mask;
        this.matched = matched;
    }

    public SmartRow map(int... columnNumbers) {
        String[] mappedValues = new String[columnNumbers.length];

        long mappedMask = 0;
        for (int i = 0; i < mappedValues.length; i++) {
            int columnNumber = columnNumbers[i];
            if (columnNumber < 0 || columnNumber >= values.length) {
                throw new IndexOutOfBoundsException();
            }
            mappedValues[i] = values[columnNumber];

            if (i < MAX_MATCH_COLUMNS && columnNumber < MAX_MATCH_COLUMNS && (mask | (1L << columnNumber)) == mask) {
                mappedMask |= 1L << i;
            }
        }
        return new SmartRow(id, mappedValues, mappedMask, matched);
    }

    public int getSize() {
        return values.length;
    }

    public int getId() {
        return id;
    }

    public String[] getValues() {
        return values;
    }

    public String getValue(int columnNumber) {
        return values[columnNumber];
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(int columnNumber) {
        if (columnNumber < 0 || columnNumber >= values.length) {
            throw new IndexOutOfBoundsException();
        }
        matched = true;
        if (columnNumber < MAX_MATCH_COLUMNS) {
            mask |= 1L << columnNumber;
        }
    }

    public void setMatched() {
        matched = true;
        mask = 0xffffffffffffffffL;
    }

    public void clearMatched() {
        matched = false;
        mask = 0;
    }

    public boolean isMatched(int columnNumber) {
        if (columnNumber < 0 || columnNumber >= values.length) {
            throw new IndexOutOfBoundsException();
        }
        return matched && columnNumber < MAX_MATCH_COLUMNS && (mask | (1L << columnNumber)) == mask;
    }
}
