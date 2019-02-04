package io.github.xantorohara.rocket_table.readers;

import com.spotfire.sbdf.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.function.Consumer;

public class SbdfReader implements Reader {
    @Override
    public void read(File file, String encoding,
                     Consumer<String[]> columnsConsumer, Consumer<Object[]> rowsConsumer) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            BinaryReader reader = new BinaryReader(is);
            FileHeader.read(reader);
            TableMetadata tableMetadata = TableMetadata.read(reader);

            ColumnMetadata[] columnsMeta = tableMetadata.getColumns();
            String[] columnNames = new String[columnsMeta.length];
            for (int i = 0; i < columnsMeta.length; i++) {
                columnNames[i] = columnsMeta[i].getName();
            }
            columnsConsumer.accept(columnNames);

            TableReader tableReader = new TableReader(reader, tableMetadata);
            int colIndex = 0;

            Object[] row = new Object[columnsMeta.length];
            while (true) {

                Object value = tableReader.readValue();
                if (value == null) {
                    break;
                }

                ColumnMetadata column = columnsMeta[colIndex];

                if (value == column.getDataType().getInvalidValue()) {
                    row[colIndex] = null;
                } else {
                    row[colIndex] = value;
                }
                colIndex++;

                if (colIndex == columnsMeta.length) {
                    colIndex = 0;
                    rowsConsumer.accept(row);
                    Arrays.fill(row, null);
                }
            }
        }
    }
}

