package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.FoldersApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.ContainerDestinationForCopy;
import com.ronreynolds.smartsheet.model.ContainerDestinationForMove;
import com.ronreynolds.smartsheet.model.Folder;
import com.ronreynolds.smartsheet.model.FolderWorkspaceInclude;
import com.ronreynolds.smartsheet.model.GenericResult;
import com.ronreynolds.smartsheet.model.ListFolders200Response;
import com.ronreynolds.smartsheet.model.SheetCopyExclude;
import com.ronreynolds.smartsheet.model.SheetCopyInclude;
import com.ronreynolds.smartsheet.model.SkipRemap;
import com.ronreynolds.smartsheet.model.UpdateFolder200Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for FoldersApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FoldersApiTest {
    private final FoldersApi api = new FoldersApi();


    /**
     * Copy Folder
     * <p>
     * Copies a folder.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void copyFolderTest() throws ApiException {
        Long folderId = null;
        ContainerDestinationForCopy containerDestination = null;
        List<SheetCopyInclude> include = null;
        SheetCopyExclude exclude = null;
        List<SkipRemap> skipRemap = null;
        var response = api.copyFolder(folderId, containerDestination, Constants.noContentType, include, exclude, skipRemap);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Create Folder
     * <p>
     * Creates a new folder.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void createFolderFolderTest() throws ApiException {
        Long folderId = null;
        Folder folder = null;
        List<SheetCopyInclude> include = null;
        SheetCopyExclude exclude = null;
        List<SkipRemap> skipRemap = null;
        var response = api.createFolderFolder(folderId, folder, Constants.noContentType, include, exclude, skipRemap);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Delete Folder
     * <p>
     * Deletes a folder.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void deleteFolderTest() throws ApiException {
        Long folderId = null;
        GenericResult response = api.deleteFolder(folderId);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Get Folder
     * <p>
     * Gets a Folder object.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getFolderTest() throws ApiException {
        Long folderId = TestData.FolderData.id;
        List<FolderWorkspaceInclude> include = null;
        Folder response = api.getFolder(folderId, include);
//        log.info("{}", response);
        assertThat(response).satisfies(TestData.FolderData::assertEquals);
    }

    /**
     * List Folders
     * <p>
     * Gets a list of folders in a given folder. The list contains an abbreviated Folder object for each folder.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listFoldersTest() throws ApiException {
        Long parentFolderId = TestData.FolderData.id;
        Boolean includeAll = null;
        Integer page = null;
        Integer pageSize = null;
        ListFolders200Response response = api.listFolders(parentFolderId, includeAll, page, pageSize);
        assertThat(response).satisfies(TestData::pagedResultHasData);
        assertThat(response.getData()).satisfies(TestData.FolderData::assertChildFolders);
    }

    /**
     * Move Folder
     * <p>
     * Moves a folder.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void moveFolderTest() throws ApiException {
        Long folderId = TestData.FolderData.id;
        ContainerDestinationForMove containerDestination = null;
        var response = api.moveFolder(folderId, containerDestination, Constants.noContentType);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Update Folder
     * <p>
     * Updates a folder.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void updateFolderTest() throws ApiException {
        Long folderId = TestData.FolderData.id;
        Folder folder = null;
        UpdateFolder200Response response = api.updateFolder(folderId, folder);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }
}
