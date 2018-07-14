package io.github.xantorohara.rocket_table.readers;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public interface Reader {
    void read(File file, String encoding,
              Consumer<String[]> columnsConsumer, Consumer<Object[]> rowsConsumer) throws IOException;
}
