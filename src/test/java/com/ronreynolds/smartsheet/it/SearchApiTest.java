package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.SearchApi;
import com.ronreynolds.smartsheet.model.ListSearch200Response;
import com.ronreynolds.smartsheet.model.ListSearchSheet200Response;
import com.ronreynolds.smartsheet.model.SearchScope;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for SearchApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchApiTest {
    private final SearchApi api = new SearchApi();

    /**
     * Search Everything
     * <p>
     * Searches all sheets that the user can access, for the specified text. If you have not used the public API in a while, we
     * will need to provision your data. This could take up to 24 hours so please check back later!
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listSearchTest() throws ApiException {
        String query = "42";
        String location = null;
        OffsetDateTime modifiedSince = null;
        String include = null;
        List<SearchScope> scopes = null;
        ListSearch200Response response = api.listSearch(query, location, modifiedSince, include, scopes);

        // TODO: test validations
        assertThat(response).isNotNull();
        System.out.println(response);
    }

    /**
     * Search Sheet
     * <p>
     * Gets a list of the user&#39;s search results in a sheet based on query. The list contains an abbreviated row object for
     * each search result in a sheet. If you have not used the public API in a while, we will need to provision your data. This
     * could take up to 24 hours so please check back later! *Note* Newly created or recently updated data may not be
     * immediately discoverable via search.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listSearchSheetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        String query = "42";
        ListSearchSheet200Response response = api.listSearchSheet(sheetId, query);

        // TODO: test validations
        assertThat(response).isNotNull();
        System.out.println(response);
    }

}
