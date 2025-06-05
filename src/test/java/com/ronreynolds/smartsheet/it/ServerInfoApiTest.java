package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.ServerInfoApi;
import com.ronreynolds.smartsheet.model.Currency;
import com.ronreynolds.smartsheet.model.FontFamily;
import com.ronreynolds.smartsheet.model.FontFamilyTrait;
import com.ronreynolds.smartsheet.model.FormatTables;
import com.ronreynolds.smartsheet.model.ServerInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for ServerInfoApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerInfoApiTest {
    private final ServerInfoApi api = new ServerInfoApi();

    /**
     * Gets application constants.
     * <p>
     * Gets application constants.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void serverinfoGetTest() throws ApiException {
        ServerInfo response = api.serverinfoGet();
        log.info("{}", response);
        assertThat(response).isNotNull();
        assertThat(response.getServerVersion()).isNotBlank();   // 430.0.0 as of 2025-06-05
        assertThat(response.getAppleAuthInfos()).isNotEmpty();
        assertThat(response.getAzureAuthInfo()).isNotNull();

        String[] noneOn = {"none", "on"};  // value for many format fields
        Predicate<String> onlyDigits = Pattern.compile("\\d+").asMatchPredicate();

        FormatTables formats = response.getFormats();
        assertThat(formats).isNotNull();
        assertThat(formats.getBold()).isNotEmpty()
                .containsOnly(noneOn);
        assertThat(formats.getColor()).isNotEmpty()
                .contains("none", "transparent");
        assertThat(formats.getCurrency()).isNotEmpty()
                .contains(Currency.builder().code("USD").symbol("$").build());
        assertThat(formats.getDateFormat()).isNotEmpty()
                .contains("YYYY_MM_DD_HYPHEN"); // ISO-8601
        assertThat(formats.getDecimalCount()).isNotEmpty()
                .allMatch(onlyDigits);
        assertThat(formats.getDefaults()).isNotBlank();
        assertThat(formats.getFontFamily()).isNotEmpty()
                .contains(FontFamily.builder().name("Arial").traits(List.of(FontFamilyTrait.SANS_SERIF)).build());
        assertThat(formats.getFontSize()).isNotEmpty()
                .anyMatch(onlyDigits);
        assertThat(formats.getHorizontalAlign()).isNotEmpty()
                .contains("default", "left", "center", "right");
        assertThat(formats.getItalic()).isNotEmpty()
                .containsOnly(noneOn);
        assertThat(formats.getNumberFormat()).isNotEmpty()
                .contains("none", "NUMBER", "CURRENCY", "PERCENT");
        assertThat(formats.getStrikethrough()).isNotEmpty()
                .containsOnly(noneOn);
        assertThat(formats.getTextWrap()).isNotEmpty()
                .containsOnly(noneOn);
        assertThat(formats.getThousandsSeparator()).isNotEmpty()
                .containsOnly(noneOn);
        assertThat(formats.getUnderline()).isNotEmpty()
                .containsOnly(noneOn);
        assertThat(formats.getVerticalAlign()).isNotEmpty()
                .contains("default", "top", "middle", "bottom");

        assertThat(response.getSupportedLocales()).isNotEmpty()
                // various common languages (English, Chinese, German, Dutch, Spanish, French, Italian, Polish, Russian, Japanese)
                .contains("en_US", "en_GB", "zh_CN", "de_DE", "nl_NL", "es_ES", "fr_FR", "it_IT", "pl_PL", "ru_RU", "ja_JP");
    }
}