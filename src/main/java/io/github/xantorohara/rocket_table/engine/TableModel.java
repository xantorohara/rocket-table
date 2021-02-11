package io.github.xantorohara.rocket_table.engine;

import au.com.bytecode.opencsv.CSVWriter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
public class TableModel {

    //todo make private
    public final IntegerProperty matchedRowCountProperty = new SimpleIntegerProperty();
    public final IntegerProperty uniqueRowCountProperty = new SimpleIntegerProperty();
    public final IntegerProperty totalRowCountProperty = new SimpleIntegerProperty();
    public final IntegerProperty totalColumnsProperty = new SimpleIntegerProperty();
    public final ObservableList<SmartRow> items = FXCollections.observableArrayList();

    private boolean uniqueRows = false;
    private boolean filterRows = false;

    private TableResource tableResource;

    private SmartColumns mappedColumns;
    private int[] mappedColumnIndexes;

    public SmartColumns getAllColumns() {
        return tableResource.getColumns();
    }

    public SmartColumns getColumns() {
        return mappedColumns;
    }

    public void setFilterRows(boolean filterRows) {
        this.filterRows = filterRows;
        updateTableData();
    }

    public void setUniqueRows(boolean uniqueRows) {
        this.uniqueRows = uniqueRows;
        updateTableData();
    }

    public void load(File file, String encoding, String sasDateFormatType) throws IOException {
        log.info("Load [{}]", file);
        tableResource = new TableResource();
        tableResource.read(file, encoding, sasDateFormatType);
        reset();
    }

    public void truncate() {
        log.info("Truncate");
        if (tableResource == null) {
            return;
        }

        tableResource.setData(new ArrayList<>(items));
        tableResource.setColumns(mappedColumns);
        reset();
    }

    public void truncate(List<SmartRow> selected) {
        log.info("Truncate selected");
        if (tableResource == null) {
            return;
        }

        tableResource.setData(new ArrayList<>(selected));
        tableResource.setColumns(mappedColumns);
        reset();
    }

    private void reset() {
        items.setAll(tableResource.getData());
        totalRowCountProperty.set(tableResource.getData().size());
        uniqueRowCountProperty.set((int) tableResource.getData().stream().distinct().count());
        matchedRowCountProperty.set(0);
        resetColumnsMapping();
    }

    private void resetColumnsMapping() {
        mappedColumnIndexes = null;
        mappedColumns = tableResource.getColumns();
        totalColumnsProperty.setValue(tableResource.getColumns().getCount());
    }

    public boolean setColumns(String columnsString) {
        log.info("Set columns");
        if (tableResource == null) {
            return false;
        }

        if (columnsString.trim().isEmpty()) {
            resetColumnsMapping();
            updateTableData();
            return true;
        }
        String[] columns = tableResource.getColumns().getNames();
        String[] columnsToMap = columnsString.split(",");

        List<Integer> columnIndexes = new ArrayList<>();
        for (String columnToMap : columnsToMap) {
            columnToMap = columnToMap.trim();
            for (int i = 0; i < columns.length; i++) {
                if (columns[i].equalsIgnoreCase(columnToMap)) {
                    columnIndexes.add(i);
                    break;
                }
            }
        }

        SmartColumns newMappedColumns;
        if (columnIndexes.isEmpty()) {
            mappedColumnIndexes = new int[0];
            newMappedColumns = new SmartColumns(new String[0]);
        } else {
            mappedColumnIndexes = columnIndexes.stream().mapToInt(i -> i).toArray();
            newMappedColumns = tableResource.getColumns().map(mappedColumnIndexes);
        }

        if (newMappedColumns.equals(mappedColumns)) {
            return false;
        } else if (newMappedColumns.equals(tableResource.getColumns())) {
            resetColumnsMapping();
            updateTableData();
        } else if (newMappedColumns.getNames().length == 0) {
            mappedColumns = newMappedColumns;
            items.clear();
        } else {
            mappedColumns = newMappedColumns;
            updateTableData();
        }
        return true;
    }

    public void search(String searchString) {
        search(searchString, filterRows);
    }

    public void search(String searchString, boolean filterRows) {
        log.info("Search [{}]", searchString);
        this.filterRows = filterRows;
        if (tableResource == null) {
            return;
        }

        match(searchString);
        updateTableData();
    }

    private void updateTableData() {
        List<SmartRow> out;

        Stream<SmartRow> stream = tableResource.getData().stream();

        if (filterRows) {
            if (matchedRowCountProperty.get() > 0) {
                stream = stream.filter(r -> r.isMatched());
            } else {
                stream = Stream.empty();
            }
        }

        if (mappedColumnIndexes != null) {
            stream = stream.map(r -> r.map(mappedColumnIndexes));
        }

        if (uniqueRows) {
            out = stream.distinct().collect(toList());
            uniqueRowCountProperty.set(out.size());
        } else {
            if (!filterRows && mappedColumnIndexes == null) {
                out = tableResource.getData();
                uniqueRowCountProperty.set((int) stream.distinct().count());
            } else {
                out = stream.collect(toList());
                uniqueRowCountProperty.set((int) out.stream().distinct().count());
            }
        }
        items.setAll(out);
    }

    private void match(String searchString) {
        Map<String, Set<SmartPattern>> columnStringsMap = SmartMatcher.splitSearchString(searchString);

        if (columnStringsMap.containsKey(SmartMatcher.NONE_MATCH)) {
            for (SmartRow smartRow : tableResource.getData()) {
                smartRow.clearMatched();
            }
            matchedRowCountProperty.set(0);
            return;
        } else if (columnStringsMap.containsKey(SmartMatcher.ALL_MATCH)) {
            for (SmartRow smartRow : tableResource.getData()) {
                smartRow.setMatched();
            }
            matchedRowCountProperty.set(tableResource.getData().size());
            return;
        }

        SmartPattern[] globalPatterns = null;
        SmartPattern[][] columnPatterns = null;

        if (columnStringsMap.containsKey(SmartMatcher.GLOBAL_KEY)) {
            Set<SmartPattern> patterns = columnStringsMap.get(SmartMatcher.GLOBAL_KEY);
            globalPatterns = patterns.toArray(new SmartPattern[patterns.size()]);
        } else {
            String[] columns = tableResource.getColumns().getNames();
            columnPatterns = new SmartPattern[columns.length][];
            for (int i = 0; i < columns.length; i++) {
                Set<SmartPattern> patterns = columnStringsMap.get(columns[i].toUpperCase());
                columnPatterns[i] = patterns == null ? null : patterns.toArray(new SmartPattern[patterns.size()]);
            }
        }

        int matchedRowCount = 0;

        for (SmartRow smartRow : tableResource.getData()) {
            smartRow.clearMatched();
            for (int j = 0; j < smartRow.getValues().length; j++) {
                String value = smartRow.getValue(j);
                if (globalPatterns != null) {
                    if (SmartMatcher.stringMatches(value, globalPatterns)) {
                        smartRow.setMatched(j);
                    }
                } else if (columnPatterns[j] != null) {
                    if (SmartMatcher.stringMatches(value, columnPatterns[j])) {
                        smartRow.setMatched(j);
                    } else {
                        smartRow.clearMatched();
                        break;
                    }
                }
            }
            if (smartRow.isMatched()) {
                matchedRowCount++;
            }
        }

        matchedRowCountProperty.set(matchedRowCount);
        log.info("Matched [{}] records", matchedRowCount);
    }

    public void export(File file) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            writer.writeNext(getColumns().getNames());
            for (SmartRow strings : items) {
                writer.writeNext(strings.getValues());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
