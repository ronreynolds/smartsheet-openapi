package com.ronreynolds.smartsheet.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ronreynolds.smartsheet.ApiClient;
import com.ronreynolds.smartsheet.api.util.JacksonUtil;
import com.ronreynolds.util.logging.JULIntoSLF4J;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Disabled("until all server responses are parsing") // FIXME
public class SheetParsingTest {
    static {
        JULIntoSLF4J.install();
    }
    @Test
    void parseSheetJson() {
        String responseBody = assertDoesNotThrow(() -> Files.readString(Path.of("src/test/resources/raw-sheet.json")));
        ApiClient client = new ApiClient();
        ObjectMapper mapper = JacksonUtil.modifyObjectMapper(client.getObjectMapper()).build();
        // code copied (more or less) from com.ronreynolds.smartsheet.api.SheetsApi.getSheetWithHttpInfo()
        var sheet = assertDoesNotThrow(() -> mapper.readValue(responseBody, new TypeReference<Sheet>() {
        }));
        assertThat(sheet).isNotNull();
        assertThat(sheet.getColumns()).hasSize(16);
        assertThat(sheet.getRows()).hasSize(422);
    }
}
