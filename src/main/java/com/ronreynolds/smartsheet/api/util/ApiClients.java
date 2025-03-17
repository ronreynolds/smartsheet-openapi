package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.ApiClient;
import com.ronreynolds.smartsheet.Configuration;
import com.ronreynolds.util.config.Settings;
import com.ronreynolds.util.string.ToString;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class ApiClients {
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String HEADER_ASSUME_USER = "Assume-User";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_CHANGE_AGENT = "Smartsheet-Change-Agent";
    private static final String HEADER_USER_AGENT = "User-Agent";

    private static final AtomicReference<ApiClient> defaultClient = new AtomicReference<>();
    // simple logging flags for now
    @Setter
    private static volatile boolean logRequest = Settings.getBool("LOG_REQUEST", false);
    @Setter
    private static volatile boolean logResponse = Settings.getBool("LOG_RESPONSE", false);
    private static volatile String authToken;
    private static volatile Servers server;
    private static volatile String assumedUser;
    private static volatile String changeAgent;
    private static volatile String userAgent;
    private static volatile Duration timeoutDuration;

    static {
        // same as Java SDK
        setAuthToken(Settings.get("SMARTSHEET_ACCESS_TOKEN"));  // note, this supports system-props AND env-vars
        setServer(Servers.US);
        setUserAgent(null);
        // getDefaultClient() so that Configuration.getDefaultApiClient() returns a properly configured client?
    }

    /**
     * this method uses its own ref to store the ApiClient and overwrites the one in Configuration if it has to create a new one;
     * this behavior will change with future openapi-configgen when we can set the defaultApiClientFactory to lazy-create our own ApiClient
     *
     * @return the ApiClient with proper auth headers
     */
    public static ApiClient getDefaultClient() {
        ApiClient client = defaultClient.get();
        if (client == null) {
            client = defaultClient.updateAndGet(val -> {
                if (val == null) {
                    val = createNewClient();
                }
                return val;
            });
            // overwrite the global singleton; FIXME with future config-gen version
            Configuration.setDefaultApiClient(client);
        }
        return client;
    }

    public static void resetClient() {
        defaultClient.set(null);    // force next request for a client to trigger a rebuild
    }

    public static void setServer(Servers newServer) {
        server = newServer;
        resetClient();
    }

    public static void setAuthToken(String key) {
        authToken = key;
        resetClient();
    }

    public static void setAssumedUser(String user) {
        assumedUser = user;
        resetClient();
    }

    public static void setChangeAgent(String agent) {
        changeAgent = agent;
        resetClient();
    }

    public static void setUserAgent(String agent) {
        userAgent = generateUserAgent(agent);   // Smartsheet code ALWAYS mangles the user-agent with OS and JVM info
        resetClient();
    }

    public static void setMaxRetryTimeMillis(long millis) {
        // FIXME - supporting this requires handling different types of error codes from the server which indicate if we
        // should call again - see {@code com.smartsheet.api.internal.http.DefaultHttpClient#shouldRetry}
        timeoutDuration = Duration.ofMillis(millis);
        resetClient();
    }

    /**
     * create and return an {@code ApiClient} configured with the current settings; exposed so code can create a new
     * {@code ApiClient} without replacing the current default client.
     */
    public static ApiClient createNewClient() {
        ApiClient client = new ApiClient();
        client.updateBaseUri(server.baseUrl);

        client.setRequestInterceptor(builder -> prepRequest(builder, getHeaderMap()));
        client.setResponseInterceptor(ApiClients::processResponse);

        return client;
    }

    /**
     * this makes a copy of our current header values so that we can provide the request-interceptor with a copy
     * @return a new Map containing all the headers we want to set (auth, assume-user, change-agent, user-agent)
     */
    private static Map<String, String> getHeaderMap() {
        Map<String, String> headers = new HashMap<>();
        headers.put(HEADER_AUTHORIZATION, "Bearer " + authToken);
//        headers.put(HttpHeaders.CONTENT_TYPE, JSON_CONTENT_TYPE);   // FIXME - this can't ALWAYS be true according to spec

        // copy volatiles to local immutables to avoid concurrent mod
        final String assumedUser = ApiClients.assumedUser;
        final String changeAgent = ApiClients.changeAgent;
        final String userAgent = ApiClients.userAgent;

        // Set assumed user
        if (assumedUser != null) {
            headers.put(HEADER_ASSUME_USER, ApiClient.urlEncode(assumedUser));
        }
        if (changeAgent != null) {
            headers.put(HEADER_CHANGE_AGENT, ApiClient.urlEncode(changeAgent));
        }
        if (userAgent != null) {
            headers.put(HEADER_USER_AGENT, userAgent);
        }
        return headers;
    }

    private static void prepRequest(HttpRequest.Builder requestBuilder, Map<String, String> headerMap) {
        headerMap.forEach(requestBuilder::setHeader);
        // not the same as retry (see com.smartsheet.api.internal.http.DefaultHttpClient#shouldRetry for retry logic)
        if (timeoutDuration != null) {
            requestBuilder.timeout(timeoutDuration);
        }
        if (logRequest) {
            log.info("request - {}", ToString.toString(requestBuilder.build()));
        }
    }

    // TODO - trace logging of request and response parts and whole
    // TODO - max trace logging length

    private static void processResponse(HttpResponse<InputStream> response) {
        if (logResponse) {
            log.info("response - {}", response);
        }
    }

    /**
     * Compose a User-Agent string that represents this version of the SDK (along with platform info).
     * copied from com.smartsheet.api.internal.SmartsheetImpl#generateUserAgent with minor mods so it can be static
     *
     * @return a User-Agent string
     */
    private static String generateUserAgent(String userAgent) {
        String title = null;
        String thisVersion = null;

        if (userAgent == null) {
            StackTraceElement[] callers = Thread.currentThread().getStackTrace();
            String module = null;
            String callerClass = null;
            int stackIdx;
            for (stackIdx = callers.length - 1; stackIdx >= 0; stackIdx--) {
                callerClass = callers[stackIdx].getClassName();
                try {
                    Class<?> clazz = Class.forName(callerClass);
                    ClassLoader classLoader = clazz.getClassLoader();
                    // skip JRE classes
                    if (classLoader == null) {
                        continue;
                    }
                    String classFilePath = callerClass.replace(".", "/") + ".class";
                    URL classUrl = classLoader.getResource(classFilePath);
                    if (classUrl != null) {
                        String classUrlPath = classUrl.getPath();
                        int jarSeparator = classUrlPath.indexOf('!');
                        if (jarSeparator > 0) {
                            module = classUrlPath.substring(0, jarSeparator);
                            // extract the last path element (the jar name only)
                            module = module.substring(module.lastIndexOf('/') + 1);
                            break;
                        }
                    }
                } catch (Exception ex) {
                    // Empty Catch Block
                }
            }
            userAgent = module + "!" + callerClass;
        }
        final Properties properties = new Properties();
        try (InputStream stream = ClassLoader.getSystemResourceAsStream("sdk.properties")) {
            if (stream != null) {
                properties.load(stream);
                thisVersion = properties.getProperty("sdk.version");
                title = properties.getProperty("sdk.name");
            }
        } catch (IOException ignore) {
        }
        return title + "/" + thisVersion + "/" + userAgent + "/" + System.getProperty("os.name") + " " +
                System.getProperty("java.vm.name") + " " + System.getProperty("java.vendor") + " " +
                System.getProperty("java.version");
    }

    public enum Servers {
        US("https://api.smartsheet.com/2.0"),
        GOV_US("https://api.smartsheetgov.com/2.0"),
        EU("https://api.smartsheet.eu/2.0");

        private final String baseUrl;

        Servers(String url) {
            baseUrl = url;
        }
    }
}
