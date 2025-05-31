package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.CommentsApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.it.TestData.CommentData;
import com.ronreynolds.smartsheet.it.TestData.SheetData;
import com.ronreynolds.smartsheet.model.CommentCreationRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for CommentsApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentsApiTest {
    private final CommentsApi api = new CommentsApi();


    /**
     * Delete a comment
     * <p>
     * Deletes the comment specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled
    public void commentDeleteTest() throws ApiException {
        Long sheetId = SheetData.id;
        Long commentId = null;
        var response = api.commentDelete(sheetId, commentId);
        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Edit a comment
     * <p>
     * Updates the text of a comment. NOTE: Only the user that created the comment is permitted to update it.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled
    public void commentEditTest() throws ApiException {
        Long commentId = null;
        CommentCreationRequest commentText = CommentCreationRequest.builder().text("new comment").build();
        var response = api.commentEdit(SheetData.id, commentId, Constants.noContentType, commentText);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Get a comment
     * <p>
     * Gets the comment specified by commentId.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void commentGetTest() throws ApiException {
        assertThat(api.commentGet(SheetData.id, CommentData.id)).satisfies(CommentData::assertEquals);
    }

    /**
     * Create a comment
     * <p>
     * Adds a comment to a discussion. To create a comment with an attachment please use \&quot;multipart/form-data\&quot;
     * content type.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled
    public void commentsCreateTest() throws ApiException {
        Long sheetId = SheetData.id;
        Long discussionId = null;
        String contentType = null;
        CommentCreationRequest commentText = CommentCreationRequest.builder()
                .text("this is a test comment")
                .build();
        var response = api.commentsCreate(sheetId, discussionId, contentType, commentText);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

}
