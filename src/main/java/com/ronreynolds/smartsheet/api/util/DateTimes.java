package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.util.string.StringUtils;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * parses and formats the ISO-8601 format used by the Smartsheet API
 */
public class DateTimes {
    // same as https://github.com/smartsheet/smartsheet-java-sdk/blob/mainline/src/main/java/com/smartsheet/api/internal/json/JacksonJsonSerializer.java#L81
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneId.of("UTC"));

    private DateTimes() {}

    public static String format(TemporalAccessor dateTime) {
        return FORMATTER.format(dateTime);
    }

    public static ZonedDateTime parseToZoned(String dateText) {
        return StringUtils.isNotBlank(dateText) ? ZonedDateTime.parse(dateText, FORMATTER) : null;
    }

    public static OffsetDateTime parseToOffset(String dateText) {
        return StringUtils.isNotBlank(dateText) ? parseToZoned(dateText).toOffsetDateTime() : null;
    }
}
