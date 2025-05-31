package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.ContactsApi;
import com.ronreynolds.smartsheet.model.Contact;
import com.ronreynolds.smartsheet.model.GetContactInclude;
import com.ronreynolds.smartsheet.model.ListContacts200Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for ContactsApi
 */
@Slf4j
public class ContactsApiTest {
    private final ContactsApi api = new ContactsApi();


    /**
     * Get Contact
     * <p>
     * Gets the specified contact.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getContactTest() throws ApiException {
        String contactId = TestData.ContactData.contacts.iterator().next().getId();
        List<ContactInclude> include = null;
        Contact response = api.getContact(contactId, include);
//        log.info("{}", response);
        assertThat(response).satisfies(TestData.ContactData::assertFirstContact);
    }

    /**
     * List Contacts
     * <p>
     * Gets a list of the user&#39;s Smartsheet contacts.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listContactsTest() throws ApiException {
        Boolean includeAll = true;
        OffsetDateTime modifiedSince = null;
        Boolean numericDates = null;
        Integer page = null;
        Integer pageSize = null;
        ListContacts200Response response = api.listContacts(includeAll, modifiedSince, numericDates, page, pageSize);

        assertThat(response).satisfies(TestData::pagedResultHasDataNullPageSize);
        assertThat(response.getData()).satisfies(TestData.ContactData::assertContains);
    }
}
