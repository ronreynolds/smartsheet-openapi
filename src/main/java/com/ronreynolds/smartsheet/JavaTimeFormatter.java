package com.ronreynolds.smartsheet;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * used by code-gen code to parse/format timestamps;
 * based on https://developer.okta.com/okta-sdk-java/apidocs/org/openapitools/client/JavaTimeFormatter.html and reading
 * the source-code of the java codegen
 *
 * note, this is a total WIP; it has to be converted to mustache and moved into (i think) src/main/resources/...
 * https://www.palo-it.com/en/blog/spring-boot-client-and-server-code-generation-using-openapi-3-specs
 */
public class JavaTimeFormatter {
    private volatile DateTimeFormatter formatter  = DateTimeFormatter.ISO_INSTANT;

    public DateTimeFormatter getOffsetDateTimeFormatter() {
        return formatter;
    }

    public void setOffsetDateTimeFormatter(DateTimeFormatter offsetDateTimeFormatter) {
//        formatter = offsetDateTimeFormatter; - we don't want it changed
    }

    public OffsetDateTime parseOffsetDateTime(String str) {
        try {
            return OffsetDateTime.parse(str, formatter);
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String formatOffsetDateTime(OffsetDateTime offsetDateTime) {
        return formatter.format(offsetDateTime);
    }
}
