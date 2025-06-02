package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.DiscussionsApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.CommentRequest;
import com.ronreynolds.smartsheet.model.Discussion;
import com.ronreynolds.smartsheet.model.DiscussionCreationRequest;
import com.ronreynolds.smartsheet.model.DiscussionInclude;
import com.ronreynolds.smartsheet.model.GenericResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for DiscussionsApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Disabled("need test discussion to delete")
    public void discussionDeleteTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long discussionId = null;
        GenericResult response = api.discussionDelete(sheetId, discussionId);

        log.info("{}", response);
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
        Long discussionId = TestData.DiscussionData.id;
        Discussion response = api.discussionGet(sheetId, discussionId);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData.DiscussionData::assertEquals);
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
    @Disabled
    public void discussionsCreateTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        String contentType = null;
        DiscussionCreationRequest request = DiscussionCreationRequest.builder()
                .comment(CommentRequest.builder().text("starting comment").build())
                .build();
        var response = api.discussionsCreate(sheetId, Constants.noContentType, request);

        log.info("{}", response);
        assertThat(response).isNotNull();
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
        List<DiscussionInclude> include = Arrays.asList(DiscussionInclude.values());
        Boolean includeAll = true;
        var response = api.discussionsList(sheetId, include, Constants.allPages, Constants.noPageSize, includeAll);

        log.info("{}", response);
        assertThat(response).isNotNull();
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
    @Disabled
    public void rowDiscussionsCreateTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long rowId = TestData.RowData.id;
        DiscussionCreationRequest request = DiscussionCreationRequest.builder()
                .comment(CommentRequest.builder().text("starting row discussion").build())
                .build();
        var response = api.rowDiscussionsCreate(sheetId, rowId, Constants.noContentType, request);

        log.info("{}", response);
        assertThat(response).isNotNull();
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
        Boolean includeAll = true;
        var response = api.rowDiscussionsList(sheetId, rowId, include, Constants.allPages, Constants.noPageSize, includeAll);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData::pagedResultHasDataNullPageSize);
        assertThat(response.getData()).satisfies(TestData.DiscussionData::assertContains);
    }
}
