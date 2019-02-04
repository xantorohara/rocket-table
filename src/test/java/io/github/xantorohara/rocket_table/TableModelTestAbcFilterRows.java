package io.github.xantorohara.rocket_table;

import org.junit.Test;

import java.io.IOException;

/**
 * Check matching, filtering and distincts
 */
public class TableModelTestAbcFilterRows extends TableModelTestAbc {

    @Test
    public void testFilterRows() throws IOException {
        model.search("*");
        assertModelData(model, CSV_DATA);
        assertMatchedRows(model, ALL_MATCHED);

        model.setFilterRows(true);
        assertModelData(model, CSV_DATA);
        assertMatchedRows(model, ALL_MATCHED);

        model.search("*a*");
        assertModelData(model, csvData(2, 3, 4, 5, 10, 13, 14));
        assertMatchedRows(model, 402, 403, 404, 405, 710, 413, 414);

        model.setFilterRows(false);
        assertModelData(model, CSV_DATA);
        assertMatchedRows(model, 402, 403, 404, 405, 710, 413, 414);

    }
}
