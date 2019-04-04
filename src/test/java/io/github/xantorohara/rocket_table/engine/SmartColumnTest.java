package io.github.xantorohara.rocket_table.engine;

import io.github.xantorohara.rocket_table.engine.SmartColumns;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmartColumnTest {

    @Test
    public void test() {
        SmartColumns columns = new SmartColumns(new String[]{"Aa", "Bb", "Cc"});

        String[] names = columns.getNames();
        assertThat(names).containsExactly("Aa", "Bb", "Cc");

        assertThat(columns.getType(0)).isNull();
        assertThat(columns.getType(1)).isNull();
        assertThat(columns.getType(2)).isNull();
    }

    @Test
    public void testStringTypeWin() {
        SmartColumns columns = new SmartColumns(new String[]{"Aa", "Bb", "Cc"});
        assertThat(columns.getType(0)).isNull();
        assertThat(columns.getType(1)).isNull();
        assertThat(columns.getType(2)).isNull();

        columns.setType(0, String.class);
        columns.setType(1, Number.class);
        assertThat(columns.getType(0)).isEqualTo(String.class);
        assertThat(columns.getType(1)).isEqualTo(Number.class);
        assertThat(columns.getType(2)).isNull();

        columns.setType(0, Number.class);
        columns.setType(1, String.class);
        assertThat(columns.getType(0)).isEqualTo(String.class);
        assertThat(columns.getType(1)).isEqualTo(String.class);
        assertThat(columns.getType(2)).isNull();

        columns.setType(0, String.class);
        columns.setType(1, Number.class);
        assertThat(columns.getType(0)).isEqualTo(String.class);
        assertThat(columns.getType(1)).isEqualTo(String.class);
        assertThat(columns.getType(2)).isNull();
    }

    @Test
    public void testMap() {
        SmartColumns columns = new SmartColumns(new String[]{"Aa", "Bb", "Cc"});

        columns.setType(0, String.class);
        columns.setType(1, Number.class);

        SmartColumns mappedColumns = columns.map(2, 1);
        assertThat(mappedColumns).isEqualTo(new SmartColumns(new String[]{"Cc", "Bb"}));
        assertThat(mappedColumns.getType(0)).isNull();
        assertThat(mappedColumns.getType(1)).isEqualTo(Number.class);
    }
}
