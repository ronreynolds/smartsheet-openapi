package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.RowsApi;
import com.ronreynolds.smartsheet.model.CompatibilityLevel;
import com.ronreynolds.smartsheet.model.CopyOrMoveRowDirective;
import com.ronreynolds.smartsheet.model.CopyOrMoveRowResult;
import com.ronreynolds.smartsheet.model.CopyRowsInclude;
import com.ronreynolds.smartsheet.model.DeleteRows200Response;
import com.ronreynolds.smartsheet.model.GetRowInclude;
import com.ronreynolds.smartsheet.model.MoveRowsInclude;
import com.ronreynolds.smartsheet.model.MultiRowEmail;
import com.ronreynolds.smartsheet.model.ResultPrefix;
import com.ronreynolds.smartsheet.model.Row;
import com.ronreynolds.smartsheet.model.RowGet200Response;
import com.ronreynolds.smartsheet.model.RowsAddToSheet200Response;
import com.ronreynolds.smartsheet.model.RowsSortRequest;
import com.ronreynolds.smartsheet.model.Sheet;
import com.ronreynolds.smartsheet.model.SheetExclude;
import com.ronreynolds.smartsheet.model.UpdateRows200Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for RowsApi
 */
@Disabled("RowsApiTest not yet implemented")
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RowsApiTest {

    private final RowsApi api = new RowsApi();


    /**
     * Copy Rows to Another Sheet
     * <p>
     * Copies rows from the sheet specified in the URL to (the bottom of) another sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void copyRowsTest() throws ApiException {
        Long sheetId = null;
        
        List<CopyRowsInclude> include = null;
        Boolean ignoreRowsNotFound = null;
        CopyOrMoveRowDirective copyOrMoveRowDirective = null;
        CopyOrMoveRowResult response = api.copyRows(sheetId, include, ignoreRowsNotFound, copyOrMoveRowDirective);
        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Delete Rows
     * <p>
     * Deletes one or more rows from the sheet specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteRowsTest() throws ApiException {
        Long sheetId = null;
        List<Long> ids = null;
        Boolean ignoreRowsNotFound = null;
        DeleteRows200Response response = api.deleteRows(sheetId, ids, ignoreRowsNotFound);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Move Rows to Another Sheet
     * <p>
     * Moves rows from the sheet specified in the URL to (the bottom of) another sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void moveRowsTest() throws ApiException {
        Long sheetId = null;
        MoveRowsInclude include = null;
        Boolean ignoreRowsNotFound = null;
        CopyOrMoveRowDirective copyOrMoveRowDirective = null;
        CopyOrMoveRowResult response = api.moveRows(sheetId, include, ignoreRowsNotFound, copyOrMoveRowDirective);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Get Row
     * <p>
     * Gets the row specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void rowGetTest() throws ApiException {
        Long sheetId = null;
        Long rowId = null;
        Integer accessApiLevel = null;
        List<GetRowInclude> include = null;
        List<SheetExclude> exclude = null;
        CompatibilityLevel level = null;
        RowGet200Response response = api.rowGet(sheetId, rowId, accessApiLevel, include, exclude, level);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Add Rows
     * <p>
     * Inserts one or more rows into the sheet specified in the URL. If you want to insert the rows in any position but the
     * default, use [location-specifier attributes](../../tag/rowsRelated#section/Specify-Row-Location) (that is, toTop,
     * toBottom, parentId, siblingId, above, indent, outdent). See language tabs for variations in syntax.  Note: This
     * operation does not add rows with cells that have images. However, you can upload an image to a cell *after* the cell
     * exists in a sheet. To do so, call the operation described in the [Add Image to Cell]
     * (/tag/cellImages#operation/addImageToCell) page.  This operation supports both single-object and bulk semantics. For
     * more information, see [Optional Bulk Operations](../../#section/Work-at-Scale/Bulk-Operations).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void rowsAddToSheetTest() throws ApiException {
        Long sheetId = null;
        Integer accessApiLevel = null;
        
        Boolean allowPartialSuccess = null;
        Boolean overrideValidation = null;
        List<Row> row = null;
        RowsAddToSheet200Response response = api.rowsAddToSheet(sheetId, accessApiLevel, allowPartialSuccess, overrideValidation, row);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Send Rows via Email
     * <p>
     * Sends one or more rows via email.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void rowsSendTest() throws ApiException {
        Long sheetId = null;
        MultiRowEmail multiRowEmail = null;
        ResultPrefix response = api.rowsSend(sheetId, multiRowEmail);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Sort Rows in Sheet
     * <p>
     * Sorts the rows of a sheet, either in ascending or descending order.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void rowsSortTest() throws ApiException {
        Long sheetId = null;
        String includeAmpersandExclude = null;
        RowsSortRequest rowsSortRequest = null;
        Sheet response = api.rowsSort(sheetId, includeAmpersandExclude, rowsSortRequest);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Update Rows
     * <p>
     * Updates cell values in the specified rows, expands/collapses the specified rows, or modifies the position of specified
     * rows (including indenting/outdenting). For detailed information about changing row positions, see [location-specifier
     * attributes](../../tag/rowsRelated#section/Specify-Row-Location).  Note: This operation does not handle adding images to
     * cells. However, you can upload an image to a cell by calling the operation described in the [Add Image to Cell]
     * (/tag/cellImages#operation/addImageToCell) page.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateRowsTest() throws ApiException {
        Long sheetId = null;
        Integer accessApiLevel = null;
        
        Boolean allowPartialSuccess = null;
        Boolean overrideValidation = null;
        List<Row> row = null;
        UpdateRows200Response response = api.updateRows(sheetId, accessApiLevel, allowPartialSuccess, overrideValidation, row);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

}
