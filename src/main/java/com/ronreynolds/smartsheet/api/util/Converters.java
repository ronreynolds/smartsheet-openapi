package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.model.Cell;
import com.ronreynolds.smartsheet.model.CellLite;
import com.ronreynolds.smartsheet.model.Row;
import com.ronreynolds.smartsheet.model.RowsAddToSheet200ResponseAllOfResultInner;
import com.ronreynolds.smartsheet.model.UpdateRows200ResponseAllOfResultInner;

import java.util.List;
import java.util.stream.Collectors;

/**
 * a collection of static methods to convert from one type to another
 */
public class Converters {
    private Converters() {
    }
    public static Row convert(RowsAddToSheet200ResponseAllOfResultInner result) {
        return new Row()
                .id(result.getId())
                .sheetId(result.getSheetId())
                .rowNumber(result.getRowNumber())
                .version(result.getVersion())
                .expanded(result.getExpanded())
                .createdAt(result.getCreatedAt())
                .modifiedAt(result.getModifiedAt())
                .cells(convert(result.getCells()));
    }

    public static Row convert(UpdateRows200ResponseAllOfResultInner result) {
        return new Row()
                .id(result.getId())
//                .sheetId(result.getSheetId()) - not available in response according to openapi spec :-?
                .rowNumber(result.getRowNumber())
                .version(result.getVersion())
                .expanded(result.getExpanded())
                .createdAt(result.getCreatedAt())
                .modifiedAt(result.getModifiedAt())
                .cells(convert(result.getCells()));
    }


    public static List<Cell> convert(List<CellLite> cellLiteList) {
        if (cellLiteList == null) {
            return null;
        }
        return cellLiteList.stream().map(Converters::convert).collect(Collectors.toList());
    }

    public static Cell convert(CellLite cellLite) {
        return new Cell()
                .columnId(cellLite.getColumnId())
                .columnType(cellLite.getColumnType())
                .value(cellLite.getValue())
                .displayValue(cellLite.getDisplayValue());
    }
}
