package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.SheetsApi;
import com.ronreynolds.smartsheet.model.Column;
import com.ronreynolds.smartsheet.model.DateUnion;
import com.ronreynolds.smartsheet.model.Sheet;
import com.ronreynolds.smartsheet.model.SheetExclude;
import com.ronreynolds.smartsheet.model.SheetInclude;
import com.ronreynolds.smartsheet.model.SheetListingDataInner;
import lombok.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * a collection of utility methods for working with Sheets (and Rows and Cells); note, heavily commented out as i need
 * to rework most of this code to work with the OpenAPI-generated code instead of the Smartsheet-SDK code
 */
@SuppressWarnings("unused")
public class Sheets {
    static String LINE_DELIMITER = "";    // no line-breaks by default

/*
    @NonNull
    public static CharSequence toString(@NonNull Sheet sheet) {
        try {
            return toString(sheet, null);
        } catch (ApiException e) {
            throw new IllegalStateException("should be impossible", e);
        }
    }

    @NonNull
    public static CharSequence toString(@NonNull Sheet sheet, SheetsApi client, ToStringOptions... options)
            throws ApiException {
        StringBuilder buf = new StringBuilder();
        buf.append(String.format("{{id:%d%s name:'%s'%s rowCount:%d%s version:%d%s owner:%s(%d)%s source:%s%s accessLevel:%s%s " +
                        "readOnly:%s%s link:%s%s ganttEnabled:%s%s dependEnabled:%s%s resMgmntEnabled:%s%s favorite:%s%s}%n",
                sheet.getId(), LINE_DELIMITER, sheet.getName(), LINE_DELIMITER, sheet.getTotalRowCount(), LINE_DELIMITER,
                sheet.getVersion(), LINE_DELIMITER, sheet.getOwner(), sheet.getOwnerId(), LINE_DELIMITER,
                sheet.getSource(), LINE_DELIMITER, sheet.getAccessLevel(), LINE_DELIMITER, sheet.getReadOnly(),
                LINE_DELIMITER, sheet.getPermalink(), LINE_DELIMITER, sheet.getGanttEnabled(), LINE_DELIMITER,
                sheet.getDependenciesEnabled(), LINE_DELIMITER, sheet.getResourceManagementEnabled(), LINE_DELIMITER,
                sheet.getFavorite(), LINE_DELIMITER));
        if (options != null && options.length > 0) {
            for (ToStringOptions option : options) {
                if (option != null) {
                    option.appendData(buf, sheet, client);
                }
            }
        }

        buf.append("}");
        return buf;
    }

    public static Row addRow(@NonNull SheetsApi client, long sheetId, @NonNull Supplier<Row> rowProvider) throws ApiException {
        List<Row> rows = addRows(client, sheetId, Collections.singletonList(rowProvider.get()), null);
        return rows.isEmpty() ? null : rows.get(0);
    }
*/
/*

    @NonNull
    public static List<Row> addRows(@NonNull SheetsApi client, long sheetId, @NonNull List<Row> rowData,
                                    Consumer<List<Row>> cb) throws ApiException {
        List<Row> newRows = client.sheetResources().rowResources().addRows(sheetId, rowData);
        Preconditions.checkState(rowData.size() == newRows.size(), "%s rows sent, only %s returned", rowData.size(),
                newRows.size());
        if (cb != null) {
            cb.accept(newRows);
        }
        return newRows;
    }

    @NonNull
    public static List<Row> updateRows(@NonNull SheetsApi client, long sheetId, @NonNull List<Row> rowData,
                                       Consumer<List<Row>> cb) throws ApiException {
        // be sure all forbidden fields are cleared
        // InvalidRequestException: The attribute(s) row.rowNumber, row.createdAt, row.modifiedAt, row.sheetId are not allowed for this operation.
        rowData.forEach((row) -> {
            row.setRowNumber(null);
            row.setCreatedAt(null);
            row.setModifiedAt(null);
            row.setSheetId(null);
        });
        List<Row> newRows = client.sheetResources().rowResources().updateRows(sheetId, rowData);
        Preconditions.checkState(rowData.size() == newRows.size(), "%s rows sent, only %s returned", rowData.size(),
                newRows.size());
        if (cb != null) {
            cb.accept(newRows);
        }
        return newRows;
    }
*/

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

    static final String AUTH_IN_CLIENT = null;
    static final SheetInclude ALL_SHEET_INCLUSIONS = null;
    static final SheetExclude NO_SHEET_EXCLUSIONS = null;
    static final String ALL_COLUMN_IDS = null;
    static final String ALL_FILTERS = null;
    static final Integer NO_PAGE_SIZE_LIMIT = null;
    static final Integer ALL_PAGE_NUMBERS = null;
    static final String ALL_ROW_IDS = null;
    static final String ALL_ROW_NUMBERS = null;
    static final DateUnion ROWS_MODIFIED_SINCE = new DateUnion(0L);

    public static Sheet getWholeSheet(@NonNull SheetsApi client, long sheetId) throws ApiException {
        return client.getSheet(sheetId, AUTH_IN_CLIENT, null, null, ALL_SHEET_INCLUSIONS, NO_SHEET_EXCLUSIONS, ALL_COLUMN_IDS,
                        ALL_FILTERS, null, null, NO_PAGE_SIZE_LIMIT, ALL_PAGE_NUMBERS, null, ALL_ROW_IDS, ALL_ROW_NUMBERS, 
                        ROWS_MODIFIED_SINCE)
                .getSheet();
    }

    public static Sheet getSheetNoRows(@NonNull SheetsApi client, long sheetId) throws ApiException {
        // FIXME - need to support a comma-sep list of enum values; not just a single value (or null)
        return client.getSheet(sheetId, AUTH_IN_CLIENT, null, null, SheetInclude.COLUMN_TYPE, SheetExclude.LINK_IN_FROM_CELL_DETAILS,
                        ALL_COLUMN_IDS, ALL_FILTERS, null, null, NO_PAGE_SIZE_LIMIT, ALL_PAGE_NUMBERS, null, ALL_ROW_IDS, ALL_ROW_NUMBERS, null)
                .getSheet();
    }

    @NonNull
    public static List<SheetListingDataInner> findByName(@NonNull SheetsApi client, @NonNull String sheetName) throws ApiException {
        return Objects.requireNonNull(
                        client.listSheets(null, AUTH_IN_CLIENT, null, true, null, false, null, null).getData())
                .stream()
                .filter(sheet -> sheetName.equals(sheet.getName()))
                .collect(Collectors.toList());
    }

    /**
     * remove all the rows from the specified sheet
     */
/*
    public static void clearRows(@NonNull SheetsApi client, @NonNull Sheet sheet) throws ApiException {
        // check if sheet already has no rows;
        // otherwise this call fails with "InvalidRequestException: A required parameter is missing from your request: ids."
        Set<Long> rowIds = sheet.getRows().stream().map(Row::getId).collect(Collectors.toSet());
        if (!rowIds.isEmpty()) {
            client.sheetResources().rowResources().deleteRows(sheet.getId(), rowIds, true);
        }
    }
*/

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

/*
    public enum ToStringOptions {
        WITH_COLUMNS {
            @Override
            void appendData(@NonNull StringBuilder buf, @NonNull Sheet sheet, @NonNull SheetsApi client) throws ApiException {
                ColumnsApi columnsApi = new ColumnsApi(ApiClients.getDefaultClient());
                var columnPage = columnsApi.columnsListOnSheet(sheet.getId(), null, Constants.ALL_PAGES);
                List<Column> columns = columnPage.getData();
                buf.append(String.format("%ncolumns:{num:%d data:{%n", columns.size()));
                for (Column col : columns) {
                    buf.append(col.getId()).append(":'").append(col.getTitle()).append("',").append(LINE_DELIMITER);
                }
                buf.append("}}");
            }
        },
        WITH_ROW_CONTENT {
            @Override
            void appendData(@NonNull StringBuilder buf, @NonNull Sheet sheet, @NonNull SheetsApi client) throws ApiException {
                buf.append("{rows:{");
                for (Row row : sheet.getRows()) {
                    buf.append(row.getId()).append(":[");
                    for (Cell cell : row.getCells()) {
                        buf.append(cell.getValue()).append(',').append(LINE_DELIMITER);
                    }
                    buf.append("],\n");
                }
                buf.append("}}\n");
            }
        }, ALL {
            @Override
            void appendData(@NonNull StringBuilder buf, @NonNull Sheet sheet, @NonNull SheetsApi client) throws ApiException {
                WITH_COLUMNS.appendData(buf, sheet, client);
                WITH_ROW_CONTENT.appendData(buf, sheet, client);
            }
        };

        abstract void appendData(@NonNull StringBuilder buf, @NonNull Sheet sheet, @NonNull SheetsApi client) throws ApiException;
    }
*/
}
