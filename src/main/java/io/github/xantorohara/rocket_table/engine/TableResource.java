package io.github.xantorohara.rocket_table.engine;

import io.github.xantorohara.rocket_table.readers.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Slf4j
public class TableResource {

    private SmartColumns columns;

    private List<SmartRow> data;

    private String[] convert(Object[] row) {
        String[] out = row instanceof String[] ? (String[]) row : new String[row.length];

        for (int i = 0; i < row.length && i < columns.getCount(); i++) {
            Class type = null;
            if (row[i] == null) {
                out[i] = "";
            } else if (row[i] instanceof Boolean) {
                out[i] = (boolean) row[i] ? "TRUE" : "FALSE";
                type = String.class;
            } else if (row[i] instanceof Date) {
                out[i] = SmartUtils.toString((Date) row[i]);
                type = String.class;
            } else {
                if (row[i] instanceof String) {
                    out[i] = (String) row[i];
                } else {
                    out[i] = row[i].toString();
                }
                if (columns.getType(i) == null || columns.getType(i) == Number.class) {
                    if (row[i] instanceof Number) {
                        type = Number.class;
                    } else {
                        try {
                            double ignore = Double.parseDouble(out[i]);
                            type = Number.class;
                        } catch (NumberFormatException e) {
                            type = String.class;
                        }
                    }
                }
            }
            columns.setType(i, type);
        }
        return out;
    }

    public void read(File file, String encoding, String sasDateFormatType) throws IOException {
        columns = null;
        data = new ArrayList<>();

        String fileName = file.getName();

        Reader reader = null;
        if (fileName.endsWith(".sas7bdat")) {
            reader = new SasReader(encoding, sasDateFormatType);
        } else if (fileName.endsWith(".sbdf")) {
            reader = new SbdfReader();
        } else if (fileName.endsWith(".stdf")) {
            reader = new StdfReader();
        } else if (fileName.endsWith(".csv")) {
            reader = new CsvReader();
        }

        if (reader != null) {
            AtomicInteger i = new AtomicInteger();
            reader.read(file,
                    cols -> columns = new SmartColumns(cols),
                    row -> {
                        data.add(new SmartRow(i.getAndIncrement(), convert(row)));
                        if (i.get() % 100000 == 0) {
                            log.debug("Loaded [{}] rows", i.get());
                        }
                    }
            );
        }

        log.info("Finally loaded [{}] records", data.size());
    }
}