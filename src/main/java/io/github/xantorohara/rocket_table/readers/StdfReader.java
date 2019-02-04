package io.github.xantorohara.rocket_table.readers;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StdfReader implements Reader {
    private final Pattern propRegex = Pattern.compile(" property=(.+?);");
    private final Pattern valueRegex = Pattern.compile(" value=.+;");
    private final Base64.Decoder base64 = Base64.getDecoder();
    private final Charset utf8 = Charset.forName("UTF-8");

    @Override
    public void read(File file, String encoding,
                     Consumer<String[]> columnsConsumer, Consumer<Object[]> rowsConsumer) throws IOException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), utf8))) {
            int ignore = br.read();
            String line;
            int propNoValIndex = 0;
            Integer nameIndex = null;
            Integer typeIndex = null;

            while (true) {
                line = br.readLine();
                if (line == null) {
                    throw new IOException("Invalid file header");
                } else if (line.startsWith("\\! ")) {
                    Matcher matcher = propRegex.matcher(line);
                    if (matcher.find() && !valueRegex.matcher(line).find()) {
                        if ("Name".equals(matcher.group(1))) {
                            nameIndex = propNoValIndex;
                        } else if ("DataType".equals(matcher.group(1))) {
                            typeIndex = propNoValIndex;
                        }
                        propNoValIndex++;
                    }
                } else {
                    break;
                }
            }

            if (nameIndex == null) {
                throw new IOException("Column name property found");
            }

            if (typeIndex == null) {
                throw new IOException("Column type property not found");
            }

            String[] colNames = null;
            String[] colTypes = null;
            for (int i = 0; i < propNoValIndex; i++) {
                if (i > 0) {
                    line = br.readLine();
                    if (line == null) {
                        throw new IOException("Invalid file header");
                    }
                }

                line = line.substring(0, line.length() - 1);
                if (i == nameIndex) {
                    colNames = line.split(";");
                } else if (i == typeIndex) {
                    colTypes = line.split(";");
                }
            }

            if (colNames == null) {
                throw new IOException("Column names not found");
            }

            if (colTypes == null) {
                throw new IOException("Column types not found");
            }

            columnsConsumer.accept(colNames);

            while ((line = br.readLine()) != null) {
                line = line.substring(0, line.length() - 1);
                String[] row = line.split(";");

                Object[] parsedRow = new Object[colNames.length];

                for (int j = 0; j < row.length; j++) {
                    String val = row[j];
                    if (!"\\?".equals(val)) {
                        switch (colTypes[j]) {
                            case "String":
                                parsedRow[j] = val.replace("\\s", ";").replace("\\\\", "\\");
                                break;
                            case "Binary":
                                parsedRow[j] = Arrays.stream(val.substring(2).split("\\\\r\\\\n"))
                                        .map(s -> new String(base64.decode(s), utf8))
                                        .collect(Collectors.joining());
                                break;
                            case "Int":
                            case "Integer":
                            case "Long":
//                                parsedRow[j] = val;
//                                parsedRow[j] = Long.parseLong(val);
//                                break;
                            case "Double":
                            case "Real":
//                                parsedRow[j] = val;
//                                parsedRow[j] = Double.parseDouble(val);
//                                break;
                            default:
                                parsedRow[j] = val;
                        }
                    }
                }
                rowsConsumer.accept(parsedRow);
            }
        }
    }
}
