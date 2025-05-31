package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.SheetSummaryApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.SheetSummary;
import com.ronreynolds.smartsheet.model.SheetSummaryExclude;
import com.ronreynolds.smartsheet.model.SheetSummaryInclude;
import com.ronreynolds.smartsheet.model.SummaryFieldCreateRequest;
import com.ronreynolds.smartsheet.model.SummaryFieldUpdateRequest;
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
        String contentDisposition = null;
        Integer contentLength = null;
        String altText = null;
        Boolean overrideValidation = null;
        File body = null;
        var response = api.addImageSummaryField(
                sheetId, fieldId, Constants.noContentType, contentDisposition, contentLength, altText, overrideValidation, body);
        log.info("{}", response);
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
        List<SummaryFieldCreateRequest> request = null;
        Boolean renameIfConflict = null;
        var response = api.addSummaryFields(sheetId, request, renameIfConflict);
        log.info("{}", response);
        assertThat(response).isNotNull();

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
        var response = api.deleteSummaryFields(sheetId, ids, ignoreSummaryFieldsNotFound);

        log.info("{}", response);
        assertThat(response).isNotNull();
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
        SheetSummary response = api.listSummaryFields(sheetId, include, exclude);

        log.info("{}", response);
        assertThat(response).isNotNull();
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
        var response = api.listSummaryFieldsPaginated(sheetId, includeAll, page, pageSize, include, exclude);

        log.info("{}", response);
        assertThat(response).isNotNull();
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
        List<SummaryFieldUpdateRequest> request = null;
        Boolean renameIfConflict = null;
        var response = api.updateSummaryFields(sheetId, request, renameIfConflict);

        log.info("{}", response);
        assertThat(response).isNotNull();
        // TODO: test validations
    }
}
