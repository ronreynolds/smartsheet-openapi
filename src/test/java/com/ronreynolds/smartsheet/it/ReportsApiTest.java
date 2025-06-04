package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.ReportsApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.Report;
import com.ronreynolds.smartsheet.model.ReportExclude;
import com.ronreynolds.smartsheet.model.ReportInclude;
import com.ronreynolds.smartsheet.model.ReportPublish;
import com.ronreynolds.smartsheet.model.Result;
import com.ronreynolds.smartsheet.model.Share;
import com.ronreynolds.smartsheet.model.SharingInclude;
import com.ronreynolds.smartsheet.model.SheetEmail;
import com.ronreynolds.smartsheet.model.UpdateReportShare200Response;
import com.ronreynolds.smartsheet.model.UpdateReportShareRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for ReportsApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReportsApiTest {
    private final ReportsApi api = new ReportsApi();


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
        Long reportId = TestData.ReportData.id;
        String shareId = null;
        Integer accessApiLevel = null;
        Result response = api.deleteReportShare(reportId, shareId, accessApiLevel);
        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Get Report
     * <p>
     * Gets a report based on the specified ID
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getReportTest() throws ApiException {
        Long reportId = TestData.ReportData.id;
        String accept = null;
        Integer accessApiLevel = null;
        List<ReportInclude> include = null;
        List<ReportExclude> exclude = null;
        Integer pageSize = null;
        Integer page = null;
        Report response = api.getReport(reportId, accept, accessApiLevel, include, exclude, pageSize, page,
                Constants.defaultReportLevel);

//        log.info("{}", response);
        assertThat(response).isNotNull().satisfies(TestData.ReportData::assertDeepEquals);
    }

    /**
     * Gets a Report&#39;s publish settings
     * <p>
     * Get a Report&#39;s publish settings based on the specified ID
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getReportPublishTest() throws ApiException {
        Long reportId = TestData.ReportData.id;
        ReportPublish response = api.getReportPublish(reportId);
        // FIXME for some reason report-publish data not being returned by server
        assertThat(response).satisfies(TestData.ReportData::assertPublish);
    }

    /**
     * List Reports
     * <p>
     * List all Reports accessible to the user.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getReportsTest() throws ApiException {
        OffsetDateTime modifiedSince = null;
        var response = api.getReports(modifiedSince);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData::pagedResultHasData);
        assertThat(response.getData()).isNotEmpty().anySatisfy(TestData.ReportData::assertBrief);
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
        Boolean includeAll = null;
        Integer page = null;
        Integer pageSize = null;
        var response = api.listReportShares(reportId, sharingInclude, includeAll, page, pageSize);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).satisfies(TestData::pagedResultHasData);
        assertThat(response.getData()).isNotEmpty()
                .anySatisfy(TestData.ReportData::assertShare);
    }

    /**
     * Send report via email
     * <p>
     * Sends the report as a PDF attachment via email to the designated recipients
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void sendReportViaEmailTest() throws ApiException {
        Long reportId = TestData.ReportData.id;
        SheetEmail sheetEmail = SheetEmail.builder().build();
        Result response = api.sendReportViaEmail(reportId, Constants.noContentType, sheetEmail);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Set a Report&#39;s publish status
     * <p>
     * Sets the publish status of the report and returns the new status, including the URL of any enabled publishing.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void setReportPublishTest() throws ApiException {
        Long reportId = TestData.ReportData.id;
        ReportPublish reportPublish = ReportPublish.builder().build();
        var response = api.setReportPublish(reportId, Constants.noContentType, reportPublish);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
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
        Long reportId = TestData.ReportData.id;
        Boolean sendEmail = null;
        List<Share> share = null;
        var response = api.shareReport(reportId, sendEmail, share);

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
    @Disabled("not yet implemented")
    public void shareReportGetTest() throws ApiException {
        Long reportId = TestData.ReportData.id;
        String shareId = null;
        Integer accessApiLevel = null;
        Share response = api.shareReportGet(reportId, shareId, accessApiLevel);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Update Report Share
     * <p>
     * Updates the access level of a user or group for the specified report.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("not yet implemented")
    public void updateReportShareTest() throws ApiException {
        Long reportId = TestData.ReportData.id;
        String shareId = null;
        Integer accessApiLevel = null;
        UpdateReportShareRequest updateReportShareRequest = null;
        UpdateReportShare200Response response = api.updateReportShare(reportId, shareId, accessApiLevel,
                updateReportShareRequest);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }
}
