package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.GroupsApi;
import com.ronreynolds.smartsheet.model.AddGroup200Response;
import com.ronreynolds.smartsheet.model.AddGroupRequest;
import com.ronreynolds.smartsheet.model.GetGroup200Response;
import com.ronreynolds.smartsheet.model.ListGroups200Response;
import com.ronreynolds.smartsheet.model.ResultPrefix;
import com.ronreynolds.smartsheet.model.UpdateGroupRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for GroupsApi
 */
@Disabled("GroupsApiTest not yet implemented")
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupsApiTest {
    private final GroupsApi api = new GroupsApi();


    /**
     * Add Group
     * <p>
     * Creates a new group.  **_This operation is only available to group administrators and system administrators._**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addGroupTest() throws ApiException {
        AddGroupRequest addGroupRequest = null;
        AddGroup200Response response = api.addGroup(addGroupRequest);

        // TODO: test validations
        System.out.println(response);
        assertThat(response).isNotNull();
    }

    /**
     * Delete Group
     * <p>
     * Deletes the group specified in the URL.  **_This operation is only available to group administrators and system
     * administrators._**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteGroupTest() throws ApiException {
        Long groupId = null;
        ResultPrefix response = api.deleteGroup(groupId);

        // TODO: test validations
        System.out.println(response);
        assertThat(response).isNotNull();
    }

    /**
     * Get Group
     * <p>
     * Gets information about an array of [Group Members](../../tag/groupMembersObjects#section/Group-Member-Object) for the
     * group specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getGroupTest() throws ApiException {
        Long groupId = null;
        GetGroup200Response response = api.getGroup(groupId);

        // TODO: test validations
        System.out.println(response);
        assertThat(response).isNotNull();
    }

    /**
     * List Org Groups
     * <p>
     * Gets a list of all groups in an organization account. To fetch the members of an individual group, use the [Get Group](.
     * ./../tag/groups#operation/get-group) operation.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listGroupsTest() throws ApiException {
        Boolean includeAll = null;
        OffsetDateTime modifiedSince = null;
        Boolean numericDates = null;
        Integer page = null;
        Integer pageSize = null;
        ListGroups200Response response = api.listGroups(includeAll, modifiedSince, numericDates, page, pageSize);

        // TODO: test validations
        System.out.println(response);
        assertThat(response).isNotNull();
    }

    /**
     * Update Group
     * <p>
     * Updates the Group specified in the URL.  **_This operation is only available to group administrators and system
     * administrators._**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateGroupTest() throws ApiException {
        Long groupId = null;
        UpdateGroupRequest updateGroupRequest = null;
        AddGroup200Response response = api.updateGroup(groupId, updateGroupRequest);

        // TODO: test validations
        System.out.println(response);
        assertThat(response).isNotNull();
    }
}
