package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.model.Sheet;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

/**
 * various bits of test constants
 */
public class TestData {
    interface UserData {
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
        interface AlternateEmailData {
            long id = 3036181538596740L;
        }
    }

    interface SheetData {
        long id = 6971132763656068L;
        String name = "Test Sheet 1";
        OffsetDateTime createdDate = OffsetDateTime.of(2025, 4, 2, 14, 27, 31, 0, ZoneOffset.UTC);
        List<String> effectiveAttachmentOptions =
                List.of("FILE", "BOX_COM", "LINK", "EVERNOTE", "ONEDRIVE", "GOOGLE_DRIVE", "DROPBOX", "EGNYTE");
    }

    interface RowData {
        long id = 6139161318133636L;
    }

    interface ColumnData {
        Set<Long> columnIds = Set.of(PrimaryColumn.id, ImageColumn.id, Column3.id, Column4.id, Column5.id, Column6.id);
        interface PrimaryColumn {
            long id = 6606505816313732L;
            String name = "Primary Column";
            String type = "TEXT_NUMBER";
        }
        interface ImageColumn {
            long id = 4354706002628484L;
            String name = "Images";
            String type = "TEXT_NUMBER";
        }
        // created these to keep track of the IDs which should not change as the column names, types, and so forth do
        interface Column3 {
            long id = 8858305629998980L;
            String name = "Column3";
            String type = "TEXT_NUMBER";
        }
        interface Column4 {
            long id = 273318840323972L;
            String name = "Column4";
            String type = "TEXT_NUMBER";
        }
        interface Column5 {
            long id = 4776918467694468L;
            String name = "Column5";
            String type = "TEXT_NUMBER";
        }
        interface Column6 {
            long id = 2525118654009220L;
            String name = "Column6";
            String type = "TEXT_NUMBER";
        }
    }

    interface CellData {
        long id = 0L;
        Object value = 42;
    }

    interface FolderData {
        long id = 0L;
        String name = "Test Folder";
    }

    interface ReportData {
        long id = 0L;
        String name = "Test Report";
    }

    interface WorkflowData {
        String id = "Test Workflow";
    }

    interface WorkspaceData {
        long id = 4931918228678532L;
        String name = "Test Workspace";
    }
}
