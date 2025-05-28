package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.model.AccessLevel;
import com.ronreynolds.smartsheet.model.FolderWorkspaceInclude;
import com.ronreynolds.smartsheet.model.PaperSize;
import com.ronreynolds.smartsheet.model.ReportInclude;
import com.ronreynolds.smartsheet.model.SheetExclude;
import com.ronreynolds.smartsheet.model.SheetInclude;
import com.ronreynolds.smartsheet.model.SheetLevel;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Constants {
    private Constants() {
    }

    // all immutable Lists
    public static final List<AccessLevel> accessLevelsWithoutOwner =
            Stream.of(AccessLevel.values())
                    .filter(v -> v != AccessLevel.OWNER)
                    .collect(Collectors.toUnmodifiableList());
    public static final List<FolderWorkspaceInclude> allFolderIncludes = List.of(FolderWorkspaceInclude.values());
    public static final List<ReportInclude> allReportIncludes = List.of(ReportInclude.values());
    public static final List<SheetInclude> allSheetIncludes = List.of(SheetInclude.values());
    public static final List<SheetInclude> normalSheetIncludes = List.of(SheetInclude.OWNER_INFO, SheetInclude.COLUMN_TYPE,
            SheetInclude.SOURCE);
    public static final List<SheetExclude> noSheetExcludes = List.of();

    public static final List<Long> allColumnIds = null;
    public static final List<Long> allRowIds = null;
    public static final List<Integer> allRowNumbers = null;

    public static final String allFilters = null;
    public static final Integer allPageNumbers = null;
    public static final PaperSize noPaperSize = null;
    public static final Integer noPageSize = null;
    public static final OffsetDateTime noModifiedSince = null;
    public static final Integer noVersionAfter = null;
    public static final SheetLevel noCompatibilityLevel = null;

    /**
     * general-purpose method to get all the values of a particular enum type as a List
     *
     * @param enumType the type of Enum for which we want a List
     * @param <T>      a type of Enum
     * @return a immutable {@code List<Enum<T>>} that contains all the values of that type in {@code values()} order
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> List<T> allOf(Class<T> enumType) {
        try {
            T[] allValues = (T[]) enumType.getDeclaredMethod("values").invoke(null);
            return List.of(allValues);
        } catch (ReflectiveOperationException impossible) {
            // all classes that extend from Enum have a public static values() method so this should be impossible
            throw new RuntimeException(impossible);
        }
    }
}
