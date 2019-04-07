package io.github.xantorohara.rocket_table.engine;

import org.junit.Test;

/**
 * Test matching
 */
public class TableModelTestAbcGlobPatterns extends TableModelTestAbc {

    @Test
    public void testMatchNumbers() {
        model.search("1");
        assertMatchedRows(model, 400);

        model.search("1*");
        assertMatchedRows(model, 400);

        model.search("*1");
        assertMatchedRows(model, 400);

        model.search("1?");
        assertMatchedRows(model);

        model.search("?1");
        assertMatchedRows(model);
    }

    @Test
    public void testMatch1Letter() {
        model.search(" ");
        assertMatchedRows(model);

        model.search("* ");
        assertMatchedRows(model, ALL_MATCHED);

        model.search(" *");
        assertMatchedRows(model, ALL_MATCHED);

        model.search("z");
        assertMatchedRows(model);

        model.search("z*");
        assertMatchedRows(model);

        model.search("*z");
        assertMatchedRows(model);

        model.search("*z*");
        assertMatchedRows(model);

        model.search("a");
        assertMatchedRows(model, 402, 403, 404, 405, 410);

        model.search("a*");
        assertMatchedRows(model, 402, 403, 404, 405, 710, 413);

        model.search("*a");
        assertMatchedRows(model, 402, 403, 404, 405, 410);

        model.search("*a*");
        assertMatchedRows(model, 402, 403, 404, 405, 710, 413, 414);

        model.search("b");
        assertMatchedRows(model, 202, 203, 204, 206, 411);

        model.search("b?");
        assertMatchedRows(model, 211);

        model.search("?b");
        assertMatchedRows(model, 210);

        model.search("?b?");
        assertMatchedRows(model, 110, 213);

        model.search("c");
        assertMatchedRows(model, 102, 103, 107, 412);

        model.search("*c?");
        assertMatchedRows(model, 111, 212, 113);

        model.search("?c*");
        assertMatchedRows(model, 311, 114);

    }

    @Test
    public void testMatch2Letters() {
        model.search("  ");
        assertMatchedRows(model);

        model.search("zz");
        assertMatchedRows(model);

        model.search("ab");
        assertMatchedRows(model, 210);

        model.search("bc");
        assertMatchedRows(model, 211);

        model.search("ac");
        assertMatchedRows(model);

        model.search("a*c");
        assertMatchedRows(model, 110);

        model.search("a?c");
        assertMatchedRows(model, 110);
    }
}
