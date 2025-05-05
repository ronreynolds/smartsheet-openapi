package com.ronreynolds.smartsheet.it.extension;

import com.ronreynolds.smartsheet.Configuration;
import com.ronreynolds.smartsheet.api.util.ApiClients;
import com.ronreynolds.util.config.Settings;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store.CloseableResource;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class BeforeAllTests implements BeforeAllCallback, CloseableResource {
    private static volatile boolean setupDone = false;

    @Override
    public void beforeAll(final ExtensionContext context) throws Exception {
        if (!setupDone) {
            // lock-out concurrent threads attempting to do setup
            synchronized (BeforeAllTests.class) {
                // check if another thread finished the setup while we were stuck at synchronized
                if (!setupDone) {
                    doSetup();
                    setupDone = true;
                }
            }
        } else {
            log.info("setup already done by another thread");
        }
    }

    private void doSetup() {
        // no API calls will succeed without this token (except maybe server-info)
        assertThat(Settings.get("SMARTSHEET_ACCESS_TOKEN"))
                .as("SMARTSHEET_ACCESS_TOKEN is blank")
                .isNotBlank();
        Configuration.setDefaultApiClient(ApiClients.createNewClient());
    }

    @Override
    public void close() {
        // not necessary; just verifying things seem to be working as expected
        log.info("completed all tests");
    }
}