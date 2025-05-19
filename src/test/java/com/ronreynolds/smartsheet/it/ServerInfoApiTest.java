package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.ServerInfoApi;
import com.ronreynolds.smartsheet.model.Currency;
import com.ronreynolds.smartsheet.model.FontFamily;
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
        assertThat(response).isNotNull();

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
                .contains(FontFamily.builder().name("Arial").traits(List.of(FontFamily.TraitsEnum.SANS_SERIF)).build());
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

/*
class ServerInfo {
    formats: class FormatTables {
        defaults: 0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
        bold: [none, on]
        color: [none, #000000, #FFFFFF, transparent, #FFEBEE, #FFF3DF, #FFFEE6, #E7F5E9, #E2F2FE, #F4E4F5, #F2E8DE, #FFCCD2,
        #FFE1AF, #FEFF85, #C6E7C8, #B9DDFC, #EBC7EF, #EEDCCA, #E5E5E5, #F87E7D, #FFCD7A, #FEFF00, #7ED085, #5FB3F9, #D190DA,
        #D0AF8F, #BDBDBD, #EA352E, #FF8D00, #FFED00, #40B14B, #1061C3, #9210AD, #974C00, #757575, #991310, #EA5000, #EBC700,
        #237F2E, #0B347D, #61058B, #592C00]
        currency: [class Currency {
            code: none
            symbol: none
        }, class Currency {
            code: ARS
            symbol: $
        }, class Currency {
            code: AUD
            symbol: $
        }, class Currency {
            code: BRL
            symbol: R$
        }, class Currency {
            code: CAD
            symbol: $
        }, class Currency {
            code: CLP
            symbol: $
        }, class Currency {
            code: EUR
            symbol: €
        }, class Currency {
            code: GBP
            symbol: £
        }, class Currency {
            code: ILS
            symbol: ₪
        }, class Currency {
            code: INR
            symbol: ₹
        }, class Currency {
            code: JPY
            symbol: ¥
        }, class Currency {
            code: MXN
            symbol: $
        }, class Currency {
            code: RUB
            symbol: ₽
        }, class Currency {
            code: USD
            symbol: $
        }, class Currency {
            code: ZAR
            symbol: R
        }, class Currency {
            code: CHF
            symbol: CHF
        }, class Currency {
            code: CNY
            symbol: ¥
        }, class Currency {
            code: DKK
            symbol: kr.
        }, class Currency {
            code: HKD
            symbol: $
        }, class Currency {
            code: KRW
            symbol: ₩
        }, class Currency {
            code: NOK
            symbol: kr
        }, class Currency {
            code: NZD
            symbol: $
        }, class Currency {
            code: SEK
            symbol: kr
        }, class Currency {
            code: SGD
            symbol: $
        }]
        dateFormat: [LOCALE_BASED, MMMM_D_YYYY, MMM_D_YYYY, D_MMM_YYYY, YYYY_MM_DD_HYPHEN, YYYY_MM_DD_DOT, DWWWW_MMMM_D_YYYY,
        DWWW_DD_MMM_YYYY, DWWW_MM_DD_YYYY, MMMM_D, D_MMMM]
        decimalCount: [0, 1, 2, 3, 4, 5]
        fontFamily: [class FontFamily {
            name: Arial
            traits: [sans-serif]
        }, class FontFamily {
            name: Tahoma
            traits: [sans-serif]
        }, class FontFamily {
            name: Times New Roman
            traits: [serif]
        }, class FontFamily {
            name: Verdana
            traits: [sans-serif]
        }]
        fontSize: [8, 9, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36]
        horizontalAlign: [default, left, center, right]
        italic: [none, on]
        numberFormat: [none, NUMBER, CURRENCY, PERCENT]
        strikethrough: [none, on]
        textWrap: [none, on]
        thousandsSeparator: [none, on]
        underline: [none, on]
        verticalAlign: [default, top, middle, bottom]
    }

 */