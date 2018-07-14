package io.github.xantorohara.rocket_table;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
Masks:
000 - 0
001 - 1
010 - 2
011 - 3
100 - 4
101 - 5
110 - 6
111 - 7
*/
public abstract class TableModelTest {
    protected TableModel model;

    /**
     * Check that model contains exactly the given data
     */
    protected static void assertModelData(TableModel tableModel, List<String[]> values) {
        assertThat(tableModel.items).hasSize(values.size());
        for (int i = 0; i < values.size(); i++) {
            assertThat(tableModel.items.get(i).getValues()).containsExactly(values.get(i));
        }
    }

    protected static void assertMatchedRows(TableModel tableModel, int... rows) {
        int count = rows.length;

        for (SmartRow smartRow : tableModel.items) {
            boolean include = false;
            int mask = 0;
            for (int row : rows) {
                if (row % 100 == smartRow.getId()) {
                    mask = row / 100;
                    include = true;
                    count--;
                    break;
                }
            }
            assertThat(smartRow.isMatched()).isEqualTo(include);

            for (int j = 0; j < 3; j++) {
                assertThat(smartRow.isMatched(j)).isEqualTo((mask & 1 << (2 - j)) != 0);
            }
        }
        assertThat(count).isZero();
    }

    protected abstract List<String[]> allData();

    protected List<String[]> csvData(int... rows) {
        List<String[]> all = allData();
        List<String[]> out = new ArrayList<>();
        for (int row : rows) {
            out.add(all.get(row));
        }
        return out;
    }
}
