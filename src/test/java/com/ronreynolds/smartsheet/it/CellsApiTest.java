package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.CellsApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.CellHistoryInclude;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for CellsApi
 */
public class CellsApiTest {
    private final CellsApi api = new CellsApi();

    /**
     * List Cell History
     * <p>
     * Gets the cell modification history.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void cellHistoryGetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Long rowId = TestData.RowData.id;
        Long columnId = TestData.ColumnData.ColumnBriefData.PRIMARY.id;
        List<CellHistoryInclude> include = Constants.allOf(CellHistoryInclude.class);
        var response = api.cellHistoryGet(sheetId, rowId, columnId, include,
                Constants.noPageSize, Constants.allPages, Constants.defaultLevel);

        assertThat(response).satisfies(TestData::pagedResultHasData);
        assertThat(response.getData()).isNotEmpty().anySatisfy(TestData.ColumnData::assertColumnHistory);
    }
}
