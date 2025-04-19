package com.ronreynolds.smartsheet.it;

import java.util.List;

/**
 * the IDs of various test resources
 */
public class TestData {
    public interface SheetData {
        long id = 6971132763656068L;    // 7290900052922244L
        String name = "";  // TODO
    }
    public interface UserData {
        long id = 829865629902724L;
        String firstName = "Ron";
        String lastName = "Reynolds";
        String locale = "en_US";
        String timezone = "US/Pacific";
        String accountName = "Flock of Singletons";
        long accountId = 429119679817604L;
        boolean isAdmin = true;
        boolean isLicensedSheetCreator = true;
        boolean isGroupAdmin = true;
        List<String> alternateEmails = List.of();
    }

    public static final long workspaceId = 0L;
    public static final long folderId = 0L;
    public static final long reportId = 0L;
}
