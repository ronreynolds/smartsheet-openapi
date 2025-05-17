package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.CellImagesApi;
import com.ronreynolds.smartsheet.model.AddImageToCell200Response;
import com.ronreynolds.smartsheet.model.ImageUrl;
import com.ronreynolds.smartsheet.model.ListImageUrls200Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for CellImagesApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CellImagesApiTest {

    private final CellImagesApi api = new CellImagesApi();


    /**
     * Add Image to Cell
     * <p>
     * Uploads an image to the specified cell within a sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need to add test image")
    public void addImageToCellTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long rowId = TestData.RowData.id;
        Long columnId = null;
        String contentType = null;  // content type of the request
        String contentDisposition = null;
        Integer contentLength = null;
        String altText = null;
        Boolean overrideValidation = null;
        File body = null;
        AddImageToCell200Response response = api.addImageToCell(
                sheetId, rowId, columnId, contentType, contentDisposition, contentLength, altText, overrideValidation, body);
        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations

    }

    /**
     * List Image URLs
     * <p>
     * Posts an array of Image Url objects that can be used to retrieve the specified cell images.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listImageUrlsTest() throws ApiException {
        // you get a 500 if this is null
        List<ImageUrl> imageUrl = List.of(
                ImageUrl.builder().imageId(TestData.ImageData.id1).build(),
                ImageUrl.builder().imageId(TestData.ImageData.id2).build());
        ListImageUrls200Response response = api.listImageUrls(imageUrl);

        log.info("{}", response);

        assertThat(response).isNotNull()
                .satisfies(val -> assertThat(val.getUrlExpiresInMillis()).isEqualTo(1_800_000));  // default value
        assertThat(response.getImageUrls()).isNotEmpty()
                .anySatisfy(TestData.ImageData::assertImageUrl);
    }
}
