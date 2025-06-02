package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.DashboardsApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.AccessLevel;
import com.ronreynolds.smartsheet.model.ContainerDestinationForCopy;
import com.ronreynolds.smartsheet.model.ContainerDestinationForMove;
import com.ronreynolds.smartsheet.model.CopySight200Response;
import com.ronreynolds.smartsheet.model.GenericResult;
import com.ronreynolds.smartsheet.model.ListSights200Response;
import com.ronreynolds.smartsheet.model.Result;
import com.ronreynolds.smartsheet.model.SetSightPublishStatus200Response;
import com.ronreynolds.smartsheet.model.Share;
import com.ronreynolds.smartsheet.model.SharingInclude;
import com.ronreynolds.smartsheet.model.Sight;
import com.ronreynolds.smartsheet.model.SightInclude;
import com.ronreynolds.smartsheet.model.SightLevel;
import com.ronreynolds.smartsheet.model.SightName;
import com.ronreynolds.smartsheet.model.SightPublish;
import com.ronreynolds.smartsheet.model.SightPublishAccess;
import com.ronreynolds.smartsheet.model.UpdateReportShare200Response;
import com.ronreynolds.smartsheet.model.UpdateReportShareRequest;
import com.ronreynolds.smartsheet.model.UpdateSight200Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * API tests for DashboardsApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DashboardsApiTest {
    private final DashboardsApi api = new DashboardsApi();


    /**
     * Copy Dashboard
     * <p>
     * Creates a copy of the specified dashboard.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void copySightTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        ContainerDestinationForCopy containerDestination = null;
        CopySight200Response response = api.copySight(sightId, Constants.noContentType, containerDestination);
        assertThat(response).isNotNull();

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Delete Dashboard
     * <p>
     * Deletes the dashboard specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void deleteSightTest() throws ApiException {
        for (Long dashboardId : TestData.DashboardData.sightIdsToDelete) {
            GenericResult response = api.deleteSight(dashboardId);
            log.info("{}", response);
            // TODO: test validations
            assertThat(response).isNotNull();
        }
    }

    /**
     * Delete Dashboard Share
     * <p>
     * Deletes the share specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(10)  // last test group to clean up data created in previous tests (possible fail due to replication delays)
    public void deleteSightShareTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        for (String shareId : TestData.DashboardData.shareIdsToDelete) {
            Result response = api.deleteSightShare(sightId, shareId);
//            log.info("{}", response);
            assertThat(response).satisfies(TestData::successfulResult);
        }
    }

    /**
     * Get Dashboard
     * <p>
     * Gets the specified dashboard.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("until we can resolve the response->model mapping issue")
    public void getSightTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        Integer accessApiLevel = null;
        List<SightInclude> include = null;
        SightLevel level = null;
        Boolean numericDates = null;
        Sight response = api.getSight(sightId, accessApiLevel, include, level, numericDates);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Get Dashboard Publish Status
     * <p>
     * Gets the dashboard &#39;publish&#39; settings.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getSightPublishStatusTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        SightPublish response = api.getSightPublishStatus(sightId);

//        log.info("{}", response);
        assertThat(response).isNotNull();
        assertThat(response.getReadOnlyFullUrl()).isNotBlank();
        assertThat(response.getReadOnlyFullEnabled()).isTrue();
        assertThat(response.getReadOnlyFullAccessibleBy()).isSameAs(SightPublishAccess.ALL);
    }

    /**
     * List Dashboard Shares
     * <p>
     * Gets a list of all users and groups to whom the specified dashboard is shared, and their access level.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(5)
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
        assertThat(response.getData()).anySatisfy(share -> {
            assertThat(share.getAccessLevel()).isSameAs(AccessLevel.VIEWER);
            assertThat(share.getEmail()).isEqualTo(TestData.ShareData.shareToEmail);
        });
    }

    /**
     * List Dashboards
     * <p>
     * Gets a list of all dashboards that the user has access to.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listSightsTest() throws ApiException {
        Integer accessApiLevel = null;
        Boolean includeAll = null;
        OffsetDateTime modifiedSince = null;
        Boolean numericDates = null;
        Integer page = null;
        Integer pageSize = null;
        ListSights200Response response = api.listSights(accessApiLevel, includeAll, modifiedSince, numericDates, page, pageSize);
        assertThat(response).satisfies(TestData::pagedResultHasData);
        assertThat(response.getData()).satisfies(TestData.DashboardData::assertContains);
    }

    /**
     * Move Dashboard
     * <p>
     * Moves the specified dashboard to a new location.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void moveSightTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        ContainerDestinationForMove containerDestination = null;
        CopySight200Response response = api.moveSight(sightId, Constants.noContentType, containerDestination);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Set Dashboard Publish Status
     * <p>
     * Publishes or unpublishes a dashboard.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void setSightPublishStatusTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        SightPublish sightPublish = SightPublish.builder()
                .readOnlyFullEnabled(true)
                .build();
        SetSightPublishStatus200Response response = api.setSightPublishStatus(sightId, Constants.noContentType, sightPublish);

//        log.info("{}", response);

        assertThat(response).satisfies(TestData::successfulResult);
        assertThat(response.getResult()).satisfies(result -> {
            assertThat(result).isNotNull();
            assertThat(result.getReadOnlyFullAccessibleBy()).isSameAs(SightPublishAccess.ALL);
            assertThat(result.getReadOnlyFullEnabled()).isTrue();
            assertThat(result.getReadOnlyFullUrl()).isNotBlank();
        });
    }

    /**
     * Share Dashboard
     * <p>
     * Shares a dashboard with the specified users and groups.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(1)
    public void shareSightTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        Integer accessApiLevel = null;
        Boolean sendEmail = false;

        // circular share should fail
        ApiException fail = assertThrows(ApiException.class, () -> api.shareSight(sightId, accessApiLevel, sendEmail,
                Share.builder().accessLevel(AccessLevel.COMMENTER).email(TestData.UserData.email).build()));
        assertThat(fail.getCode()).isEqualTo(400);
        assertThat(fail).hasMessageContainingAll("The share already exists.", "\"errorCode\" : 1025");

        Share share = Share.builder()
                .accessLevel(AccessLevel.VIEWER)
                .email(TestData.ShareData.shareToEmail)
                .build();
        var response = api.shareSight(sightId, accessApiLevel, sendEmail, share);
        assertThat(response).as("successful result").satisfies(TestData::successfulResult);
        for (Share newShare : assertThat(response.getResult()).isNotNull().actual()) {
            TestData.DashboardData.shareIdsToDelete.add(newShare.getId());
            assertThat(newShare.getEmail()).isEqualTo(TestData.ShareData.shareToEmail);
            assertThat(newShare.getAccessLevel()).isSameAs(AccessLevel.VIEWER);
        }
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

        log.info("{}", response);
        assertThat(response).satisfies(TestData.DashboardData::assertShare);
    }

    /**
     * Update Dashboard
     * <p>
     * Updates (renames) the specified dashboard.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void updateSightTest() throws ApiException {
        Long sightId = TestData.DashboardData.id;
        Boolean numericDates = null;
        SightName updateSightRequest = SightName.builder()
                .name("new dashboard name")
                .build();
        UpdateSight200Response response = api.updateSight(sightId, numericDates, Constants.noContentType, updateSightRequest);

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
        UpdateReportShare200Response response = api.updateSightShare(sightId, shareId, accessApiLevel, updateReportShareRequest);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }
}
