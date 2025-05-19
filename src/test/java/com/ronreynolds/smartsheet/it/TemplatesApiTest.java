package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.TemplatesApi;
import com.ronreynolds.smartsheet.model.AccessLevel;
import com.ronreynolds.smartsheet.model.Template;
import com.ronreynolds.smartsheet.model.TemplateType;
import com.ronreynolds.smartsheet.model.TemplatesList200Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for TemplatesApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TemplatesApiTest {
    private final TemplatesApi api = new TemplatesApi();


    /**
     * List User-Created Templates
     * <p>
     * Gets a list of user-created templates that the user has access to.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void templatesListTest() throws ApiException {
        TemplatesList200Response response = api.templatesList(null, true, 1, null);

        // if you pass null into the API you get null back (seems weird)
        assertThat(response).satisfies(TestData::pagedResultHasDataNullPageSize);
        assertThat(response.getData())
                .hasSize(response.getTotalCount())
                .contains(TestData.TemplateData.template);
    }

    /**
     * List Public Templates
     * <p>
     * Gets a list of public templates that the user has access to.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void templatesListPublicTest() throws ApiException {
        Integer accessApiLevel = null;
        Boolean includeAll = null;
        TemplateType level = null;  // 0 = sheet; 1 = all
        Integer pageSize = 100; // default page size
        TemplatesList200Response response = api.templatesListPublic(accessApiLevel, includeAll, level, 1, pageSize);

        assertThat(response).isNotNull();
        int totalCount = assertThat(response.getTotalCount()).isGreaterThan(500).actual();    // 513 as of 2025-04-29
        assertThat(response.getPageNumber()).isEqualTo(1);
        assertThat(response.getTotalPages()).isEqualTo(totalCount / pageSize + 1);  // assuming a partial page
        assertThat(response.getData())
                .hasSize(100)
                .contains(BLANK_SHEET_TEMPLATE);

        // get next page
        response = api.templatesListPublic(accessApiLevel, includeAll, level, 2, pageSize);
        assertThat(response).isNotNull();
        assertThat(response.getTotalCount()).isEqualTo(totalCount);    // should be same as page 1
        assertThat(response.getPageNumber()).isEqualTo(2);
        assertThat(response.getData())
                .hasSize(100)
                .doesNotContain(BLANK_SHEET_TEMPLATE)
                .contains(DEPARTMENT_FORECAST_TEMPLATE);
    }

    // improves the quality of the test but makes it more fragile to changes in the Smartsheet API response/data
    static final Template BLANK_SHEET_TEMPLATE = Template.builder()
            .id(7881304550205316L)
            .type(Template.TypeEnum.SHEET)
            .accessLevel(null)
            .blank(true)
            .categories(List.of("Featured Templates"))
            .description("Create and customize a new sheet")
            .globalTemplate(Template.GlobalTemplateEnum.BLANK_SHEET)
            .image(null)
            .largeImage(null)
            .locale(Template.LocaleEnum.EN_US)
            .name("Blank Sheet")
            .tags(List.of())
            .build();
    static final Template DEPARTMENT_FORECAST_TEMPLATE = Template.builder()
            .id(2318345887344516L)
            .type(Template.TypeEnum.SHEET)
            .accessLevel(AccessLevel.EDITOR)
            .categories(List.of())
            .locale(Template.LocaleEnum.EN_US)
            .name("Department Forecast")
            .tags(List.of())
            .build();
}
