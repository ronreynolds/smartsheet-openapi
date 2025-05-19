package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.ImportApi;
import com.ronreynolds.smartsheet.model.ImportSheetIntoFolder200Response;
import com.ronreynolds.smartsheet.model.SheetContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for ImportApi
 */
@Disabled("ImportApiTest not yet implemented")
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImportApiTest {

    private final ImportApi api = new ImportApi();


    /**
     * Import Sheet into Folder
     * <p>
     * Imports CSV or XLSX data into a new sheet in the specified folder.  Note the following: * Both sheetName and the file
     * name must use ASCII characters. * The source data must be basic text. To include rich formula data, import and create a
     * sheet first, and then use Update Rows. To work with images, see Cell Images. * XLS is not supported. You must use XLSX.
     * * Hierarchical relationships between rows in an external file won&#39;t import.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void importSheetIntoFolderTest() throws ApiException {
        Long folderId = null;
        SheetContentType contentType = null;
        String sheetName = null;
        String contentDisposition = null;
        Integer headerRowIndex = null;
        Integer primaryColumnIndex = null;
        File body = null;
        ImportSheetIntoFolder200Response response =
                api.importSheetIntoFolder(folderId, contentType, sheetName, contentDisposition, headerRowIndex,
                        primaryColumnIndex, body);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Import Sheet from CSV / XLSX
     * <p>
     * Imports CSV or XLSX data into a new sheet in the top-level \&quot;Sheets\&quot; folder.  Note the following: * Both
     * sheetName and the file name must use ASCII characters. * The source data must be basic text. To include rich formula
     * data, import and create a sheet first, and then use Update Rows. To work with images, see Cell Images. * XLS is not
     * supported. You must use XLSX. * Hierarchical relationships between rows in an external file won&#39;t import.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void importSheetIntoSheetsFolderTest() throws ApiException {
        SheetContentType contentType = null;
        String sheetName = null;
        String contentDisposition = null;
        Integer headerRowIndex = null;
        Integer primaryColumnIndex = null;
        File body = null;
        ImportSheetIntoFolder200Response response =
                api.importSheetIntoSheetsFolder(contentType, sheetName, contentDisposition, headerRowIndex, primaryColumnIndex,
                        body);

        // TODO: test validations
    }

    /**
     * Import Sheet into Workspace
     * <p>
     * Imports CSV or XLSX data into a new sheet in the specified workspace.  Note the following: * Both sheetName and the file
     * name must use ASCII characters. * The source data must be basic text. To include rich formula data, import and create a
     * sheet first, and then use Update Rows. To work with images, see Cell Images. * XLS is not supported. You must use XLSX.
     * * Hierarchical relationships between rows in an external file won&#39;t import.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void importSheetIntoWorkspaceTest() throws ApiException {
        Long workspaceId = null;
        SheetContentType contentType = null;
        String sheetName = null;
        String contentDisposition = null;
        Integer headerRowIndex = null;
        Integer primaryColumnIndex = null;
        File body = null;
        ImportSheetIntoFolder200Response response =
                api.importSheetIntoWorkspace(workspaceId, contentType, sheetName, contentDisposition, headerRowIndex,
                        primaryColumnIndex, body);

        // TODO: test validations
    }

}
