package io.github.xantorohara.rocket_table.engine;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TableModelFileTypesTest {

    @Test
    public void testSas7bdat() throws IOException {
        TableModel tableModel = new TableModel();
        File f = new File("src/test/resources/ext96243_cschar_test.sas7bdat");
        tableModel.load(f, null, null);
    }

    @Test
    public void testSbdf() throws IOException {
        TableModel tableModel = new TableModel();
        File f = new File("src/test/resources/projects.sbdf");
        tableModel.load(f, null, null);
    }

    @Test
    public void testStdf() throws IOException {
        TableModel tableModel = new TableModel();
        File f = new File("src/test/resources/world_bank_data.stdf");
        tableModel.load(f, null, null);
    }

    @Test
    public void testStdfBinary() throws IOException {
        TableModel tableModel = new TableModel();
        File f = new File("src/test/resources/usa_states.stdf");
        tableModel.load(f, null, null);
    }
}
