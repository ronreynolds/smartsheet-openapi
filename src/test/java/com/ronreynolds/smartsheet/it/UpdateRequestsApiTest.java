package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.UpdateRequestsApi;
import com.ronreynolds.smartsheet.model.Result;
import com.ronreynolds.smartsheet.model.SentUpdateRequest;
import com.ronreynolds.smartsheet.model.UpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for UpdateRequestsApi
 */
@Disabled("UpdateRequestsApiTest not yet implemented")
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UpdateRequestsApiTest {

    private final UpdateRequestsApi api = new UpdateRequestsApi();


    /**
     * Delete Sent Update Request
     * <p>
     * Deletes the specified sent update request.  **Delete operation is supported only when the specified sent update request
     * is in the pending status. Deleting a sent update request that was already completed by recipient is not allowed.**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void sentupdaterequestDeleteTest() throws ApiException {
        Long sheetId = null;
        Long sentUpdateRequestId = null;
        Result response = api.sentupdaterequestDelete(sheetId, sentUpdateRequestId);
        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Get Sent Update Request
     * <p>
     * Gets the specified sent update request on the sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void sentupdaterequestGetTest() throws ApiException {
        Long sheetId = null;
        Long sentUpdateRequestId = null;
        SentUpdateRequest response = api.sentupdaterequestGet(sheetId, sentUpdateRequestId);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * List Sent Update Requests
     * <p>
     * Gets a summarized list of all sent update requests on the sheet. Only the following fields are returned in the response:
     * * **id**   * **message**   * **sendTo**   * **sentAt**   * **sentBy**   * **status**   * **subject**   *
     * **updateRequestId**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void sentupdaterequestsListTest() throws ApiException {
        Long sheetId = null;
        Boolean includeAll = null;
        Integer page = null;
        Integer pageSize = null;
        var response = api.sentupdaterequestsList(sheetId, includeAll, page, pageSize);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Create an Update Request
     * <p>
     * Creates an update request for the specified rows within the sheet. An email notification (containing a link to the
     * update request) is sent to the specified recipients according to the specified schedule.  The recipients of an update
     * request must be specified by using email addresses only. Sending an update request to a group is not supported.  The
     * following attributes have the following values when not specified: * **ccMe:** false * **message:** Please update the
     * following rows in my online sheet. * **subject:** Update Request: {Sheet Name}  When the Schedule object is not
     * specified, the request is sent to the recipients immediately.  If an error occurs because the request specified one or
     * more *alternate email addresses*, please retry using the primary email address.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updaterequestsCreateTest() throws ApiException {
        Long sheetId = null;
        String contentType = null;
        UpdateRequest updateRequest = null;
        var response = api.updaterequestsCreate(sheetId, contentType, updateRequest);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Delete an Update Request
     * <p>
     * Terminates the future scheduled delivery of the update request specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updaterequestsDeleteTest() throws ApiException {
        Long sheetId = null;
        Long updateRequestId = null;
        Result response = api.updaterequestsDelete(sheetId, updateRequestId);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Get an Update Request
     * <p>
     * Gets the specified update request for the sheet that has a future schedule.  The rowIds and columnIds in the returned
     * UpdateRequest object represent the list at the time the update request was created or last modified. The lists may
     * contain Ids of rows or columns that are no longer valid (for example, they have been removed from the sheet).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updaterequestsGetTest() throws ApiException {
        Long sheetId = null;
        Long updateRequestId = null;
        UpdateRequest response = api.updaterequestsGet(sheetId, updateRequestId);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * List Update Requests
     * <p>
     * Gets a summarized list of all update requests that have future schedules associated with the specified sheet. Only the
     * following fields are returned in the response:   * **id**   * **ccMe**   * **createdAt**   * **message**   *
     * **modifiedAt**   * **schedule**   * **sendTo**   * **sentBy**   * **subject**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updaterequestsListTest() throws ApiException {
        Long sheetId = null;
        Boolean includeAll = null;
        Integer page = null;
        Integer pageSize = null;
        var response = api.updaterequestsList(sheetId, includeAll, page, pageSize);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Update an Update Request
     * <p>
     * Changes the specified update request for the sheet.  **Making changes to update requests that do not have future
     * scheduled delivery is not allowed.**  The UpdateRequest object in the request body must specify one or more of the
     * following attributes:  * **ccMe:** Boolean * **columnIds:** number[] * **includeAttachments:** Boolean *
     * **includeDiscussions:** Boolean * **message:** string * **schedule:** Schedule object * **sendTo:** Recipient[] *
     * **subject:** string  If an error occurs because the request specified one or more *alternate email addresses*, please
     * retry using the primary email address.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updaterequestsUpdateTest() throws ApiException {
        Long sheetId = null;
        Long updateRequestId = null;
        String contentType = null;
        var response = api.updaterequestsUpdate(sheetId, updateRequestId, contentType);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }
}
