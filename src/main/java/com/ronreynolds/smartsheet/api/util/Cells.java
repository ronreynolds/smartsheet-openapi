package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.ApiClient;
import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.RowsApi;
import com.ronreynolds.smartsheet.model.Cell;
import com.ronreynolds.smartsheet.model.CellValue;
import com.ronreynolds.smartsheet.model.Column;
import com.ronreynolds.smartsheet.model.Row;
import com.ronreynolds.smartsheet.model.Sheet;
import com.ronreynolds.util.assertions.State;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * a collection of utility functions for working with Cell model objects
 */
public class Cells {
    @SuppressWarnings("unchecked")
    public enum ValueType {
        STRING {
            @Override
            <T> T getValue(CellValue value) {
                return (T) value.getString();
            }
        },
        NUMBER {
            @Override
            <T> T getValue(CellValue value) {
                return (T) value.getBigDecimal();
            }
        },
        BOOLEAN {
            @Override
            <T> T getValue(CellValue value) {
                return (T) value.getBoolean();
            }
        },
        NULL {
            @Override
            <T> T getValue(CellValue value) {
                return null;
            }
        };

        public static ValueType getValueType(CellValue value) {
            Object actualValue = value.getActualInstance();
            if (actualValue == null) {
                return NULL;    // not sure if this is possible but good to be prepared
            }
            if (actualValue instanceof Boolean) {
                return BOOLEAN;
            }
            if (actualValue instanceof String) {
                return STRING;
            }
            if (actualValue instanceof BigDecimal) {
                return NUMBER;
            }
            throw new IllegalStateException("unrecognized value type - " + actualValue);
        }

        abstract <T> T getValue(CellValue value);
    }

    private Cells() {
    }

    public static CellValue makeCellValue(Object value) {
        CellValue cellValue = new CellValue();
        cellValue.setActualInstance(value);
        return cellValue;
    }

    /**
     * @param client   - client to use to access the Smartsheet API
     * @param sheetId  - ID of sheet into which we want to set the cell value
     * @param rowId    - ID of the row in the sheet into which we want to set the cell value
     * @param columnId - ID of the column in the row in the sheet in which we want to set the cell value
     * @param value    - the value we want to set into the cell in the column in the row in the sheet that Jack built. :)
     * @return the updated row
     * @throws ApiException if anything goes wrong
     */
    @NonNull
    public static List<Row> setCellValue(@NonNull ApiClient client, long sheetId, long rowId, long columnId, Object value)
            throws ApiException {
        Row updatedRow = new Row().id(rowId);
        List<Cell> cell = Collections.singletonList(
                new Cell()
                        .columnId(columnId)
                        .value(new CellValue().actualInstance(value))
                        .strict(true)
                        .hyperlink(null)
                        .linkInFromCell(null));
        updatedRow.setCells(cell);
        var response = new RowsApi(client).updateRows(sheetId, null, null, null, null, Collections.singletonList(updatedRow));
        State.notNull(response, "null response from updateRows");
        var result = State.notNull(response.getResult());
        State.isTrue(result.size() == 1, "update FAILED - row:%d column:%d value:'%s'", rowId, columnId, value);
        return result.stream().map(Converters::convert).collect(Collectors.toList());
    }

    /**
     * get the Cell for the specified column the provided row
     *
     * @param row      is the row from which we want a Cell
     * @param columnId is the column id for the Cell we want
     * @return the Cell in the Row for the columnId or Optional.empty if none is found
     */
    public static Optional<Cell> getCellForColumn(Row row, long columnId) {
        return State.notNull(row.getCells(), "row returned null cells list").stream()
                .filter(cell -> State.notNull(cell.getColumnId(), "cell has null column-id") == columnId)
                .findFirst();
    }

    /**
     * determine if a Cell in the provided row and column match the provided match criteria
     *
     * @param row       is the row for which we want to check a cell value
     * @param columnId  is the column of the cell we want to check
     * @param cellMatch is the match criteria by which we determine if the cell for the column in the row matches
     * @return true if the specified row's column's cell matches; false if not or no cell found for columnId in row
     */
    public static boolean rowHasMatchingCell(Row row, long columnId, Predicate<? super Cell> cellMatch) {
        return getCellForColumn(row, columnId).stream().anyMatch(cellMatch);
    }

    /**
     * set the values of many cells in a single row given a map of column ID and cell value
     *
     * @param client         is to access the Smartsheet API
     * @param sheetId        is the ID of the sheet with the row we want to update
     * @param rowId          is the ID of teh row we want to update in the sheet
     * @param columnValueMap is a collection of (columnId,cellValue) pairs to be applied to the specified row
     * @return the list of Rows updated
     * @throws ApiException if anything goes wrong talking to the API
     */
    public static List<Row> setCellValues(@NonNull ApiClient client, long sheetId, long rowId, Map<Long, Object> columnValueMap)
            throws ApiException {
        Row updatedRow = new Row()
                .id(rowId)
                .cells(columnValueMap.entrySet().stream()
                        .map(entry -> new Cell().columnId(entry.getKey()).value(makeCellValue(entry.getValue())).strict(true)
                                .hyperlink(null).linkInFromCell(null))
                        .collect(Collectors.toList()));
        var response = new RowsApi(client).updateRows(sheetId, null, null, null, null, List.of(updatedRow));
        var result = State.notNull(response.getResult());
        State.isTrue(result.size() == 1, "failed to update row");
        return result.stream().map(Converters::convert).collect(Collectors.toList());
    }

    /**
     * @param sheet - sheet from which we get the Column info
     * @return a Stream of row cell values mapped by column title
     */
    public static Stream<Map<String, Object>> getCellValuesByNameStream(@NonNull Sheet sheet) {
        List<Row> rowList = State.notNull(sheet.getRows(), "sheet has null rows");
        // for each row get the list of cells
        return rowList.stream()
                .map(Cells::cellsByColumnId)
                .map(cellMap -> {
                    Map<String, Object> cellValueMap = new HashMap<>();
                    for (var column : State.notNull(sheet.getColumns(), "sheet has null columns")) {
                        Cell cell = cellMap.get(column.getId());
                        cellValueMap.put(column.getTitle(), cell != null ? cell.getValue() : null);
                    }
                    return cellValueMap;
                });
    }

    /**
     * @param row from which to extract cells
     * @return Map of cells by their column-id
     */
    @NonNull
    public static Map<Long, Cell> cellsByColumnId(@NonNull Row row) {
        return row.getCells().stream().collect(Collectors.toMap(Cell::getColumnId, Function.identity()));
    }

    /**
     * @return the displayed value of the Cell as a string
     * @throws NoSuchElementException if the Cell can't be found
     */
    public static String getCellDisplayValue(Row row, Column column) {
        return getCellForColumn(row, column.getId()).orElseThrow().getDisplayValue();
    }

    /**
     * @return the raw value of the Cell as whatever type is expectec by the caller
     * @throws NoSuchElementException if the Cell can't be found
     * @throws ClassCastException     if the Cell's raw type doesn't match the expected type
     */
    @SuppressWarnings("unchecked")
    public static <T> T getCellValue(Row row, Column column) {
        return (T) getCellForColumn(row, column.getId()).orElseThrow().getValue();
    }

    /**
     * @return the raw value of the Cell converted via the callback into the type expected by the caller
     * @throws NoSuchElementException if the Cell can't be found
     */
    public static <T> T getCellValue(Row row, Column column, Function<Object, T> converter) {
        return converter.apply(getCellValue(row, column));
    }
}
