package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.AttachmentsApi;
import com.ronreynolds.smartsheet.it.TestData.AttachmentData.CommentAttachment;
import com.ronreynolds.smartsheet.it.TestData.AttachmentData.RowAttachment;
import com.ronreynolds.smartsheet.it.TestData.AttachmentData.SheetAttachment;
import com.ronreynolds.smartsheet.it.TestData.CommentData;
import com.ronreynolds.smartsheet.it.TestData.RowData;
import com.ronreynolds.smartsheet.it.TestData.SheetData;
import com.ronreynolds.smartsheet.model.Attachment;
import com.ronreynolds.smartsheet.model.AttachmentsAttachToSheet200Response;
import com.ronreynolds.smartsheet.model.AttachmentsListOnSheet200Response;
import com.ronreynolds.smartsheet.model.AttachmentsVersionList200Response;
import com.ronreynolds.smartsheet.model.ResultPrefix;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * API tests for AttachmentsApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AttachmentsApiTest {
    private final AttachmentsApi api = new AttachmentsApi();

    /**
     * Attach File or URL to Comment
     * <p>
     * Attaches a file to the comment. The URL can be any of the following:  * Normal URL (attachmentType \&quot;LINK\&quot;) *
     * Box.com URL (attachmentType \&quot;BOX_COM\&quot;) * Dropbox URL (attachmentType \&quot;DROPBOX\&quot;) * Egnyte URL
     * (attachmentType \&quot;EGNYTE\&quot;) * Evernote URL (attachmentType \&quot;EVERNOTE\&quot;) * Google Drive URL
     * (attachmentType \&quot;GOOGLE_DRIVE\&quot;) * OneDrive URL (attachmentType \&quot;ONEDRIVE\&quot;)  This operation can
     * be performed using a simple upload or a multipart upload.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need file to attach to comment")
    public void attachmentsAttachToCommentTest() throws ApiException {
        String contentType = null;
        File body = null;
        var response = api.attachmentsAttachToComment(SheetData.id, CommentData.id, contentType, body);
        // TODO: test validations
        log.info("{}", response);
        assertThat(response).isNotNull();
    }

    /**
     * Attach File or URL to Sheet
     * <p>
     * Attaches a file to the sheet. The URL can be any of the following:  * Normal URL (attachmentType \&quot;LINK\&quot;) *
     * Box.com URL (attachmentType \&quot;BOX_COM\&quot;) * Dropbox URL (attachmentType \&quot;DROPBOX\&quot;) * Egnyte URL
     * (attachmentType \&quot;EGNYTE\&quot;) * Evernote URL (attachmentType \&quot;EVERNOTE\&quot;) * Google Drive URL
     * (attachmentType \&quot;GOOGLE_DRIVE\&quot;) * OneDrive URL (attachmentType \&quot;ONEDRIVE\&quot;)  For multipart
     * uploads please use \&quot;multipart/form-data\&quot; content type.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need file to attach to sheet")
    public void attachmentsAttachToSheetTest() throws ApiException {
        String contentType = null;
        File body = null;
        AttachmentsAttachToSheet200Response response = api.attachmentsAttachToSheet(SheetData.id, contentType, body);

        // TODO: test validations
        log.info("{}", response);
        assertThat(response).isNotNull();
    }

    /**
     * Delete Attachment
     * <p>
     * Deletes the attachment specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need attachment ID to delete")
    public void attachmentsDeleteTest() throws ApiException {
        Long attachmentId = null;
        ResultPrefix response = api.attachmentsDelete(SheetData.id, attachmentId);

        // TODO: test validations
        log.info("{}", response);
        assertThat(response).isNotNull();
    }

    /**
     * Get Attachment
     * <p>
     * Fetches a temporary URL that allows you to download an attachment. The urlExpiresInMillis attribute tells you how long
     * the URL is valid.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void attachmentsGetTest() throws ApiException {
        assertThat(api.attachmentsGet(SheetData.id, CommentAttachment.id)).satisfies(CommentAttachment::assertEquals);
        assertThat(api.attachmentsGet(SheetData.id, RowAttachment.id)).satisfies(RowAttachment::assertEquals);
        assertThat(api.attachmentsGet(SheetData.id, SheetAttachment.id)).satisfies(SheetAttachment::assertEquals);
    }

    /**
     * List Row Attachments
     * <p>
     * Gets a list of all attachments that are on the row, including row and discussion-level attachments.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void attachmentsListOnRowTest() throws ApiException {
        Long sheetId = SheetData.id;
        Long rowId = RowData.id;
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = true;
        AttachmentsVersionList200Response response = api.attachmentsListOnRow(sheetId, rowId, page, pageSize, includeAll);
        assertThat(response).isNotNull();

        Map<Long, Attachment> attachmentMap = attachmentListToMap(response.getData());
        assertThat(attachmentMap.get(RowAttachment.id))
                .as("checking row attachment")
                .satisfies(RowAttachment::assertEquals);
        assertThat(attachmentMap.get(CommentAttachment.id))
                .as("checking comment attachment")
                .satisfies(CommentAttachment::assertEquals);
    }

    /**
     * List Attachments
     * <p>
     * Gets a list of all attachments that are on the sheet, including sheet, row, and discussion-level attachments.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void attachmentsListOnSheetTest() throws ApiException {
        Long sheetId = SheetData.id;
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = true;
        AttachmentsListOnSheet200Response response = api.attachmentsListOnSheet(sheetId, page, pageSize, includeAll);
        assertThat(response).isNotNull();

        Map<Long, Attachment> attachmentMap = attachmentListToMap(response.getData());
        assertThat(attachmentMap.get(RowAttachment.id))
                .as("checking row attachment")
                .satisfies(RowAttachment::assertEquals);
        assertThat(attachmentMap.get(CommentAttachment.id))
                .as("checking comment attachment")
                .satisfies(CommentAttachment::assertEquals);
        assertThat(attachmentMap.get(SheetAttachment.id))
                .as("checking sheet attachment")
                .satisfies(SheetAttachment::assertEquals);
    }

    /**
     * List Versions
     * <p>
     * Gets a list of all versions of the given attachmentId in order from newest to oldest.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void attachmentsVersionListTest() throws ApiException {
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = true;
        var response = api.attachmentsVersionList(SheetData.id, SheetAttachment.id, page, pageSize, includeAll);
        // TODO - probably can improve these assertions
        assertThat(response).isNotNull();
        assertThat(response.getData())
                .isNotEmpty()
                .first().satisfies(SheetAttachment::assertEquals);
    }

    /**
     * Attach New version
     * <p>
     * Uploads a new version of a file to a sheet or row. This operation can be performed using a simple upload or a multipart
     * upload.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need file to attach to existing attachment on sheet")
    public void attachmentsVersionUploadTest() throws ApiException {
        Long sheetId = SheetData.id;
        Long attachmentId = TestData.AttachmentData.SheetAttachment.id;
        String contentType = null;
        File body = null;
        AttachmentsAttachToSheet200Response response = api.attachmentsVersionUpload(sheetId, attachmentId, contentType, body);
        // TODO: test validations
        log.info("{}", response);
        assertThat(response).isNotNull();
    }

    /**
     * Delete All Versions
     * <p>
     * Deletes all versions of the attachment corresponding to the specified attachmentId. For attachments with multiple
     * versions, this effectively deletes the attachment from the object that it’s attached to.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need attachment ID")
    public void attachmentsVersionsDeleteTest() throws ApiException {
        Long attachmentId = TestData.AttachmentData.SheetAttachment.id;
        ResultPrefix response = api.attachmentsVersionsDelete(SheetData.id, attachmentId);
        // TODO: test validations
        log.info("{}", response);
        assertThat(response).isNotNull();
    }

    /**
     * List Discussion Attachments
     * <p>
     * Gets a list of all attachments that are in the discussion.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void discussionListAttachmentsTest() throws ApiException {
        Long discussionId = TestData.DiscussionData.id;
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = true;
        var response = api.discussionListAttachments(SheetData.id, discussionId, page, pageSize, includeAll);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData::pagedResultHasDataNullPageSize);
        assertThat(response.getData()).satisfies(TestData.DiscussionData::assertContainsAttachment);
    }

    /**
     * Attach File or URL to Row
     * <p>
     * Attaches a file to the row. The URL can be any of the following:  * Normal URL (attachmentType \&quot;LINK\&quot;) * Box
     * .com URL (attachmentType \&quot;BOX_COM\&quot;) * Dropbox URL (attachmentType \&quot;DROPBOX\&quot;) * Egnyte URL
     * (attachmentType \&quot;EGNYTE\&quot;) * Evernote URL (attachmentType \&quot;EVERNOTE\&quot;) * Google Drive URL
     * (attachmentType \&quot;GOOGLE_DRIVE\&quot;) * OneDrive URL (attachmentType \&quot;ONEDRIVE\&quot;)  For multipart
     * uploads please use \&quot;multipart/form-data\&quot; content type.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled
    public void rowAttachmentsAttachFileTest() throws ApiException {
        Long sheetId = SheetData.id;
        Long rowId = RowData.id;
        String contentType = null;
        File body = null;

        AttachmentsAttachToSheet200Response response = api.rowAttachmentsAttachFile(sheetId, rowId, contentType, body);
        // TODO: test validations
        log.info("{}", response);
        assertThat(response).isNotNull();
    }

    private static Map<Long, Attachment> attachmentListToMap(List<Attachment> list) {
        assertThat(list).as("attachment list is null").isNotNull();
        return list.stream().collect(Collectors.toMap(Attachment::getId, Function.identity()));
    }
}
