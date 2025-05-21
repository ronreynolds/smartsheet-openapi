package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.EventsApi;
import com.ronreynolds.smartsheet.model.AcceptEncoding;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * API tests for EventsApi
 */
public class EventsApiTest {
    private final EventsApi api = new EventsApi();

    /**
     * List Events
     * <p>
     * Gets events that are occurring in your Smartsheet organization account. Examples of events are creation, update, load,
     * and delete of sheets, reports, dashboards, attachments, users, etc.  Each event type has a distinct combination of
     * objectType and action. Many event types have additional information returned under an additionalDetails object. See the
     * &lt;a href&#x3D;\&quot;https://smartsheet-platform.github.io/event-reporting-docs/\&quot; target&#x3D;\&quot;
     * _blank\&quot;&gt;Event Reporting reference documentation&lt;/a&gt; for a complete list of all currently supported
     * events, including their respective objectType, action, and additionalDetails properties.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listEventsTest() throws ApiException {
        AcceptEncoding acceptEncoding = null;
        OffsetDateTime since = null;
        OffsetDateTime to = null;
        String streamPosition = null;
        Integer maxCount = null;
        Boolean numericDates = null;
        Long managedPlanId = null;
        ApiException expectedFailure = assertThrows(ApiException.class, () ->
                api.listEvents(acceptEncoding, since, to, streamPosition, maxCount, numericDates, managedPlanId));

        // test validations (sorta)
        assertThat(expectedFailure)
                .hasMessageContaining("listEvents call failed with: 403")
                .hasMessageContaining("The operation you are attempting to perform is not supported by your plan.");
    }
}
