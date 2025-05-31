package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.CrossSheetReferencesApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.AddCrosssheetReferenceRequest;
import com.ronreynolds.smartsheet.model.CrossSheetReference;
import com.ronreynolds.smartsheet.model.ListCrosssheetReferences200Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for CrossSheetReferencesApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrossSheetReferencesApiTest {

    private final CrossSheetReferencesApi api = new CrossSheetReferencesApi();


    /**
     * Create Cross-sheet References
     * <p>
     * Adds a cross-sheet reference between two sheets and defines the data range for formulas. Each distinct data range
     * requires a new cross-sheet reference.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void addCrosssheetReferenceTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        AddCrosssheetReferenceRequest addCrosssheetReferenceRequest = new AddCrosssheetReferenceRequest();
        // CrossSheetReferenceRequestWithColumnAndRowIds
        // CrossSheetReferenceRequestWithColumnIds
        // CrossSheetReferenceRequestWithRowIds
        var response = api.addCrosssheetReference(sheetId, addCrosssheetReferenceRequest, Constants.noContentType);
        assertThat(response).isNotNull();

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Get Cross-sheet Reference
     * <p>
     * Gets the cross-sheet reference specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need to create cross-sheet reference to test with")
    public void getCrosssheetReferenceTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long crossSheetReferenceId = null;
        CrossSheetReference response = api.getCrosssheetReference(sheetId, crossSheetReferenceId);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * List Cross-sheet References
     * <p>
     * Lists all cross-sheet references for the sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("currently have no cross-sheet references")
    public void listCrosssheetReferencesTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Boolean includeAll = true;
        Integer page = null;
        Integer pageSize = null;
        ListCrosssheetReferences200Response response = api.listCrosssheetReferences(sheetId, includeAll, page, pageSize);

        log.info("{}", response);
        assertThat(response).satisfies(TestData::pagedResultHasDataNullPageSize);

        // TODO: test validations
    }
}