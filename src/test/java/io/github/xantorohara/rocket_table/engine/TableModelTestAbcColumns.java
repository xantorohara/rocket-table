package io.github.xantorohara.rocket_table.engine;

import org.junit.Test;

/**
 * Check search string with "column=value, " specification
 */
public class TableModelTestAbcColumns extends TableModelTestAbc {

    @Test
    public void test1() {
        model.search("=*");
        assertModelData(model, CSV_DATA);
        assertMatchedRows(model);
    }

    @Test
    public void test2() {
        model.search("X=a");
        assertModelData(model, CSV_DATA);
        assertMatchedRows(model);

        model.search("A=a");
        assertModelData(model, CSV_DATA);
        assertMatchedRows(model, 402, 403, 404, 405, 410);
    }

    @Test
    public void test3() {
        model.search("A=a, B=b");
        assertModelData(model, CSV_DATA);
        assertMatchedRows(model, 602, 603, 604);

        model.search("B=a, A=b");
        assertModelData(model, CSV_DATA);
        assertMatchedRows(model);
    }

    @Test
    public void test4() {
        model.search("A=a, A=b");
        assertModelData(model, CSV_DATA);
        assertMatchedRows(model, 402, 403, 404, 405, 410, 411);

        model.search("A=a, A=b, B=b*");
        assertModelData(model, CSV_DATA);
        assertMatchedRows(model, 602, 603, 604, 611);

        model.search("A=~a, A=b, B=~b*");
        assertModelData(model, CSV_DATA);
        assertMatchedRows(model, 601, 602, 603, 604, 611);
    }
}
