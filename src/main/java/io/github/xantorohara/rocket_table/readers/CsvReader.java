package io.github.xantorohara.rocket_table.readers;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

public class CsvReader implements Reader {
    @Override
    public void read(File file, String encoding,
                     Consumer<String[]> columnsConsumer, Consumer<Object[]> rowsConsumer) throws IOException {

        try (FileReader fr = new FileReader(file)) {
            CSVReader reader = new CSVReader(fr);

            columnsConsumer.accept(reader.readNext());

            String[] row;
            while ((row = reader.readNext()) != null) {
                rowsConsumer.accept(row);
            }
        }
    }
}
