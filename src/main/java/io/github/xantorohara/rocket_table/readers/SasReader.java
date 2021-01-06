package io.github.xantorohara.rocket_table.readers;

import com.epam.parso.Column;
import com.epam.parso.date.OutputDateType;
import com.epam.parso.impl.SasFileReaderImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

@Slf4j
public class SasReader implements Reader {
    private final String encoding;
    private final String dateFormatType;

    public SasReader(String encoding, String dateFormatType) {
        this.encoding = encoding;
        this.dateFormatType = dateFormatType;
    }

    @Override
    public void read(File file, Consumer<String[]> columnsConsumer,
                     Consumer<Object[]> rowsConsumer) throws IOException {
        log.info("Open SAS7BDAT file using encoding [{}] and date format type [{}]",
                encoding, dateFormatType);
        try (InputStream is = new FileInputStream(file)) {
            OutputDateType sasDateFormatType = dateFormatType == null ? null
                    : OutputDateType.valueOf(dateFormatType);
            SasFileReaderImpl reader = new SasFileReaderImpl(is, encoding, sasDateFormatType);

            columnsConsumer.accept(reader.getColumns().stream().map(Column::getName).toArray(String[]::new));

            Object[] row;
            while ((row = reader.readNext()) != null) {
                rowsConsumer.accept(row);
            }
        }
    }
}
