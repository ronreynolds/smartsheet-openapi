package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.SharingApi;
import com.ronreynolds.smartsheet.model.Result;
import com.ronreynolds.smartsheet.model.Share;
import com.ronreynolds.smartsheet.model.ShareReport200Response;
import com.ronreynolds.smartsheet.model.SharingInclude;
import com.ronreynolds.smartsheet.model.UpdateReportShareRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for SharingApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SharingApiTest {
    private final SharingApi api = new SharingApi();


    /**
     * Delete Report Share
     * <p>
     * Deletes the share specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void deleteReportShareTest() throws ApiException {
        Long reportId = null;
        String shareId = null;
        Integer accessApiLevel = null;
        Result response = api.deleteReportShare(reportId, shareId, accessApiLevel);

        log.info("{}", response);
        assertThat(response).satisfies(TestData::successfulResult);
    }

    /**
     * Delete Sheet Share
     * <p>
     * Deletes the share specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void deleteSheetShareTest() throws ApiException {
        Long sheetId = null;
        String shareId = null;
        Integer accessApiLevel = null;
        Result response = api.deleteSheetShare(sheetId, shareId, accessApiLevel);

        log.info("{}", response);
        assertThat(response).satisfies(TestData::successfulResult);
    }

    /**
     * Delete Dashboard Share
     * <p>
     * Deletes the share specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void deleteSightShareTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        String shareId = null;
        Result response = api.deleteSightShare(sightId, shareId);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Delete Workspace Share
     * <p>
     * Deletes the share specified in the URL.  **_This operation is only available to system administrators._**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void deleteWorkspaceShareTest() throws ApiException {
        Long workspaceId = null;
        String shareId = null;
        Result response = api.deleteWorkspaceShare(workspaceId, shareId);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * List Report Shares
     * <p>
     * Gets a list of all users and groups to whom the specified Report is shared, and their access level. This operation
     * supports query string parameters for pagination of results.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listReportSharesTest() throws ApiException {
        Long reportId = TestData.ReportData.id;
        SharingInclude sharingInclude = null;
        Boolean includeAll = true;
        Integer page = null;
        Integer pageSize = null;
        var response = api.listReportShares(reportId, sharingInclude, includeAll, page, pageSize);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData::pagedResultHasDataNullPageSize);
        assertThat(response.getData()).anySatisfy(TestData.ReportData::assertShare);
    }

    /**
     * List Sheet Shares
     * <p>
     * Gets a list of all users and groups to whom the specified Sheet is shared, and their access level. This operation
     * supports query string parameters for pagination of results. For more information, see Paging Query String Parameters.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listSheetSharesTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Integer accessApiLevel = null;
        SharingInclude sharingInclude = null;
        Boolean includeAll = true;
        Integer page = null;
        Integer pageSize = null;
        var response = api.listSheetShares(sheetId, accessApiLevel, sharingInclude, includeAll, page, pageSize);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData::pagedResultHasDataNullPageSize);
        assertThat(response.getData()).anySatisfy(TestData.SheetData::assertShare);
    }

    /**
     * List Dashboard Shares
     * <p>
     * Gets a list of all users and groups to whom the specified dashboard is shared, and their access level.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listSightSharesTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        Integer accessApiLevel = null;
        SharingInclude sharingInclude = null;
        Boolean includeAll = null;
        Integer page = null;
        Integer pageSize = null;
        var response = api.listSightShares(sightId, accessApiLevel, sharingInclude, includeAll, page, pageSize);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData::pagedResultHasData);
        assertThat(response.getData()).anySatisfy(TestData.DashboardData::assertShare);
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

//        log.info("{}", response);
        assertThat(response).satisfies(TestData::pagedResultHasDataNullPageSize);
        assertThat(response.getData()).anySatisfy(TestData.WorkspaceData::assertShare);
    }

    /**
     * Share Report
     * <p>
     * Shares a Report with the specified users and groups.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void shareReportTest() throws ApiException {
        Long reportId = null;
        Boolean sendEmail = null;
        List<Share> share = null;
        ShareReport200Response response = api.shareReport(reportId, sendEmail, share);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Get Report Share
     * <p>
     * Gets the share specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void shareReportGetTest() throws ApiException {
        Long reportId = TestData.ReportData.id;
        String shareId = TestData.ShareData.shareId;
        Integer accessApiLevel = null;
        Share response = api.shareReportGet(reportId, shareId, accessApiLevel);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData.ReportData::assertShare);
    }

    /**
     * Share Sheet
     * <p>
     * Shares a sheet with the specified users and groups.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void shareSheetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Integer accessApiLevel = null;
        Boolean sendEmail = null;
        List<Share> share = null;
        var response = api.shareSheet(sheetId, accessApiLevel, sendEmail, share);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Get Sheet Share.
     * <p>
     * Gets the share specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void shareSheetGetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        String shareId = TestData.ShareData.shareId;
        Integer accessApiLevel = null;
        Share response = api.shareSheetGet(sheetId, shareId, accessApiLevel);
//        log.info("{}", response);
        assertThat(response).satisfies(TestData.SheetData::assertShare);
    }

    /**
     * Share Dashboard
     * <p>
     * Shares a dashboard with the specified users and groups.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void shareSightTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        Integer accessApiLevel = null;
        Boolean sendEmail = null;
        Share share = null;
        var response = api.shareSight(sightId, accessApiLevel, sendEmail, share);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Get Dashboard Share
     * <p>
     * Gets a list of all users and groups to whom the specified dashboard is shared, and their access level.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void shareSightGetTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        String shareId = TestData.ShareData.shareId;
        Integer accessApiLevel = null;
        Share response = api.shareSightGet(sightId, shareId, accessApiLevel);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData.DashboardData::assertShare);
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
        var response = api.shareWorkspace(workspaceId, accessApiLevel, sendEmail, share);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Get Workspace Share
     * <p>
     * Gets the share specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void shareWorkspaceGetTest() throws ApiException {
        Long workspaceId = TestData.WorkspaceData.id;
        String shareId = TestData.ShareData.shareId;
        Integer accessApiLevel = null;
        Share response = api.shareWorkspaceGet(workspaceId, shareId, accessApiLevel);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData.WorkspaceData::assertShare);
    }

    /**
     * Update Report Share
     * <p>
     * Updates the access level of a user or group for the specified report.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void updateReportShareTest() throws ApiException {
        Long reportId = null;
        String shareId = null;
        Integer accessApiLevel = null;
        UpdateReportShareRequest updateReportShareRequest = null;
        var response = api.updateReportShare(reportId, shareId, accessApiLevel, updateReportShareRequest);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Update Sheet Share.
     * <p>
     * Updates the access level of a user or group for the specified sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void updateSheetShareTest() throws ApiException {
        Long sheetId = null;
        String shareId = null;
        Integer accessApiLevel = null;
        UpdateReportShareRequest updateReportShareRequest = null;
        var response = api.updateSheetShare(sheetId, shareId, accessApiLevel, updateReportShareRequest);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Update Dashboard Share
     * <p>
     * Updates the access level of a user or group for the specified dashboard.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void updateSightShareTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        String shareId = null;
        Integer accessApiLevel = null;
        UpdateReportShareRequest updateReportShareRequest = null;
        var response = api.updateSightShare(sightId, shareId, accessApiLevel, updateReportShareRequest);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
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
        var response = api.updateWorkspaceShare(workspaceId, shareId, accessApiLevel, updateReportShareRequest);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }
}
