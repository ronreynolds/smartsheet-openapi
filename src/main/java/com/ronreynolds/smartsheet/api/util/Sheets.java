package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.ApiClient;
import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.RowsApi;
import com.ronreynolds.smartsheet.api.SheetsApi;
import com.ronreynolds.smartsheet.model.Column;
import com.ronreynolds.smartsheet.model.Row;
import com.ronreynolds.smartsheet.model.Sheet;
import com.ronreynolds.smartsheet.model.SheetExclude;
import com.ronreynolds.smartsheet.model.SheetInclude;
import com.ronreynolds.smartsheet.model.SheetListingDataInner;
import com.ronreynolds.util.assertions.State;
import lombok.NonNull;

import java.time.OffsetDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * a collection of utility methods for working with Sheets (and Rows and Cells); note, heavily commented out as i need
 * to rework most of this code to work with the OpenAPI-generated code instead of the Smartsheet-SDK code
 */
@SuppressWarnings("unused")
public class Sheets {
    public static Row addRow(@NonNull ApiClient client, long sheetId, @NonNull Supplier<Row> rowProvider) throws ApiException {
        List<Row> rows = addRows(client, sheetId, List.of(rowProvider.get()), null);
        return rows.isEmpty() ? null : rows.get(0);
    }

    @NonNull
    public static List<Row> addRows(@NonNull ApiClient client, long sheetId, @NonNull List<Row> rowData, Consumer<List<Row>> cb)
            throws ApiException {
        var response = new RowsApi(client).rowsAddToSheet(sheetId, null, null, null, null, rowData);
        State.notNull(response, "null response from rowsAddToSheet");
        var responseResult = State.notNull(response.getResult(), "null response result");
        assertEqualRowCounts(rowData.size(), responseResult.size());
        // convert the response result into Rows
        List<Row> newRows = responseResult.stream().map(Converters::convert).collect(Collectors.toList());
        if (cb != null) {
            cb.accept(newRows);
        }
        return newRows;
    }

    @NonNull
    public static List<Row> updateRows(@NonNull ApiClient client, long sheetId, @NonNull List<Row> rowData,
                                       Consumer<List<Row>> cb) throws ApiException {
        // be sure all forbidden fields are cleared
        // InvalidRequestException: The attribute(s) row.rowNumber, row.createdAt, row.modifiedAt, row.sheetId are not allowed for this operation.
        rowData.forEach((row) -> {
            row.setRowNumber(null);
            row.setCreatedAt(null);
            row.setModifiedAt(null);
            row.setSheetId(null);
        });
        var response = new RowsApi(client).updateRows(sheetId, null, null, null, null, rowData);
        State.notNull(response, "null response from updateRows");
        var responseResult = State.notNull(response.getResult(), "null response result");
        assertEqualRowCounts(rowData.size(), responseResult.size());
        List<Row> newRows = response.getResult().stream().map(Converters::convert).collect(Collectors.toList());
        newRows.forEach(row -> row.setSheetId(sheetId));    // not in the update rows response?
        if (cb != null) {
            cb.accept(newRows);
        }
        return newRows;
    }

    @NonNull
    public static String columnInfo(@NonNull Sheet sheet) {
        StringBuilder buf = new StringBuilder();
        for (Column column : sheet.getColumns()) {
            buf.append(String.format("col[%d]={title:%s index:%d primary:%s}", column.getId(), column.getTitle(),
                    column.getIndex(), column.getPrimary()));

//            column.getAutoNumberFormat()
//            column.getFilter()
//            column.getOptions()
//            column.getSymbol()
        }
        return buf.toString();
    }

    static final List<SheetInclude> ALL_SHEET_INCLUSIONS = List.copyOf(EnumSet.allOf(SheetInclude.class));
    static final List<SheetExclude> NO_SHEET_EXCLUSIONS = List.of();
    static final List<Long> ALL_COLUMN_IDS = null;
    static final String ALL_FILTERS = null;
    static final Integer NO_PAGE_SIZE_LIMIT = null;
    static final Integer ALL_PAGE_NUMBERS = null;
    static final List<Long> ALL_ROW_IDS = null;
    static final List<Integer> ALL_ROW_NUMBERS = null;
    static final OffsetDateTime ROWS_MODIFIED_SINCE = OffsetDateTime.now();

    public static Sheet getWholeSheet(@NonNull ApiClient client, long sheetId) throws ApiException {
        return new SheetsApi(client)
                .getSheet(sheetId, null, null, ALL_SHEET_INCLUSIONS, NO_SHEET_EXCLUSIONS, ALL_COLUMN_IDS, ALL_FILTERS, null, null,
                        NO_PAGE_SIZE_LIMIT, ALL_PAGE_NUMBERS, null, ALL_ROW_IDS, ALL_ROW_NUMBERS, ROWS_MODIFIED_SINCE)
                .getSheet();
    }

    public static Sheet getSheetNoRows(@NonNull ApiClient client, long sheetId) throws ApiException {
        // FIXME - need to support a comma-sep list of enum values; not just a single value (or null)
        return new SheetsApi(client)
                .getSheet(sheetId, null, null, List.of(SheetInclude.COLUMN_TYPE), List.of(SheetExclude.LINK_IN_FROM_CELL_DETAILS),
                        ALL_COLUMN_IDS, ALL_FILTERS, null, null, NO_PAGE_SIZE_LIMIT, ALL_PAGE_NUMBERS, null, ALL_ROW_IDS, ALL_ROW_NUMBERS, null)
                .getSheet();
    }

    @NonNull
    public static List<SheetListingDataInner> findByName(@NonNull ApiClient client, @NonNull String sheetName)
            throws ApiException {
        return new SheetsApi(client)
                .listSheets(null, null, true, null, false, null, null).getData().stream()
                .filter(sheet -> sheetName.equals(sheet.getName()))
                .collect(Collectors.toList());
    }

    /**
     * remove all the rows from the specified sheet
     */
    public static void clearRows(@NonNull ApiClient client, @NonNull Sheet sheet) throws ApiException {
        // check if sheet already has no rows;
        // otherwise this call fails with "InvalidRequestException: A required parameter is missing from your request: ids."
        List<Long> rowIds = sheet.getRows().stream().map(Row::getId).collect(Collectors.toList());
        if (!rowIds.isEmpty()) {
            new RowsApi(client).deleteRows(sheet.getId(), rowIds, true);
        }
    }

    private static void assertEqualRowCounts(int expected, int actual) {
        State.isTrue(expected == actual, "%s rows sent, only %s returned", expected, actual);
    }
/*
    @NonNull
    public static Sheet copyAndRefresh(@NonNull SheetsApi client, @NonNull Sheet original,
                                       Folder folder, @NonNull String newSheetName) throws ApiException {
        ContainerDestination destination = new ContainerDestination();
        if (folder != null) {
            destination.setDestinationId(folder.getId());
            destination.setDestinationType(folder instanceof Workspace ? DestinationType.WORKSPACE : DestinationType.FOLDER);
        }
        destination.setNewName(newSheetName);
        Sheet newSheet = client.sheetResources().copySheet(original.getId(), destination, Constants.ALL_SHEET_COPY_INCLUSIONS);
        long newSheetId = newSheet.getId();
        return Objects.requireNonNull(Sheets.getSheetNoRows(client, newSheetId), "failed to find cloned sheet id:" + newSheetId);
    }

    @Deprecated // use Rows.clearLocations(Row) instead
    public static void clearLocations(@NonNull Row row) {
        Rows.clearLocations(row);
    }
*/
}
