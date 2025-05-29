package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.AlternateEmailAddressApi;
import com.ronreynolds.smartsheet.api.util.ApiClients;
import com.ronreynolds.smartsheet.model.AddAlternateEmail;
import com.ronreynolds.smartsheet.model.AddAlternateEmail200Response;
import com.ronreynolds.smartsheet.model.AlternateEmail;
import com.ronreynolds.smartsheet.model.GenericResult;
import com.ronreynolds.smartsheet.model.ListAlternateEmails200Response;
import com.ronreynolds.smartsheet.model.PromoteAlternateEmail200Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for AlternateEmailAddressApi
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlternateEmailAddressApiTest {
    private final AlternateEmailAddressApi api = new AlternateEmailAddressApi();
    private List<Long> altEmailIdsToDelete;

    /**
     * Add Alternate Emails
     * <p>
     * Adds one or more alternate email addresses for the specified user.  **_This operation is only available to system
     * administrators._**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("failing with a 500 error!")
    @Order(1)
    public void addAlternateEmailTest() throws ApiException {
        ApiClients.setLogRequest(true);
        Long userId = TestData.UserData.id;
        List<AddAlternateEmail> emailAddress = List.of(
                AddAlternateEmail.builder().email("foo@example.com").build(),
                AddAlternateEmail.builder().email("bar@example.com").build());
        AddAlternateEmail200Response response = api.addAlternateEmail(userId, emailAddress);
        // FIXME - currently failing with a 500 error (unexpected server-side error)
        // TODO: more test validations
        assertThat(response).isNotNull();
        System.out.println(response);
        // save these IDs for deleting later
        altEmailIdsToDelete = response.getData().stream().map(AlternateEmail::getId).collect(Collectors.toList());
    }

    /**
     * Delete Alternate Email
     * <p>
     * Deletes the specified alternate email address for the specified user.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("can't create new alt-emails to delete yet")
    @Order(5)   // last test - cleans up data added by other tests
    public void deleteAlternateEmailTest() throws ApiException {
        assertThat(altEmailIdsToDelete).as("no alternate email IDs to delete").isNotEmpty();

        Long userId = TestData.UserData.id;
        for (Long alternateEmailId : altEmailIdsToDelete) {
            GenericResult response = api.deleteAlternateEmail(userId, alternateEmailId);
            // TODO: test validations
            assertThat(response).isNotNull();
            System.out.println(response);
        }
    }

    /**
     * Get Alternate Email
     * <p>
     * Gets the specified alternate email.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(2)
    public void getAlternateEmailTest() throws ApiException {
        Long userId = TestData.UserData.id;
        Long alternateEmailId = TestData.UserData.AlternateEmailData.id;
        AlternateEmail response = api.getAlternateEmail(userId, alternateEmailId);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(alternateEmailId);
    }

    /**
     * List Alternate Emails
     * <p>
     * Gets a list of the alternate emails for the specified user.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(4)
    public void listAlternateEmailsTest() throws ApiException {
        Long userId = TestData.UserData.id;
        ListAlternateEmails200Response response = api.listAlternateEmails(userId);

        assertThat(response).satisfies(TestData::pagedResultHasData);
        assertThat(response.getData()).anyMatch(altEmail -> altEmail.getId() == TestData.UserData.AlternateEmailData.id);
    }

    /**
     * Make Alternate Email Primary
     * <p>
     * Makes the specified alternate email address to become the primary email address for the specified user.  * **_This
     * operation is only available to system administrators_**  The alternate email address can only be made primary if both
     * conditions are met:   * The primary email address domain is validated   * The alternate email address is confirmed or
     * the alternate email address domain is validated
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need to create new alt-emails first")
    @Order(3)
    public void promoteAlternateEmailTest() throws ApiException {
        Long userId = TestData.UserData.id;
        Long alternateEmailId = null;
        PromoteAlternateEmail200Response response = api.promoteAlternateEmail(userId, alternateEmailId);

        // TODO: test validations
        assertThat(response).isNotNull();
        System.out.println(response);
    }

}
