package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.FoldersApi;
import com.ronreynolds.smartsheet.api.HomeApi;
import com.ronreynolds.smartsheet.api.util.ApiClients;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.Folder;
import com.ronreynolds.smartsheet.model.FolderWorkspaceInclude;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for HomeApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HomeApiTest {
    private final HomeApi api = new HomeApi();

    /**
     * Create Folder
     * <p>
     * Creates a new folder.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    void createHomeFolderTest() throws ApiException {
        String newFolderName = "test folder " + System.currentTimeMillis();
        ApiClients.setLogRequest(true);
        Folder newFolder = Folder.builder().name(newFolderName).build();
        var response = api.createHomeFolder(newFolder, Constants.noContentType);
        assertThat(response).satisfies(TestData::successfulResult);

        // the response folder is quite minimal (id, name, permalink)
        assertThat(response.getResult()).satisfies(folder -> {
            assertThat(folder).isNotNull();
            assertThat(folder.getId()).isPositive();
//FIXME            assertThat(folder.getAccessLevel()).isNull();
//FIXME            assertThat(folder.getCreatedAt()).isNull();
//FIXME            assertThat(folder.getModifiedAt()).isNull();
            assertThat(folder.getFavorite()).isNull();
            assertThat(folder.getFolders()).isEmpty();
            assertThat(folder.getName()).isEqualTo(newFolderName);
            assertThat(folder.getPermalink()).isNotBlank();
            assertThat(folder.getReports()).isEmpty();
            assertThat(folder.getSheets()).isEmpty();
            assertThat(folder.getSights()).isEmpty();
            assertThat(folder.getTemplates()).isEmpty();
        });
        var deleteResult = new FoldersApi().deleteFolder(response.getResult().getId());
        log.info("{}", deleteResult);
        assertThat(deleteResult).isNotNull();
        // TODO more validation
    }

    /**
     * List Folders in Home
     * <p>
     * Gets a list of folders in your Home tab. The list contains an abbreviated Folder object for each folder.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    void homeListFoldersTest() throws ApiException {
        ApiClients.setLogRequest(true);
        Boolean includeAll = null;
        Integer page = null;
        Integer pageSize = null;
        var response = api.homeListFolders(includeAll, page, pageSize);

        // TODO: test validations
        log.info("{}", response);
        assertThat(response).isNotNull();
    }

    /**
     * List Contents
     * <p>
     * Gets a nested list of all Home objects shared to the user, including dashboards, folders, reports, sheets, and
     * templates, as shown on the \&quot;Home\&quot; tab.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    void listHomeContentsTest() throws ApiException {
        List<FolderWorkspaceInclude> include = null;
        var response = api.listHomeContents(include);

        // TODO: test validations
        log.info("{}", response);
        assertThat(response).isNotNull();
    }
}
