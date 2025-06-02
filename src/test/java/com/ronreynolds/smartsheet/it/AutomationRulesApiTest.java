package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.AutomationRulesApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.AutomationRule;
import com.ronreynolds.smartsheet.model.AutomationruleUpdate200Response;
import com.ronreynolds.smartsheet.model.AutomationrulesList200Response;
import com.ronreynolds.smartsheet.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for AutomationRulesApi
 */
@Slf4j
@Disabled("AutomationRulesApiTest not yet implemented")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)   // enable processing of the @Order annotation to specify test ordering
public class AutomationRulesApiTest {
    private final AutomationRulesApi api = new AutomationRulesApi();

    /**
     * Delete an Automation Rule
     * <p>
     * Deletes an automation rule.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(4) // last test
    public void automationruleDeleteTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long automationRuleId = null;   // FIXME
        Result response = api.automationruleDelete(sheetId, automationRuleId);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Get an Automation Rule
     * <p>
     * Returns the specified automation rule, including any action values.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(1)
    public void automationruleGetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long automationRuleId = null;   // FIXME
        AutomationRule response = api.automationruleGet(sheetId, automationRuleId);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Update an Automation Rule
     * <p>
     * Updates an existing automation rule.  When sending an AutomationRule, you must always specify **action.type** and it
     * must match the existing rule type.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(2)
    public void automationruleUpdateTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long automationRuleId = null;   // FIXME
        AutomationRule automationRule = AutomationRule.builder()
                .name("Test Workflow rename")
                .enabled(false)
                .build();
        AutomationruleUpdate200Response response = api.automationruleUpdate(sheetId, automationRuleId, Constants.noContentType, automationRule);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * List All Automation Rules
     * <p>
     * Returns all automation rules associated with the specified sheet.  Multistep workflows are not returned via the API.
     * Instead, you&#39;ll see an error 400 - 1266: This rule is not accessible through the API. Only single-action
     * notifications, approval requests, or update requests qualify.  For users of Smartsheet for Slack, note that Slack
     * notifications are not returned.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(3)
    public void automationrulesListTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Boolean includeAll = true;
        Integer page = null;
        Integer pageSize = null;
        AutomationrulesList200Response response = api.automationrulesList(sheetId, includeAll, page, pageSize);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }
}
