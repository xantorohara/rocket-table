package io.github.xantorohara.rocket_table.engine;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test on CSV file with 99 columns and 3 rows like this:
 * C0,C1,C2,C3,C4,C5,C6,C7,C8,C9...
 * 0,1,2,3,4,5,6,7,8,9...
 * 0,0,0,0,0,0,0,0,0,0...
 * ,,,,,,,,,...
 */
public class TableModelWideTableTest {

    TableModel tableModel;

    public static void main1(String[] args) {
        System.out.println(IntStream.range(0, 100).mapToObj(i -> "C" + i).collect(Collectors.joining(",")));
        System.out.println(IntStream.range(0, 100).mapToObj(i -> "" + i).collect(Collectors.joining(",")));
        System.out.println(IntStream.range(0, 100).mapToObj(i -> "0").collect(Collectors.joining(",")));
        System.out.println(IntStream.range(0, 100).mapToObj(i -> "").collect(Collectors.joining(",")));
    }

    @Before
    public void init() throws IOException {
        tableModel = new TableModel();
        File f = new File("src/test/resources/c99.csv");
        tableModel.load(f);
    }

    @Test
    public void testDefault() {
        assertThat(tableModel.getColumns().getNames()).hasSize(99);
        assertThat(tableModel.items).hasSize(3);
        assertThat(tableModel.items.get(0).getValues()).hasSize(99);
        assertThat(tableModel.items.get(1).getValues()).hasSize(99);
        assertThat(tableModel.items.get(2).getValues()).hasSize(99);

//        assertThat(tableModel.hasMatches()).isEqualTo(0);

        for (SmartRow smartRow : tableModel.items) {
            assertThat(smartRow.isMatched()).isFalse();
            for (int i = 0; i < smartRow.getSize(); i++) {
                assertThat(smartRow.isMatched(i)).isFalse();
            }
        }
    }

    @Test
    public void testMatch() {
        tableModel.search("*0*");
        assertThat(tableModel.items).hasSize(3);

//        assertThat(tableModel.hasMatches()).isEqualTo(2);
        assertThat(tableModel.items.get(0).isMatched()).isTrue();
        assertThat(tableModel.items.get(1).isMatched()).isTrue();
        assertThat(tableModel.items.get(2).isMatched()).isFalse();

        SmartRow smartRow;
        smartRow = tableModel.items.get(0);
        for (int i = 0; i < smartRow.getSize(); i++) {
            if (i == 9 || i == 19 || i == 29 || i == 39 || i == 49 || i == 59) {
                assertThat(smartRow.isMatched(i)).isTrue();
            } else {
                assertThat(smartRow.isMatched(i)).isFalse();
            }
        }

        smartRow = tableModel.items.get(1);
        for (int i = 0; i < smartRow.getSize(); i++) {
            if (i < 64) {
                assertThat(smartRow.isMatched(i)).isTrue();
            } else {
                assertThat(smartRow.isMatched(i)).isFalse();
            }
        }

        smartRow = tableModel.items.get(2);
        for (int i = 0; i < smartRow.getSize(); i++) {
            assertThat(smartRow.isMatched(i)).isFalse();
        }
    }

    @Test
    public void testMatchMapped() {
        tableModel.search("*0*");
        tableModel.setColumns("C1");

        assertThat(tableModel.items).hasSize(3);
//        assertThat(tableModel.hasMatches()).isEqualTo(2);
        assertThat(tableModel.items.get(0).isMatched()).isTrue();
        assertThat(tableModel.items.get(1).isMatched()).isTrue();
        assertThat(tableModel.items.get(2).isMatched()).isFalse();

        SmartRow smartRow;
        smartRow = tableModel.items.get(0);
        for (int i = 0; i < smartRow.getSize(); i++) {
            assertThat(smartRow.isMatched(i)).isFalse();
        }

        smartRow = tableModel.items.get(1);
        for (int i = 0; i < smartRow.getSize(); i++) {
            assertThat(smartRow.isMatched(i)).isTrue();
        }

        smartRow = tableModel.items.get(2);
        for (int i = 0; i < smartRow.getSize(); i++) {
            assertThat(smartRow.isMatched(i)).isFalse();
        }
    }
}
