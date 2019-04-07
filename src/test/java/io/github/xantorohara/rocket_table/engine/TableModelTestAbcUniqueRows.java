package io.github.xantorohara.rocket_table.engine;

import org.junit.Test;

/**
 * Check matching, filtering and distincts
 */
public class TableModelTestAbcUniqueRows extends TableModelTestAbc {

    @Test
    public void testUniqueRows() {
        model.search("*");
        assertModelData(model, CSV_DATA);
        assertMatchedRows(model, ALL_MATCHED);

        model.setUniqueRows(true);
        assertModelData(model, csvData(0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
        assertMatchedRows(model, 700, 701, 702, 704, 705, 706, 707, 708, 709, 710, 711, 712, 713, 714, 715);

        model.search("*a*");
        assertModelData(model, csvData(0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
        assertMatchedRows(model, 402, 404, 405, 710, 413, 414);

        model.setUniqueRows(false);
        assertModelData(model, csvData(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
        assertMatchedRows(model, 402, 403, 404, 405, 710, 413, 414);
    }
}
