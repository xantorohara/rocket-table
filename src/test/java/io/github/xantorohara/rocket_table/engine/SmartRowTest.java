package io.github.xantorohara.rocket_table.engine;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class SmartRowTest {

    @Test
    public void test() {
        SmartRow row = new SmartRow(0, new String[]{"Aa", "Bb", "Cc"});
        String[] values = row.getValues();

        assertThat(values).containsExactly("Aa", "Bb", "Cc");

        assertThat(row.isMatched()).isFalse();
        for (int i = 0; i < values.length; i++) {
            assertThat(row.isMatched(i)).isFalse();
        }

        try {
            row.isMatched(-1);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException ignored) {
        }

        try {
            row.isMatched(3);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    @Test
    public void testMatched() {
        SmartRow row = new SmartRow(0, new String[]{"Aa", "Bb", "Cc"});

        row.setMatched(0);
        assertThat(row.isMatched()).isTrue();
        assertThat(row.isMatched(0)).isTrue();
        assertThat(row.isMatched(1)).isFalse();
        assertThat(row.isMatched(2)).isFalse();

        row.setMatched(2);
        assertThat(row.isMatched()).isTrue();
        assertThat(row.isMatched(0)).isTrue();
        assertThat(row.isMatched(1)).isFalse();
        assertThat(row.isMatched(2)).isTrue();

        row.setMatched(1);
        assertThat(row.isMatched()).isTrue();
        assertThat(row.isMatched(0)).isTrue();
        assertThat(row.isMatched(1)).isTrue();
        assertThat(row.isMatched(2)).isTrue();

        try {
            row.setMatched(-1);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException ignored) {
        }

        try {
            row.setMatched(3);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    @Test
    public void testMap() {
        SmartRow row = new SmartRow(0, new String[]{"Aa", "Bb", "Cc"});
        SmartRow out;

        out = row.map(0, 1, 2);
        assertThat(out.getValues()).containsExactly("Aa", "Bb", "Cc");
        assertThat(row.isMatched()).isFalse();

        out = row.map(2, 1, 0);
        assertThat(out.getValues()).containsExactly("Cc", "Bb", "Aa");
        assertThat(row.isMatched()).isFalse();

        out = row.map(0);
        assertThat(out.getValues()).containsExactly("Aa");
        assertThat(row.isMatched()).isFalse();

        out = row.map(1);
        assertThat(out.getValues()).containsExactly("Bb");
        assertThat(row.isMatched()).isFalse();

        out = row.map(2);
        assertThat(out.getValues()).containsExactly("Cc");
        assertThat(row.isMatched()).isFalse();

        try {
            row.map(-1);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException ignored) {
        }

        try {
            row.map(3);
            fail("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    @Test
    public void testMapMatched() {
        SmartRow row = new SmartRow(0, new String[]{"Aa", "Bb", "Cc"});
        SmartRow out;

        row.setMatched(0);

        out = row.map(0, 1, 2);
        assertThat(out.getValues()).containsExactly("Aa", "Bb", "Cc");
        assertThat(out.isMatched()).isTrue();
        assertThat(out.isMatched(0)).isTrue();
        assertThat(out.isMatched(1)).isFalse();
        assertThat(out.isMatched(2)).isFalse();

        out = row.map(2, 1, 0);
        assertThat(out.getValues()).containsExactly("Cc", "Bb", "Aa");
        assertThat(out.isMatched()).isTrue();
        assertThat(out.isMatched(0)).isFalse();
        assertThat(out.isMatched(1)).isFalse();
        assertThat(out.isMatched(2)).isTrue();

        out = row.map(1, 2);
        assertThat(out.getValues()).containsExactly("Bb", "Cc");
        assertThat(out.isMatched()).isTrue();
        assertThat(out.isMatched(0)).isFalse();
        assertThat(out.isMatched(1)).isFalse();

    }

}
