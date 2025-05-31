package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.GroupMembersApi;
import com.ronreynolds.smartsheet.model.AddGroupMembers200Response;
import com.ronreynolds.smartsheet.model.GenericResult;
import com.ronreynolds.smartsheet.model.GroupMember;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for GroupMembersApi
 */
@Disabled("GroupMembersApiTest not yet implemented")
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupMembersApiTest {
    private final GroupMembersApi api = new GroupMembersApi();

    /**
     * Add Group Members
     * <p>
     * Adds one or more members to a group.  **_This operation supports both single-object and bulk semantics. For more
     * information, see Optional Bulk Operations._**  If called with a single [GroupMember object](../.
     * ./tag/groupMembersObjects), and that group member already exists, error code **1129** is returned. If called with an
     * array of [GroupMember objects](../../tag/groupMembersObjects), any users specified in the array that are already group
     * members are ignored and omitted from the response.  **_This operation is only available to group administrators and
     * system administrators._**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addGroupMembersTest() throws ApiException {
        Long groupId = null;
        List<GroupMember> groupMember = null;
        var response = api.addGroupMembers(groupId, groupMember);
        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Delete Group Members
     * <p>
     * Removes a member from a group.  **_This operation is only available to group administrators and system administrators._**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteGroupMembersTest() throws ApiException {
        Long groupId = null;
        Long userId = null;
        GenericResult response = api.deleteGroupMembers(groupId, userId);
        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

}
