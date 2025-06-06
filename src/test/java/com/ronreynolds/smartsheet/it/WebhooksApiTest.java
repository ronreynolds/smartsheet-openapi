package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.WebhooksApi;
import com.ronreynolds.smartsheet.api.util.ApiClients;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.CreateWebhook200Response;
import com.ronreynolds.smartsheet.model.CreateWebhookRequest;
import com.ronreynolds.smartsheet.model.CreateWebhookRequestSubscope;
import com.ronreynolds.smartsheet.model.ListWebhooks200Response;
import com.ronreynolds.smartsheet.model.ResetSharedSecret200Response;
import com.ronreynolds.smartsheet.model.Result;
import com.ronreynolds.smartsheet.model.UpdateWebhookRequest;
import com.ronreynolds.smartsheet.model.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for WebhooksApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebhooksApiTest {
    private final WebhooksApi api = new WebhooksApi();


    /**
     * Create Webhook
     * <p>
     * Creates a new Webhook.  A webhook is not enabled by default when it is created. Once you&#39;ve created a webhook, you
     * can enable it by using the Update Webhook operation to set **enabled** to **true**.  When a row is deleted on a sheet,
     * even if you are using a **subscope** to monitor columns only and the cell in that column for that row is empty, you will
     * receive a \&quot;row.deleted\&quot; event.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(1)
    public void createWebhookTest() throws ApiException {
        CreateWebhookRequest createWebhookRequest = CreateWebhookRequest.builder()
                .scope(CreateWebhookRequest.ScopeEnum.SHEET)
                .scopeObjectId(TestData.SheetData.id)
                .subscope(CreateWebhookRequestSubscope.builder().columnIds(TestData.ColumnData.allColumnIds).build())
                .callbackUrl(TestData.WebhookData.url)
                .name(TestData.WebhookData.name)
                .events(TestData.WebhookData.events)
                .version(TestData.WebhookData.version)
                .build();
        CreateWebhook200Response response = api.createWebhook(Constants.noContentType, createWebhookRequest);

        log.info("{}", response);
        assertThat(response).satisfies(TestData::successfulResult);
        assertThat(response.getResult()).satisfies(TestData.WebhookData::assertCreated);
    }

    /**
     * Delete Webhook
     * <p>
     * Deletes the specified Webhook.  Using this operation permanently deletes the specified webhook. To temporarily disable a
     * webhook, use the Update Webhook operation to set **enabled** to **false**.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(10)
    public void deleteWebhookTest() throws ApiException {
        Long webhookId = TestData.WebhookData.id;
        Result response = api.deleteWebhook(webhookId);
        log.info("{}", response);
        assertThat(response).satisfies(TestData::successfulResult);
    }

    /**
     * Get Webhook
     * <p>
     * Gets a Webhook based on the specified ID
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(4)
    public void getWebhookTest() throws ApiException {
        Long webhookId = TestData.WebhookData.id;
        Webhook response = api.getWebhook(webhookId);
        log.info("{}", response);
        assertThat(response).satisfies(TestData.WebhookData::assertEquals);
    }

    /**
     * List Webhooks
     * <p>
     * Gets the list of all *webhooks* that the user owns (if a user-generated token was used to make the request) or the list
     * of all webhooks associated with the third-party app (if a third-party app made the request). Items in the response are
     * ordered by API cient name &gt; webhook name &gt; creation date.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(4)
    public void listWebhooksTest() throws ApiException {
        Boolean includeAll = true;
        ListWebhooks200Response response = api.listWebhooks(includeAll, Constants.allPages, Constants.noPageSize);

        log.info("{}", response);
        assertThat(response).satisfies(TestData::pagedResultHasDataNullPageSize);
        assertThat(response.getData()).satisfies(TestData.WebhookData::assertContains);
    }

    /**
     * Reset Shared Secret
     * <p>
     * Resets the shared secret for the specified webhook. For more information about how a shared secret is used, see
     * Authenticating Callbacks. This operation can be used to rotate an API client&#39;s webhooks&#39; shared secrets at
     * periodic intervals to provide additional security.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(5)
    public void resetSharedSecretTest() throws ApiException {
        try (var ignore = ApiClients.logRequestContext()) {
            Long webhookId = TestData.WebhookData.id;
            // 400 - {"errorCode":1008,"message":"Unable to parse request. The following error occurred: EOF.","refId":"bIs21s"}
            ResetSharedSecret200Response response = api.resetSharedSecret(webhookId, Constants.noContentType);

            // TODO: test validations
            log.info("{}", response);
            assertThat(response).isNotNull();
        }
    }

    /**
     * Update Webhook
     * <p>
     * Updates the specified Webhook. The following properties can be updated: * callbackUrl (optional) * enabled (optional) *
     * events (optional) * name (optional) * version (optional)  When setting a webhook&#39;s **enabled** to **true** using
     * this operation, the behavior and result depend on the webhook&#39;s **status** and may result in a webhook verification
     * being triggered, or in some cases, an error being returned. See Webhook Status for more details.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Order(6)
    public void updateWebhookTest() throws ApiException {
        Long webhookId = TestData.WebhookData.id;
        UpdateWebhookRequest updateWebhookRequest = UpdateWebhookRequest.builder()
                .callbackUrl(TestData.WebhookData.url)
                .enabled(true)
                .events(TestData.WebhookData.events)
                .name(TestData.WebhookData.name)
                .version(TestData.WebhookData.version)
                .build();
        CreateWebhook200Response response = api.updateWebhook(webhookId, Constants.noContentType, updateWebhookRequest);
        log.info("{}", response);
        assertThat(response).satisfies(TestData::successfulResult);
        assertThat(response.getResult()).satisfies(TestData.WebhookData::assertEnabled);
    }
}
