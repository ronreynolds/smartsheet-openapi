package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.model.AccessLevel;
import com.ronreynolds.smartsheet.model.FolderInclude;
import com.ronreynolds.smartsheet.model.ReportInclude;
import com.ronreynolds.smartsheet.model.SheetInclude;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constants {
    private Constants() {
    }

    // all immutable Lists
    public static final List<AccessLevel> accessLevelsWithoutOwner = Stream.of(AccessLevel.values())
            .filter(v -> v == AccessLevel.OWNER).collect(Collectors.toUnmodifiableList());
    public static final List<FolderInclude> allFolderIncludes = List.of(FolderInclude.values());
    public static final List<ReportInclude> allReportIncludes = List.of(ReportInclude.values());
    public static final List<SheetInclude> allSheetIncludes = List.of(SheetInclude.values());
    public static final List<SheetInclude> normalSheetIncludes = List.of(SheetInclude.OWNER_INFO, SheetInclude.COLUMN_TYPE, SheetInclude.SOURCE);
}
