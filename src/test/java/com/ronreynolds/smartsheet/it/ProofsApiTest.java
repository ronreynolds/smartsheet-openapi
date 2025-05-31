package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.ProofsApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.AttachmentsAttachToSheet200Response;
import com.ronreynolds.smartsheet.model.CommentRequest;
import com.ronreynolds.smartsheet.model.DiscussionCreationRequest;
import com.ronreynolds.smartsheet.model.DiscussionInclude;
import com.ronreynolds.smartsheet.model.GenericResult;
import com.ronreynolds.smartsheet.model.Proof;
import com.ronreynolds.smartsheet.model.ProofInclude;
import com.ronreynolds.smartsheet.model.ProofRequestBody;
import com.ronreynolds.smartsheet.model.UpdateProofStatusRequest;
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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        File body = null;
        AttachmentsAttachToSheet200Response response = api.proofsAttachToProof(sheetId, proofId, Constants.noContentType, body);
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
        Long sheetId = TestData.SheetData.id;
        Long rowId = TestData.RowData.id;
        File body = null;
        var response = api.proofsCreate(sheetId, rowId, Constants.noContentType, body);

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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        DiscussionCreationRequest request = DiscussionCreationRequest.builder()
                .comment(CommentRequest.builder().text("let's creat a discussion!").build())
                .build();
        var response = api.proofsCreateDiscussion(sheetId, proofId, Constants.noContentType, request);

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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        ProofRequestBody proofRequestBody = null;
        var response = api.proofsCreateProofRequests(sheetId, proofId, Constants.noContentType, proofRequestBody);

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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        File body = null;
        var response = api.proofsCreateVersion(sheetId, proofId, Constants.noContentType, body);

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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        GenericResult response = api.proofsDelete(sheetId, proofId);

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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        GenericResult response = api.proofsDeleteProofRequests(sheetId, proofId);

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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        GenericResult response = api.proofsDeleteVersion(sheetId, proofId);

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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        List<ProofInclude> include = null;
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
        Long sheetId = TestData.SheetData.id;
        Boolean includeAll = true;
        var response = api.proofsGetAllProofs(sheetId, Constants.allPages, Constants.noPageSize, includeAll);

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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        Boolean includeAll = true;
        var response = api.proofsGetVersions(sheetId, proofId, Constants.allPages, Constants.noPageSize, includeAll);

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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        Boolean includeAll = true;
        var response = api.proofsListAttachments(sheetId, proofId, Constants.allPages, Constants.noPageSize, includeAll);

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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        List<DiscussionInclude> include = null;
        Boolean includeAll = true;
        var response = api.proofsListDiscussions(sheetId, proofId, include, Constants.allPages, Constants.noPageSize, includeAll);

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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        Boolean includeAll = true;
        var response = api.proofsListRequestActions(sheetId, proofId, Constants.allPages, Constants.noPageSize, includeAll);

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
        Long sheetId = TestData.SheetData.id;
        Long proofId = TestData.ProofData.id;
        UpdateProofStatusRequest proofsUpdateRequest = UpdateProofStatusRequest.builder()
                .isCompleted(true)
                .build();
        Proof response = api.proofsUpdate(sheetId, proofId, proofsUpdateRequest);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

}
