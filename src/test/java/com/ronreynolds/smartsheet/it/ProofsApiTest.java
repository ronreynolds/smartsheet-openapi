package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.ProofsApi;
import com.ronreynolds.smartsheet.model.AttachmentsAttachToSheet200Response;
import com.ronreynolds.smartsheet.model.CommentBrief;
import com.ronreynolds.smartsheet.model.DiscussionInclude;
import com.ronreynolds.smartsheet.model.DiscussionsCreate200Response;
import com.ronreynolds.smartsheet.model.Proof;
import com.ronreynolds.smartsheet.model.ProofRequestBody;
import com.ronreynolds.smartsheet.model.ProofsCreate200Response;
import com.ronreynolds.smartsheet.model.ProofsCreateProofRequests200Response;
import com.ronreynolds.smartsheet.model.ProofsCreateVersion200Response;
import com.ronreynolds.smartsheet.model.ProofsGetAllProofs200Response;
import com.ronreynolds.smartsheet.model.ProofsGetVersions200Response;
import com.ronreynolds.smartsheet.model.ProofsListAttachments200Response;
import com.ronreynolds.smartsheet.model.ProofsListDiscussions200Response;
import com.ronreynolds.smartsheet.model.ProofsListRequestActions200Response;
import com.ronreynolds.smartsheet.model.ProofsUpdateRequest;
import com.ronreynolds.smartsheet.model.ResultPrefix;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for ProofsApi
 */
@Slf4j
@Disabled("ProofsApiTest not yet implemented")
public class ProofsApiTest {

    private final ProofsApi api = new ProofsApi();


    /**
     * Attach File to Proof
     * <p>
     * Attaches a file to the proof.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsAttachToProofTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        String contentType = null;
        File body = null;
        AttachmentsAttachToSheet200Response response = api.proofsAttachToProof(sheetId, proofId, contentType, body);
        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Create Proof
     * <p>
     * Creates a proof on a row.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsCreateTest() throws ApiException {
        Long sheetId = null;
        Long rowId = null;
        String contentType = null;
        File body = null;
        ProofsCreate200Response response = api.proofsCreate(sheetId, rowId, contentType, body);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Create Proof Discussion
     * <p>
     * Creates a discussion on a proof.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsCreateDiscussionTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        String contentType = null;
        CommentBrief commentLite = null;
        DiscussionsCreate200Response response = api.proofsCreateDiscussion(sheetId, proofId, contentType, commentLite);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Create Proof Request
     * <p>
     * Creates a proof request.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsCreateProofRequestsTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        ProofRequestBody proofRequestBody = null;
        ProofsCreateProofRequests200Response response = api.proofsCreateProofRequests(sheetId, proofId, proofRequestBody);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Create Proof Version
     * <p>
     * Creates a proof version. Proof Id must be for the original proof.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsCreateVersionTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        String contentType = null;
        File body = null;
        ProofsCreateVersion200Response response = api.proofsCreateVersion(sheetId, proofId, contentType, body);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Delete Proof
     * <p>
     * Deletes the proof including all versions. The proofId must be for the original version.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsDeleteTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        ResultPrefix response = api.proofsDelete(sheetId, proofId);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Delete Proof Requests
     * <p>
     * Deletes all proof requests in a proof.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsDeleteProofRequestsTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        ResultPrefix response = api.proofsDeleteProofRequests(sheetId, proofId);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Delete Proof Version
     * <p>
     * Deletes a proof version. Proof Id must be a current version proof Id.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsDeleteVersionTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        ResultPrefix response = api.proofsDeleteVersion(sheetId, proofId);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Get Proof
     * <p>
     * Gets the proof specified in the URL. Returns the proof, which is optionally populated with discussion and attachment
     * objects.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsGetTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        List<DiscussionInclude> include = null;
        Proof response = api.proofsGet(sheetId, proofId, include);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * List Proofs
     * <p>
     * Gets a list of all proofs for a given sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsGetAllProofsTest() throws ApiException {
        Long sheetId = null;
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = null;
        ProofsGetAllProofs200Response response = api.proofsGetAllProofs(sheetId, page, pageSize, includeAll);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * List Proof Versions
     * <p>
     * Gets a list of all versions of the given proofId in order from newest to oldest.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsGetVersionsTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = null;
        ProofsGetVersions200Response response = api.proofsGetVersions(sheetId, proofId, page, pageSize, includeAll);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * List Proof Attachments
     * <p>
     * Gets a list of all attachments that are in the proof, excluding discussion-level attachments in the proof.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsListAttachmentsTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = null;
        ProofsListAttachments200Response response = api.proofsListAttachments(sheetId, proofId, page, pageSize, includeAll);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * List Proof Discussions
     * <p>
     * Gets a list of all discussions that are in the proof.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsListDiscussionsTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        List<DiscussionInclude> include = null;
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = null;
        ProofsListDiscussions200Response response = api.proofsListDiscussions(sheetId, proofId, include, page, pageSize, includeAll);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * List Proof Request Actions
     * <p>
     * Gets a summarized list of all request actions associated with the specified proof.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsListRequestActionsTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = null;
        ProofsListRequestActions200Response response = api.proofsListRequestActions(sheetId, proofId, page, pageSize, includeAll);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Update Proof Status
     * <p>
     * Sets the proof status as either complete or incomplete.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void proofsUpdateTest() throws ApiException {
        Long sheetId = null;
        Long proofId = TestData.ProofData.id;
        ProofsUpdateRequest proofsUpdateRequest = null;
        Proof response = api.proofsUpdate(sheetId, proofId, proofsUpdateRequest);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

}
