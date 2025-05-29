package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.WorkspacesApi;
import com.ronreynolds.smartsheet.api.util.ApiClients;
import com.ronreynolds.smartsheet.model.ContainerDestinationForCopy;
import com.ronreynolds.smartsheet.model.CreateWorkspace200Response;
import com.ronreynolds.smartsheet.model.FolderNameOnly;
import com.ronreynolds.smartsheet.model.FolderWorkspaceInclude;
import com.ronreynolds.smartsheet.model.GenericResult;
import com.ronreynolds.smartsheet.model.GetWorkspaceFolders200Response;
import com.ronreynolds.smartsheet.model.ListWorkspaces200Response;
import com.ronreynolds.smartsheet.model.Result;
import com.ronreynolds.smartsheet.model.Share;
import com.ronreynolds.smartsheet.model.ShareReport200Response;
import com.ronreynolds.smartsheet.model.SkipRemap;
import com.ronreynolds.smartsheet.model.UpdateReportShare200Response;
import com.ronreynolds.smartsheet.model.UpdateReportShareRequest;
import com.ronreynolds.smartsheet.model.UpdateWorkspace200Response;
import com.ronreynolds.smartsheet.model.UpdateWorkspaceRequest;
import com.ronreynolds.smartsheet.model.Workspace;
import com.ronreynolds.smartsheet.model.WorkspaceCreateInclude;
import com.ronreynolds.smartsheet.model.WorkspaceListing;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for WorkspacesApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WorkspacesApiTest {
    private final WorkspacesApi api = new WorkspacesApi();

    /**
     * Copy Workspace
     * <p>
     * Copies a workspace.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void copyWorkspaceTest() throws ApiException {
        Long workspaceId = TestData.WorkspaceData.id;
        ContainerDestinationForCopy containerDestination = ContainerDestinationForCopy.builder()
                .newName("New Test Workspace")
                .build();
        String contentType = null;
        List<WorkspaceCreateInclude> include = null;
        List<SkipRemap> skipRemap = null;
        var response = api.copyWorkspace(workspaceId, containerDestination, contentType, include, skipRemap);
        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Create Workspace
     * <p>
     * Creates a new workspace.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(1)
    public void createWorkspaceTest() throws ApiException {
        WorkspaceListing workspace = WorkspaceListing.builder()
                .name("new test workspace")
                .build();
        Integer accessApiLevel = null;
        String contentType = null;
        List<WorkspaceCreateInclude> include = null;
        List<SkipRemap> skipRemap = null;
        CreateWorkspace200Response response = api.createWorkspace(workspace, accessApiLevel, contentType, include, skipRemap);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
        TestData.temporaryWorkspaceIds.add(response.getResult().getId());    // for deleting later
    }

    /**
     * Create a Folder
     * <p>
     * Creates a new folder.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(2)
    public void createWorkspaceFolderTest() throws ApiException {
        Long workspaceId = TestData.WorkspaceData.id;
        FolderNameOnly createWorkspaceFolderRequest = FolderNameOnly.builder()
                // "The value for folder.name must be 50 characters in length or less"
                .name(("new workspace folder - " + ZonedDateTime.now()).substring(0, 49))
                .build();
        String contentType = null;
        var response = api.createWorkspaceFolder(workspaceId, createWorkspaceFolderRequest, contentType);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Delete Workspace
     * <p>
     * Deletes a workspace.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(10)
    public void deleteWorkspaceTest() throws ApiException {
        if (TestData.temporaryWorkspaceIds.isEmpty()) {
            System.out.println("no temporary workspace IDs for deleting");
            return; // we have nothing to delete
        }
        for (Long workspaceId : TestData.temporaryWorkspaceIds) {
            GenericResult response = api.deleteWorkspace(workspaceId);
            log.info("{}", response);
            assertThat(response).isNotNull();

            // TODO: test validations
        }
        // if we fail might as well leave the IDs there?
        TestData.temporaryWorkspaceIds.clear();
    }

    /**
     * Delete Workspace Share
     * <p>
     * Deletes the share specified in the URL.  **_This operation is only available to system administrators._**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(9)   // before deleteWorkspaceTest()
    @Disabled("need test data")
    public void deleteWorkspaceShareTest() throws ApiException {
        Long workspaceId = null;
        String shareId = null;
        Result response = api.deleteWorkspaceShare(workspaceId, shareId);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Get Workspace
     * <p>
     * Gets a Workspace object.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(5)   // after createWorkspaceTest() and copyWorkspaceTest()
    public void getWorkspaceTest() throws ApiException {
        try (var ignore = ApiClients.logRequestContext()) {
            Long workspaceId = TestData.WorkspaceData.id;
            Integer accessApiLevel = null;
            List<FolderWorkspaceInclude> include = null;
            Boolean loadAll = true;
            Workspace response = api.getWorkspace(workspaceId, accessApiLevel, include, loadAll);
            assertThat(response).satisfies(TestData.WorkspaceData::assertEquals);

            for (Long id : TestData.temporaryWorkspaceIds) {
                assertThat(api.getWorkspace(workspaceId, accessApiLevel, include, loadAll)).isNotNull();
            }
        }
    }

    /**
     * List Workspace Folders
     * <p>
     * Lists a workspace&#39;s folders.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getWorkspaceFoldersTest() throws ApiException {
        Long workspaceId = TestData.WorkspaceData.id;
        Boolean includeAll = null;
        Integer page = null;
        Integer pageSize = null;
        GetWorkspaceFolders200Response response = api.getWorkspaceFolders(workspaceId, includeAll, page, pageSize);
//        log.info("{}", response);
        assertThat(response).satisfies(TestData::pagedResultHasData);
        assertThat(response.getData()).anySatisfy(TestData.WorkspaceData::assertFolderInWorkspace);
    }

    /**
     * List Workspace Shares
     * <p>
     * Gets a list of all users and groups to whom the specified Workspace is shared, and their access level.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listWorkspaceSharesTest() throws ApiException {
        Long workspaceId = TestData.WorkspaceData.id;
        Integer accessApiLevel = null;
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = true;
        var response = api.listWorkspaceShares(workspaceId, accessApiLevel, page, pageSize, includeAll);
        assertThat(response).satisfies(TestData::pagedResultHasDataNullPageSize);
        assertThat(response.getData()).anySatisfy(TestData.WorkspaceData::assertShare);
    }

    /**
     * List Workspaces
     * <p>
     * Gets a list of workspaces that the user has access to. The list contains an abbreviated Workspace object for each
     * workspace.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listWorkspacesTest() throws ApiException {
        Integer accessApiLevel = null;
        Boolean includeAll = null;
        Integer page = null;
        Integer pageSize = null;
        ListWorkspaces200Response response = api.listWorkspaces(accessApiLevel, includeAll, page, pageSize);
        assertThat(response).satisfies(TestData::pagedResultHasData);
        assertThat(response.getData()).anySatisfy(TestData.WorkspaceData::assertEquals);
    }

    /**
     * Share Workspace
     * <p>
     * Shares a Workspace with the specified users and groups. This operation supports both single-object and bulk semantics.
     * **_This operation is only available to system administrators._**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void shareWorkspaceTest() throws ApiException {
        Long workspaceId = null;
        Integer accessApiLevel = null;
        Boolean sendEmail = null;
        List<Share> share = null;
        ShareReport200Response response = api.shareWorkspace(workspaceId, accessApiLevel, sendEmail, share);
        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Get Workspace Share
     * <p>
     * Gets the share specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void shareWorkspaceGetTest() throws ApiException {
        Long workspaceId = null;
        String shareId = null;
        Integer accessApiLevel = null;
        Share response = api.shareWorkspaceGet(workspaceId, shareId, accessApiLevel);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Update Workspace
     * <p>
     * Updates a workspace.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void updateWorkspaceTest() throws ApiException {
        Long workspaceId = null;
        Integer accessApiLevel = null;
        UpdateWorkspaceRequest updateWorkspaceRequest = null;
        UpdateWorkspace200Response response = api.updateWorkspace(workspaceId, accessApiLevel, updateWorkspaceRequest);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Update Workspace Share
     * <p>
     * Updates the access level of a user or group for the specified workspace.  **_This operation is only available to system
     * administrators._**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void updateWorkspaceShareTest() throws ApiException {
        Long workspaceId = null;
        String shareId = null;
        Integer accessApiLevel = null;
        UpdateReportShareRequest updateReportShareRequest = null;
        UpdateReportShare200Response response =
                api.updateWorkspaceShare(workspaceId, shareId, accessApiLevel, updateReportShareRequest);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }
}