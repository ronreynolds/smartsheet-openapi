package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.model.Column;
import com.ronreynolds.smartsheet.model.Sheet;
import com.ronreynolds.util.assertions.State;
import lombok.NonNull;

import java.util.Map;
import java.util.stream.Collectors;

public class Columns {
    private Columns() {
    }

    /**
     * @param sheet - sheet from which we want a map of all the columns by title
     * @return a Map of all columns in the specified sheet by column title
     */
    @NonNull
    public static Map<String, Column> buildColumnByTitleMap(@NonNull Sheet sheet) {
        return State.notNull(sheet.getColumns(), "sheet has null columns")
                .stream()
                .collect(Collectors.toMap(Column::getTitle, v -> v));
    }
}