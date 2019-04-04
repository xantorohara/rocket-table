package io.github.xantorohara.rocket_table.engine;

import org.junit.Test;

/**
 * Check case-sensitive and inverse flags
 */
public class TableModelTestAbcMatchFlags extends TableModelTestAbc {

    @Test
    public void test1() {
        model.search("~*");
        assertMatchedRows(model, ALL_MATCHED);

        model.search("!*");
        assertMatchedRows(model);

        model.search("!~*");
        assertMatchedRows(model);

        model.search("~!*");
        assertMatchedRows(model);
    }

    @Test
    public void test2() {
        model.search("a");
        assertMatchedRows(model, 402, 403, 404, 405, 410);

        model.search("~a");
        assertMatchedRows(model, 401, 402, 403, 404, 405, 410);

        model.search("!a");
        assertMatchedRows(model, 700, 701, 302, 303, 304, 305, 706, 707, 708, 709, 310, 711, 712, 713, 714, 715);

        model.search("!~a");
        assertMatchedRows(model, 700, 301, 302, 303, 304, 305, 706, 707, 708, 709, 310, 711, 712, 713, 714, 715);
    }
}
