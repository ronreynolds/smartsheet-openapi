package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.SheetSummaryApi;
import com.ronreynolds.smartsheet.model.AddImageSummaryField200Response;
import com.ronreynolds.smartsheet.model.AddSummaryFields200Response;
import com.ronreynolds.smartsheet.model.AddSummaryFieldsRequestInner;
import com.ronreynolds.smartsheet.model.DeleteSummaryFields200Response;
import com.ronreynolds.smartsheet.model.ListSummaryFieldsPaginated200Response;
import com.ronreynolds.smartsheet.model.SheetSummary;
import com.ronreynolds.smartsheet.model.SheetSummaryExclude;
import com.ronreynolds.smartsheet.model.SheetSummaryInclude;
import com.ronreynolds.smartsheet.model.UpdateSummaryFields200Response;
import com.ronreynolds.smartsheet.model.UpdateSummaryFieldsRequestInner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for SheetSummaryApi
 */
@Disabled("SheetSummaryApiTest not yet implemented")
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SheetSummaryApiTest {

    private final SheetSummaryApi api = new SheetSummaryApi();


    /**
     * Add Image to Sheet Summary
     * <p>
     * Adds an image to the summary field.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addImageSummaryFieldTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long fieldId = null;
        String contentType = null;
        String contentDisposition = null;
        Integer contentLength = null;
        String altText = null;
        Boolean overrideValidation = null;
        File body = null;
        AddImageSummaryField200Response response =
                api.addImageSummaryField(sheetId, fieldId, contentType, contentDisposition, contentLength, altText,
                        overrideValidation, body);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Add Summary Fields
     * <p>
     * Creates one or more summary fields for the specified sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addSummaryFieldsTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        List<AddSummaryFieldsRequestInner> addSummaryFieldsRequestInner = null;
        Boolean renameIfConflict = null;
        AddSummaryFields200Response response =
                api.addSummaryFields(sheetId, addSummaryFieldsRequestInner, renameIfConflict);

        // TODO: test validations
    }

    /**
     * Delete Summary Fields
     * <p>
     * Deletes summary fields from the specified sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteSummaryFieldsTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        List<Long> ids = null;
        Boolean ignoreSummaryFieldsNotFound = null;
        DeleteSummaryFields200Response response =
                api.deleteSummaryFields(sheetId, ids, ignoreSummaryFieldsNotFound);

        // TODO: test validations
    }

    /**
     * Get Sheet Summary
     * <p>
     * Returns object containing array of summary fields. Allows for pagination of results.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listSummaryFieldsTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        List<SheetSummaryInclude> include = null;
        List<SheetSummaryExclude> exclude = null;
        SheetSummary response =
                api.listSummaryFields(sheetId, include, exclude);

        // TODO: test validations
    }

    /**
     * Get Summary Fields
     * <p>
     * Returns object containing array of summary fields. Allows for pagination of results.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listSummaryFieldsPaginatedTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Boolean includeAll = null;
        Integer page = null;
        Integer pageSize = null;
        List<SheetSummaryInclude> include = null;
        List<SheetSummaryExclude> exclude = null;
        ListSummaryFieldsPaginated200Response response =
                api.listSummaryFieldsPaginated(sheetId, includeAll, page, pageSize, include, exclude);

        // TODO: test validations
    }

    /**
     * Update Summary Fields
     * <p>
     * Updates the summary fields for the given sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateSummaryFieldsTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        List<UpdateSummaryFieldsRequestInner> updateSummaryFieldsRequestInner = null;
        Boolean renameIfConflict = null;
        UpdateSummaryFields200Response response =
                api.updateSummaryFields(sheetId, updateSummaryFieldsRequestInner, renameIfConflict);

        // TODO: test validations
    }

}
