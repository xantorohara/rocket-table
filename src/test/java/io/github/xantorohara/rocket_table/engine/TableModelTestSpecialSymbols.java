package io.github.xantorohara.rocket_table.engine;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Check match of special symbols
 */
public class TableModelTestSpecialSymbols extends TableModelTest {

    private static final int[] ALL_MATCHED = new int[]{700, 701, 702, 703, 704, 705, 706, 707};

    @Before
    public void init() throws IOException {
        model = new TableModel();
        File f = new File("src/test/resources/abc-special_symbols.csv");
        model.load(f);
    }

    @Override
    protected List<String[]> allData() {
        return Arrays.asList(
                new String[]{"*", "*", "*"},        //0
                new String[]{"?", "?", "?"},        //1
                new String[]{"~", "~", "~"},        //2
                new String[]{"!", "!", "!"},        //3
                new String[]{"*_", "_*_", "_*"},    //4
                new String[]{"?_", "_?_", "_?"},    //5
                new String[]{"!_", "_!_", "_!"},    //6
                new String[]{"~_", "_~_", "_~"}     //7
        );
    }

    @Test
    public void test() {
        assertThat(model.getColumns().getNames()).containsExactly("A", "B", "C");
        assertModelData(model, allData());
        assertMatchedRows(model);
    }

    @Test
    public void whenGiven1SymbolThenMatchRows() {
        model.search("*");
        assertModelData(model, allData());
        assertMatchedRows(model, ALL_MATCHED);

        model.search("?");
        assertModelData(model, allData());
        assertMatchedRows(model, 700, 701, 702, 703);

        model.search("~");
        assertModelData(model, allData());
        assertMatchedRows(model);

        model.search("!");
        assertModelData(model, allData());
        assertMatchedRows(model);
    }

    @Test
    public void whenGiven2SymbolThenMatchRows() {
        model.search("**");
        assertModelData(model, allData());
        assertMatchedRows(model, ALL_MATCHED);

        model.search("??");
        assertModelData(model, allData());
        assertMatchedRows(model, 504, 505, 506, 507);

        model.search("~~");
        assertModelData(model, allData());
        assertMatchedRows(model);

        model.search("!!");
        assertModelData(model, allData());
        assertMatchedRows(model);
    }

    @Test
    public void whenGiven3SymbolThenMatchRows() {
        model.search("***");
        assertModelData(model, allData());
        assertMatchedRows(model, ALL_MATCHED);

        model.search("???");
        assertModelData(model, allData());
        assertMatchedRows(model, 204, 205, 206, 207);

        model.search("~~~");
        assertModelData(model, allData());
        assertMatchedRows(model);

        model.search("!!!");
        assertModelData(model, allData());
        assertMatchedRows(model);
    }
}
