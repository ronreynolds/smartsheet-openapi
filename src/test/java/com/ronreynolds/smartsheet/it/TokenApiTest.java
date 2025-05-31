package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.TokensApi;
import com.ronreynolds.smartsheet.model.GrantType;
import com.ronreynolds.smartsheet.model.Result;
import com.ronreynolds.smartsheet.model.Token;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for TokenApi
 */
@Disabled("TokenApiTest not yet implemented")
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TokenApiTest {

    private final TokensApi api = new TokensApi();

    /**
     * Revoke Access Token
     * <p>
     * Revokes the access token used to make this request. The access token is no longer valid, and subsequent API calls made
     * using the token fail.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void tokensDeleteTest() throws ApiException {
        Boolean deleteAllForApiClient = null;
        Result response = api.tokensDelete(deleteAllForApiClient);
        log.info("{}", response);
        assertThat(response).isNotNull();
        // TODO: test validations
    }

    /**
     * Gets or Refreshes an Access Token
     * <p>
     * Gets or refreshes an access token, as part of the OAuth process.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void tokensGetOrRefreshTest() throws ApiException {
        String clientId = null;
        GrantType grantType = null;
        String contentType = null;
        String clientSecret = null;
        String code = null;
        String hash = null;
        String refreshToken = null;
        String redirectUrl = null;
        Token response = api.tokensGetOrRefresh(
                clientId, grantType, contentType, clientSecret, code, hash, refreshToken, redirectUrl);
        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

}
