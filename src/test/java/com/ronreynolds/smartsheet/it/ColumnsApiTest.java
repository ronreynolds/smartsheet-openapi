package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.ColumnsApi;
import com.ronreynolds.smartsheet.model.ColumnBrief;
import com.ronreynolds.smartsheet.model.ColumnObject;
import com.ronreynolds.smartsheet.model.ColumnUpdateColumn200Response;
import com.ronreynolds.smartsheet.model.ColumnsAddToSheet200Response;
import com.ronreynolds.smartsheet.model.ColumnsListOnSheet200Response;
import com.ronreynolds.smartsheet.model.CompatibilityLevel;
import com.ronreynolds.smartsheet.model.GenericResult;
import com.ronreynolds.smartsheet.model.ResultPrefix;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for ColumnsApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ColumnsApiTest {
    private final ColumnsApi api = new ColumnsApi();


    /**
     * Delete Column
     * <p>
     * Deletes the column specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void columnDeleteTest() throws ApiException {
        Long sheetId = null;
        Long columnId = null;
        GenericResult response = api.columnDelete(sheetId, columnId);
        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Get Column
     * <p>
     * Gets definitions for the column specified in the URL. **NOTE:** If you need to see the values of individual cells within
     * the column, use [Get Sheet](#operation/getSheet) or [Get Row](#operation/row-get).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void columnGetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long columnId = TestData.ColumnData.ColumnBriefData.COLUMN6.id;
        CompatibilityLevel level = null;
        ColumnBrief response = api.columnGet(sheetId, columnId, level);

        log.info("{}", response);
        assertThat(response).satisfies(TestData.ColumnData.ColumnBriefData.COLUMN6::assertMatches);
    }

    /**
     * Update Column
     * <p>
     * Updates properties of the column, moves the column, or renames the column.  **NOTE:** * You cannot change the type of a
     * Primary column. * While dependencies are enabled on a sheet, you can&#39;t change the type of any special calendar/Gantt
     * columns. * If the column type is changed, all cells in the column are converted to the new column type and column
     * validation is cleared. * Type is optional when moving or renaming, but required when changing type or dropdown values.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void columnUpdateColumnTest() throws ApiException {
        Long sheetId = null;
        Long columnId = null;
        ColumnObject columnObject = null;
        ColumnUpdateColumn200Response response = api.columnUpdateColumn(sheetId, columnId, columnObject);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * Add Columns
     * <p>
     * Inserts one or more columns into the sheet specified in the URL.This operation can be performed using a [simple upload](
     * ../../tag/attachmentsDescription#section/Post-an-Attachment/Simple-Uploads) or a [multipart upload](../.
     * ./tag/attachmentsDescription#section/Post-an-Attachment/Multipart-Uploads). For more information, see [Post an
     * Attachment](../../tag/attachmentsDescription#section/Post-an-Attachment).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void columnsAddToSheetTest() throws ApiException {
        Long sheetId = null;
        ColumnObject columnObject = null;
        ColumnsAddToSheet200Response response = api.columnsAddToSheet(sheetId, columnObject);

        log.info("{}", response);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * List Columns
     * <p>
     * Gets a list of all columns belonging to the sheet specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void columnsListOnSheetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        CompatibilityLevel level = null;
        Integer page = null;
        Integer pageSize = null;
        Boolean includeAll = true;
        ColumnsListOnSheet200Response response = api.columnsListOnSheet(sheetId, level, page, pageSize, includeAll);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData::pagedResultHasDataNullPageSize);
        assertThat(response.getData()).isNotEmpty().anySatisfy(TestData.ColumnData::assertColumnBriefs);
    }
}
