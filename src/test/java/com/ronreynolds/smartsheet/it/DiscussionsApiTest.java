package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.DiscussionsApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.CommentBrief;
import com.ronreynolds.smartsheet.model.Discussion;
import com.ronreynolds.smartsheet.model.DiscussionInclude;
import com.ronreynolds.smartsheet.model.DiscussionsCreate200Response;
import com.ronreynolds.smartsheet.model.DiscussionsInclude;
import com.ronreynolds.smartsheet.model.DiscussionsList200Response;
import com.ronreynolds.smartsheet.model.ResultPrefix;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for DiscussionsApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled("DiscussionsApiTest not yet implemented")
public class DiscussionsApiTest {

    private final DiscussionsApi api = new DiscussionsApi();


    /**
     * Delete a Discussion
     * <p>
     * Deletes the discussion specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void discussionDeleteTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long discussionId = null;
        ResultPrefix response = api.discussionDelete(sheetId, discussionId);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Get Discussion
     * <p>
     * Gets the discussion specified by discussionId.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void discussionGetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long discussionId = null;
        Discussion response = api.discussionGet(sheetId, discussionId);

        // TODO: test validations
    }

    /**
     * Create a Discussion
     * <p>
     * Creates a new discussion on a sheet. To create a discussion with an attachment please use \&quot;
     * multipart/form-data\&quot; content type.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void discussionsCreateTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        String contentType = null;
        CommentBrief commentLite = null;
        DiscussionsCreate200Response response = api.discussionsCreate(sheetId, contentType, commentLite);

        // TODO: test validations
    }

    /**
     * List Discussions
     * <p>
     * Gets a list of all discussions associated with the specified sheet. Remember that discussions are containers for the
     * conversation thread. To see the entire thread, use the include&#x3D;comments parameter.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void discussionsListTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        List<DiscussionsInclude> include = null;
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = null;
        DiscussionsList200Response response =
                api.discussionsList(sheetId, include, page, pageSize, includeAll);

        // TODO: test validations
    }

    /**
     * Create a Discussion on a Row
     * <p>
     * Creates a new discussion on a row. To create a discussion with an attachment please use \&quot;
     * multipart/form-data\&quot; content type.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void rowDiscussionsCreateTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long rowId = TestData.RowData.id;
        String contentType = null;
        CommentBrief commentLite = null;
        DiscussionsCreate200Response response = api.rowDiscussionsCreate(sheetId, rowId, contentType, commentLite);

        // TODO: test validations
    }

    /**
     * List Discussions with a Row
     * <p>
     * Gets a list of all discussions associated with the specified row. Remember that discussions are containers for the
     * conversation thread. To see the entire thread, use the include&#x3D;comments parameter.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void rowDiscussionsListTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long rowId = TestData.RowData.id;
        List<DiscussionInclude> include = Constants.allOf(DiscussionInclude.class);
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = true;
        DiscussionsList200Response response = api.rowDiscussionsList(sheetId, rowId, include, page, pageSize, includeAll);

        // TODO: test validations
    }

}
