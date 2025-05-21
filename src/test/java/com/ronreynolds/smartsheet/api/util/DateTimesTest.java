package com.ronreynolds.smartsheet.api.util;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DateTimesTest {
    @Test
    void parsingDates_works() {
        String dateText = "2025-03-31T15:36:52Z";
        OffsetDateTime date = assertDoesNotThrow(() -> DateTimes.parseToOffset(dateText));
        assertThat(date).isNotNull().isEqualTo(OffsetDateTime.of(2025, 3, 31, 15, 36, 52, 0, ZoneOffset.UTC));
    }
}