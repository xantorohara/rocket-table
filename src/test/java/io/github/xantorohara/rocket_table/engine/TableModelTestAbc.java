package io.github.xantorohara.rocket_table.engine;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class TableModelTestAbc extends TableModelTest {
    /**
     * Masks:
     * 000 - 0
     * 001 - 1
     * 010 - 2
     * 011 - 3
     * 100 - 4
     * 101 - 5
     * 110 - 6
     * 111 - 7
     */
    protected static final List<String[]> CSV_DATA = Arrays.asList(
            new String[]{"1", "2", "3"}, //             0
            new String[]{"A", "B", "C"}, //             1
            new String[]{"a", "b", "c"}, //             2
            new String[]{"a", "b", "c"}, //             3
            new String[]{"a", "b", ""}, //              4
            new String[]{"a", "", ""}, //               5
            new String[]{"", "b", ""}, //               6
            new String[]{"", "", "c"}, //               7
            new String[]{"", "", ""}, //                8
            new String[]{" ", " ", ""}, //              9
            new String[]{"a", "ab", "abc"}, //          10
            new String[]{"b", "bc", "bc_"}, //          11
            new String[]{"c", "c_", "c__"}, //          12
            new String[]{"a_", "_b_", "c_"}, //         13
            new String[]{"_a__", "__b__", "_c__"}, //   14
            new String[]{"_", "_", "_"} //              15
    );
    //

    protected static final int[] ALL_MATCHED = IntStream.range(0, CSV_DATA.size()).map(i -> 700 + i).toArray();

    protected List<String[]> allData() {
        return CSV_DATA;
    }

    @Before
    public void init() throws IOException {
        model = new TableModel();
        File f = new File("src/test/resources/abc.csv");
        model.load(f);
    }

    @Test
    public void test() {
        assertThat(model.getColumns().getNames()).containsExactly("A", "B", "C");
        assertModelData(model, allData());
        assertMatchedRows(model);
    }

    @Test
    public void testMatchAll() {
        model.search("*");
        assertModelData(model, allData());
        assertMatchedRows(model, ALL_MATCHED);
    }
}