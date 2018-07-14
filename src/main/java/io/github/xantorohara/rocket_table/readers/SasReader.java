package io.github.xantorohara.rocket_table.readers;

import com.epam.parso.Column;
import com.epam.parso.impl.SasFileReaderImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class SasReader implements Reader {
    @Override
    public void read(File file, String encoding,
                     Consumer<String[]> columnsConsumer, Consumer<Object[]> rowsConsumer) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            SasFileReaderImpl reader = new SasFileReaderImpl(is, encoding);

            columnsConsumer.accept(reader.getColumns().stream().map(Column::getName).toArray(String[]::new));

            Object[] row;
            while ((row = reader.readNext()) != null) {
                rowsConsumer.accept(row);
            }
        }
    }
}
