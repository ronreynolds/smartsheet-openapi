package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.UsersApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.api.util.Converters;
import com.ronreynolds.smartsheet.it.TestData.UserData;
import com.ronreynolds.smartsheet.model.AddUser200Response;
import com.ronreynolds.smartsheet.model.GenericResult;
import com.ronreynolds.smartsheet.model.GetCurrentUser200Response;
import com.ronreynolds.smartsheet.model.GetUserInclude;
import com.ronreynolds.smartsheet.model.ListUsers200Response;
import com.ronreynolds.smartsheet.model.RemoveUser200Response;
import com.ronreynolds.smartsheet.model.RemoveUserRequest;
import com.ronreynolds.smartsheet.model.ResultPrefix;
import com.ronreynolds.smartsheet.model.UpdateUser200Response;
import com.ronreynolds.smartsheet.model.UpdateUserProfileImage200Response;
import com.ronreynolds.smartsheet.model.UpdateUserRequest;
import com.ronreynolds.smartsheet.model.User;
import com.ronreynolds.smartsheet.model.UserProfile;
import com.ronreynolds.smartsheet.model.UserProfileImageResponse;
import com.ronreynolds.smartsheet.model.UserUpdate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * API tests for UsersApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)   // enable processing of the @Order annotation to specify test ordering
public class UsersApiTest {
    private static final UsersApi api = new UsersApi();

    /**
     * Add User
     * <p>
     * Adds a user to the organization account.  * **_This operation is only available to system administrators_**  * **If
     * successful, and user auto provisioning (UAP) is on, and user matches the auto provisioning rules, user is added to the
     * org. If UAP is off, or user does not match UAP rules, user is invited to the org and must explicitly accept the
     * invitation to join.**  * **In some specific scenarios, supplied attributes such as firstName and lastName may be ignored
     * . For example, if you are inviting an existing Smartsheet user to join your organization account, and the invited user
     * has not yet accepted your invitation, any supplied firstName and lastName are ignored.**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(1)   // first test
    public void addUserTest() throws ApiException {
        Boolean sendEmail = false;
        String email = "test-user-" + System.currentTimeMillis() +"@example.com";
        User user = User.builder()
                .admin(false)
                .email(email)
                .firstName("Test")
                .lastName("User")
                .build();
        AddUser200Response response = api.addUser(sendEmail, user);
        assertThat(response).isNotNull();
        User newUser = assertThat(response.getResult()).isNotNull().actual();
        assertThat(newUser.getAdmin()).isFalse();
        assertThat(newUser.getEmail()).isEqualTo(email);
        assertThat(newUser.getFirstName()).isEqualTo("Test");
        assertThat(newUser.getLastName()).isEqualTo("User");
        assertThat(newUser.getName()).isEqualTo("Test User");

        log.info("added user {}", newUser.getId());
        TestData.temporaryUserIds.add(newUser.getId());
    }

    /**
     * Deactivate User
     * <p>
     * Deactivates a user in an organization account. User will no longer be able to access Smartsheet in any way. User&#39;s
     * assets will continue to be owned by this user until they are transferred to another user.  **This operation is only
     * available to system administrators of Enterprise organizations.** **Additionally, if organizations have Enterprise Plan
     * Manager (EPM) enabled, a system administrator of the main plan can provide a userId for a user belonging to a managed
     * plan within the EPM hierarchy.**  **NOTES:** * Currently unavailable for Smartsheet GOV (aka the Gov environment). *
     * This operation does not apply to users with an ISP based domain account (outlook.com, gmail.com, hotmail.com, live.com,
     * yahoo.com, aol.com, verizon.net, rocketmail.com, comcast.net, icloud.com, charter.net, web.de, mail.com, email.com, usa
     * .com, duck.com, mail.ru).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(2)
    @Disabled("possibly issue with edge-case where newly-created users aren't yet visible for deactivation?")
    public void deactivateUserTest() throws ApiException {
        // deactivate all temporary users (if any)
        if (TestData.temporaryUserIds.isEmpty()) {
            log.warn("no temporary users to deactivate");
            return;
        }
        // FIXME - the problem here is we're trying to deactivate NEWLY CREATED users and thus we hit edge-case issues
        for (Long userId : TestData.temporaryUserIds) {
            log.info("deactivating {}", userId);
            GenericResult response = api.deactivateUser(userId);
            assertThat(response).satisfies(TestData::successfulResult);
            log.info("deactivate-user response:{}", response);
        }
    }

    /**
     * Get Current User
     * <p>
     * Gets the current user  **NOTE:** For system administrators, the following UserProfile attributes are included in the
     * response:   * **customWelcomeScreenViewed** (only returned when an Enterprise user has viewed the [Custom Welcome
     * Screen](https://help.smartsheet.com/articles/1392225-customizing-a-welcome-message-upgrade-screen-enterprise-only))   *
     * **lastLogin** (only returned if the user has logged in)   * **sheetCount** (only returned if the status attribute is
     * ACTIVE; however, to increase performance, this returns a static &#39;0&#39;)
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getCurrentUserTest() throws ApiException {
        GetCurrentUser200Response currentUser = api.getCurrentUser(GetUserInclude.GROUPS);
        // test validation
        assertThat(currentUser).isNotNull().satisfies(UserData::assertEquals);

        // also tests getUser and User.equals methods
        UserProfile sameUser = api.getUser(currentUser.getId());
        // for whatever reason getCurrentUser does not return status but getUser does. :-?
        // and as of 2025-05-14 getUser returns a sheetCount of -1 instead of the proper value
        sameUser.setStatus(null);
        sameUser.setSheetCount(-1);
        assertThat(sameUser).isEqualTo(Converters.convert(currentUser));
    }

    /**
     * Get User
     * <p>
     * Gets the user specified in the URL.  * NOTE: For system administrators, the following UserProfile attributes are
     * included in the response):   * **admin**   * **customWelcomeScreenViewed** (only returned when an Enterprise user has
     * viewed the [Custom Welcome Screen](https://help.smartsheet
     * .com/articles/1392225-customizing-a-welcome-message-upgrade-screen-enterprise-only))   * **groupAdmin**   *
     * **lastLogin** (only returned if the user has logged in)   * **licensedSheetCreator**   * **resourceViewer**   *
     * **sheetCount** (only returned if the status attribute is ACTIVE)   * **status**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getUserTest() throws ApiException {
        UserProfile user = api.getUser(UserData.id);
        assertThat(user).isNotNull()
                .satisfies(UserData::assertEquals);
    }

    /**
     * List Users
     * <p>
     * Gets a list of users in the organization account. To filter by email, use the optional email query string parameter to
     * specify a list of users&#39; email addresses.  **NOTE:** If the API request is submitted by a system administrator, the
     * following User object attributes are included in the response (else, they are omitted from the response):   * **admin**
     * * **groupAdmin**   * **licensedSheetCreator**   * **resourceViewer**   * **sheetCount (omitted if the status attribute
     * is not ACTIVE)**   * **status**  **NOTE:** If the API request is submitted by a system administrator of an Enterprise
     * account, and [Custom Welcome Screen](https://help.smartsheet
     * .com/articles/1392225-customizing-a-welcome-message-upgrade-screen-enterprise-only) is enabled, the following [User
     * object](../../tag/usersObjects#section/User-Object) attributes are included in the response (else, they are omitted from
     * the response):   * **customWelcomeScreenViewed** (omitted if the user has never viewed the [Custom Welcome Screen]
     * (https://help.smartsheet.com/articles/1392225-customizing-a-welcome-message-upgrade-screen-enterprise-only))
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(9)
    public void listUsersTest() throws ApiException {
        List<String> email = null;
        String include = null;
        Boolean includeAll = null;
        OffsetDateTime modifiedSince = null;
        Boolean numericDates = null;
        Integer page = null;
        Integer pageSize = null;
        ListUsers200Response response = api.listUsers(email, include, includeAll, modifiedSince, numericDates, page, pageSize);

        // TODO: test validations
        log.info("list-users: {}", response);
        assertThat(response).satisfies(TestData::pagedResultHasData);
    }

    /**
     * Reactivate User
     * <p>
     * Reactivates a user in an organization account. User will regain to access Smartsheet and will have the same roles as
     * when they were deactivated.  **This operation is only available to system administrators of Enterprise organizations.**
     * **Additionally, if organizations have Enterprise Plan Manager (EPM) enabled, a system administrator of the main plan can
     * provide a userId for a user belonging to a managed plan within the EPM hierarchy.**  **NOTES:** * Currently unavailable
     * for Smartsheet GOV (aka the Gov environment). * This operation does not apply to users with an ISP based domain account
     * (outlook.com, gmail.com, hotmail.com, live.com, yahoo.com, aol.com, verizon.net, rocketmail.com, comcast.net, icloud
     * .com, charter.net, web.de, mail.com, email.com, usa.com, duck.com, mail.ru).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(5)
    @Disabled("You are not authorized to perform this action.")
    public void reactivateUserTest() throws ApiException {
        // reactivate all temporary users (if any)
        if (TestData.temporaryUserIds.isEmpty()) {
            log.warn("no temporary users to reactivate");
            return;
        }
        for (Long userId : TestData.temporaryUserIds) {
            log.info("reactivating {}", userId);
            GenericResult response = api.reactivateUser(userId);
            assertThat(response).satisfies(TestData::successfulResult);
            log.info("{}", response);
        }
    }

    /**
     * Remove User
     * <p>
     * Removes a user from an organization account. User is transitioned to a free collaborator with read-only access to owned
     * reports, sheets, Sights, workspaces, and any shared templates (unless those are optionally transferred to another user).
     * * **_This operation is only available to system administrators_**  * If the **transferTo** parameter is specified and
     * the removed user owns groups, the user specified via the **transferTo** parameter must have group admin rights.  * The
     * **transferTo** and **transferSheets** parameters cannot be specified for a user who has not yet accepted an invitation
     * to join the organization account (that is, if user **status&#x3D;PENDING**).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(10)
    public void removeUserTest() throws ApiException {
        // verify the expected 404
        ApiException thrown = assertThrows(ApiException.class, () -> api.removeUser(42L, null, null, null));
        assertThat(thrown).hasMessageContaining("User not found");

//        TestData.temporaryUserIds.add(3414752471869316L);
//        TestData.temporaryUserIds.add(8582663280846724L);

        // delete all temporary users (if any)
        if (TestData.temporaryUserIds.isEmpty()) {
            log.warn("no temporary users to delete");
            return;
        }
        for (Long userId : TestData.temporaryUserIds) {
            var response = api.removeUser(userId, null, null, null);
            assertThat(response).satisfies(TestData::successfulResult);
        }
    }

    /**
     * Update User
     * <p>
     * Updates the user specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(7)
    @Disabled("works but flaky with 404; read-after-write latency?")
    public void updateUserTest() throws ApiException {
        if (TestData.temporaryUserIds.isEmpty()) {
            log.warn("no temporary users to update");
            return;
        }

        Long userId = TestData.temporaryUserIds.get(0);
        // get previous user data
        var user = api.getUser(userId);
        log.info("updating {}", userId);

        UserUpdate updateUserRequest = UserUpdate.builder()
                .firstName("Fuzzy")
                .lastName("Wuzzy")
                .build();
        UpdateUser200Response response = api.updateUser(userId, updateUserRequest);
        log.info("update-user-response:{}", response);
        assertThat(response).satisfies(TestData::successfulResult);

        // put their data back as we found it
        response = api.updateUser(userId,
                UserUpdate.builder().firstName(user.getFirstName()).lastName(user.getLastName()).build());
        log.info("resetting updated-user-response:{}", response);
        assertThat(response).satisfies(TestData::successfulResult);
    }

    /**
     * Update User Profile Image
     * <p>
     * Uploads an image to the user profile.  Uploading a profile image differs from Adding an Image to a Cell in the following
     * ways:
     * A **Content-Length** header is not required
     * Allowable file types are limited to: gif, jpg, and png
     * Maximum file size is determined by the following rules:
     * If you have not defined a custom size and the image is larger than 1050 x 1050 pixels, Smartsheet scales the image down
     * to 1050 x 1050
     * If you have defined a custom size, Smartsheet uses that as the file size max
     * If the image is not square, Smartsheet uses a solid color to pad the image
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need image data")
    public void updateUserProfileImageTest() throws ApiException {
        Long userId = null;
        String contentType = null;
        File body = null;
        UpdateUserProfileImage200Response response = api.updateUserProfileImage(userId, contentType, body);

        log.info("update-user-profile-image response:{}", response);
        // TODO: test validations
    }

    @BeforeAll
    static void preListUsers() throws ApiException {
        log.info("pre-test all users:{}", api.listUsers(null, null, true, null, null, null, null));
    }

    @AfterAll
    static void postListUsers() throws ApiException {
        log.info("post-test all users:{}", api.listUsers(null, null, true, null, null, null, null));
    }
}
