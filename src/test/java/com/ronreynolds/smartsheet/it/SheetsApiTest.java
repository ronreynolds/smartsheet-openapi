package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.SheetsApi;
import com.ronreynolds.smartsheet.api.util.Constants;
import com.ronreynolds.smartsheet.model.CompatibilityLevel;
import com.ronreynolds.smartsheet.model.ContainerDestinationForCopy;
import com.ronreynolds.smartsheet.model.ContainerDestinationForMove;
import com.ronreynolds.smartsheet.model.CopySheet200Response;
import com.ronreynolds.smartsheet.model.CreateSheetInFolderRequest;
import com.ronreynolds.smartsheet.model.GenericResult;
import com.ronreynolds.smartsheet.model.ListOrgSheets200Response;
import com.ronreynolds.smartsheet.model.PaperSize;
import com.ronreynolds.smartsheet.model.Recipient;
import com.ronreynolds.smartsheet.model.RecipientIndividual;
import com.ronreynolds.smartsheet.model.Result;
import com.ronreynolds.smartsheet.model.SetSheetPublish200Response;
import com.ronreynolds.smartsheet.model.Share;
import com.ronreynolds.smartsheet.model.SharingInclude;
import com.ronreynolds.smartsheet.model.Sheet;
import com.ronreynolds.smartsheet.model.SheetCopyExclude;
import com.ronreynolds.smartsheet.model.SheetCopyInclude;
import com.ronreynolds.smartsheet.model.SheetEmail;
import com.ronreynolds.smartsheet.model.SheetEmailFormat;
import com.ronreynolds.smartsheet.model.SheetExclude;
import com.ronreynolds.smartsheet.model.SheetInclude;
import com.ronreynolds.smartsheet.model.SheetPublish;
import com.ronreynolds.smartsheet.model.SheetPublishRequest;
import com.ronreynolds.smartsheet.model.SheetTemplateInclude;
import com.ronreynolds.smartsheet.model.SheetVersion;
import com.ronreynolds.smartsheet.model.SourceInclude;
import com.ronreynolds.smartsheet.model.UpdateReportShare200Response;
import com.ronreynolds.smartsheet.model.UpdateReportShareRequest;
import com.ronreynolds.smartsheet.model.UpdateSheet;
import com.ronreynolds.smartsheet.model.UpdateSheet200Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for SheetsApi
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)   // enable processing of the @Order annotation to specify test ordering
public class SheetsApiTest {
    private final SheetsApi api = new SheetsApi();

    /**
     * Copy Sheet
     * <p>
     * Creates a copy of the specified sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void copySheetTest() throws ApiException {
        ContainerDestinationForCopy containerDestination = null;
        List<SheetCopyInclude> include = Constants.allOf(SheetCopyInclude.class);
        SheetCopyExclude exclude = null;
        var response = api.copySheet(TestData.SheetData.id, containerDestination, Constants.noContentType, include, exclude);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Create Sheet in Folder
     * <p>
     * Creates a sheet from scratch or from the specified template in the specified folder.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void createSheetInFolderTest() throws ApiException {
        Long folderId = null;
        CreateSheetInFolderRequest createSheetInFolderRequest = null;
        List<SheetTemplateInclude> include = null;
        var response = api.createSheetInFolder(folderId, createSheetInFolderRequest, Constants.noContentType, include);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Create Sheet in \&quot;Sheets\&quot; Folder
     * <p>
     * Creates a sheet from scratch or from the specified template in the user&#39;s Sheets folder (Home). For subfolders, use
     * Create Sheet in Folder.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void createSheetInSheetsFolderTest() throws ApiException {
        CreateSheetInFolderRequest createSheetInFolderRequest = null;
        Integer accessApiLevel = null;
        List<SheetTemplateInclude> include = null;
        var response = api.createSheetInSheetsFolder(createSheetInFolderRequest, accessApiLevel, Constants.noContentType,
                include);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Create Sheet in Workspace
     * <p>
     * Creates a sheet from scratch or from the specified template at the top-level of the specified workspace. For subfolders,
     * use Create Sheet in Folder.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void createSheetInWorkspaceTest() throws ApiException {
        Long workspaceId = null;
        CreateSheetInFolderRequest createSheetInFolderRequest = null;
        Integer accessApiLevel = null;
        List<SheetTemplateInclude> include = null;
        var response = api.createSheetInWorkspace(workspaceId, createSheetInFolderRequest, accessApiLevel,
                Constants.noContentType, include);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Delete Sheet
     * <p>
     * Deletes the sheet specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void deleteSheetTest() throws ApiException {
        Sheet sheet = TestData.SheetData.createTestSheetCopy();
        assertThat(sheet).isNotNull();
//        sheet = api.createSheetInFolder(TestData.FolderData.id, ) - need to work out method args
        Long sheetId = sheet.getId();
        var response = api.deleteSheet(sheetId);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Delete Sheet Share
     * <p>
     * Deletes the share specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void deleteSheetShareTest() throws ApiException {
        String shareId = null;
        Integer accessApiLevel = null;
        Result response = api.deleteSheetShare(TestData.SheetData.id, shareId, accessApiLevel);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Get Sheet
     * <p>
     * Gets a sheet in the format specified, based on the sheet Id.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getSheetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        String accept = null;
        Integer accessApiLevel = null;
        List<SheetInclude> include = Constants.allSheetIncludes;
        List<SheetExclude> exclude = null;
        List<Long> columnIds = null;
        String filterId = null;
        Integer ifVersionAfter = null;
        CompatibilityLevel level = null;
        Integer pageSize = null;
        Integer page = null;
        PaperSize paperSize = null;
        List<Long> rowIds = null;
        List<Integer> rowNumbers = null;
        OffsetDateTime rowsModifiedSince = null;
        var response =
                api.getSheet(sheetId, accept, accessApiLevel, include, exclude, columnIds, filterId, ifVersionAfter, level,
                        pageSize, page, paperSize, rowIds, rowNumbers, rowsModifiedSince);

        log.info("{}", response);
        assertThat(response).isNotNull();
        assertThat(response.getSheet()).isNotNull().satisfies(TestData.SheetData::assertEquals);
    }

    /**
     * Get Sheet Publish Status
     * <p>
     * Gets the sheet&#39;s &#39;Publish&#39; settings.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getSheetPublishTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        SheetPublish response = api.getSheetPublish(sheetId);

        log.info("{}", response);
        assertThat(response).isNotNull().satisfies(TestData.SheetData::assertNotPublished);

        // TODO - publish a sheet and validate results
    }

    /**
     * Get Sheet Version
     * <p>
     * Gets the sheet version without loading the entire sheet. The following actions increment sheet version: * add/modify
     * cell value * add/modify discussion/comment * add/modify row * add/remove/update version attachment * cell updated via
     * cell link * change formatting
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getSheetVersionTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        SheetVersion response = api.getSheetVersion(sheetId);

        log.info("{}", response);
        assertThat(response).isNotNull();
        assertThat(response.getVersion()).isGreaterThanOrEqualTo(9);    // as of 2025-05-06
    }

    /**
     * List Org Sheets
     * <p>
     * Gets a summarized list of all sheets owned by the members of the organization account.  * **_This operation is only
     * available to system administrators_**  * **_You may use the query string parameter numericDates with a value of true to
     * enable strict parsing of dates in numeric format. See [Dates and Times](#section/API-Basics/Dates-and-Times) for more
     * information._**
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listOrgSheetsTest() throws ApiException {
        OffsetDateTime modifiedSince = null;
        ListOrgSheets200Response response = api.listOrgSheets(modifiedSince);

        log.info("{}", response);

        assertThat(response).isNotNull();
        assertThat(response.getPageNumber()).isOne();
        assertThat(response.getPageSize()).isEqualTo(100);  // default page size (could change)
        assertThat(response.getTotalCount()).isEqualTo(-1); // weird
        assertThat(response.getTotalPages()).isEqualTo(-1); // weird
        assertThat(response.getData())
                .hasSizeGreaterThan(10)
                .anySatisfy(datum -> {
                    assertThat(datum.getName()).isEqualTo(TestData.SheetData.name);
                    assertThat(datum.getId()).isEqualTo(TestData.SheetData.id);
                });
    }

    /**
     * List Sheet Shares
     * <p>
     * Gets a list of all users and groups to whom the specified Sheet is shared, and their access level. This operation
     * supports query string parameters for pagination of results. For more information, see Paging Query String Parameters.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listSheetSharesTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Integer accessApiLevel = null;
        SharingInclude sharingInclude = null;
        Boolean includeAll = null;
        Integer page = null;
        Integer pageSize = null;
        var response = api.listSheetShares(sheetId, accessApiLevel, sharingInclude, includeAll, page, pageSize);
//        log.info("{}", response);
        assertThat(response).satisfies(TestData::pagedResultHasData);
        assertThat(response.getData()).anySatisfy(TestData.SheetData::assertShare);
    }

    /**
     * List Sheets
     * <p>
     * Gets a list of all sheets that the user has access to. The list contains an abbreviated Sheet object for each sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listSheetsTest() throws ApiException {
        Integer accessApiLevel = null;
        List<SourceInclude> include = List.of(SourceInclude.values());
        Boolean includeAll = true;
        OffsetDateTime modifiedSince = null;
        Boolean numericDates = null;
        Integer page = null;
        Integer pageSize = null;
        var response = api.listSheets(accessApiLevel, include, includeAll, modifiedSince, numericDates, page, pageSize);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).satisfies(TestData::pagedResultHasData);
        assertThat(response.getData()).anySatisfy(TestData.SheetData::assertListingEquals);
    }

    /**
     * Move Sheet
     * <p>
     * Moves the specified sheet to a new location. When a sheet that is shared to one or more users and/or groups is moved
     * into or out of a workspace, those sheet-level shares are preserved.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void moveSheetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        ContainerDestinationForMove moveSheetRequest = ContainerDestinationForMove.builder()
                // TODO
//                .destinationId()
//                .destinationType()
                .build();
        CopySheet200Response response = api.moveSheet(sheetId, moveSheetRequest, Constants.noContentType);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Set Sheet Publish Status
     * <p>
     * Sets the publish status of the sheet and returns the new status, including the URLs of any enabled publishings.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void setSheetPublishTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        SheetPublishRequest sheetPublishSettings = SheetPublishRequest.builder()
//                .icalEnabled()
//                .readOnlyFullAccessibleBy()
//                .readOnlyFullDefaultView()
//                .readOnlyFullEnabled()
//                .readOnlyFullShowToolbar()
//                .readOnlyLiteEnabled()
//                .readWriteAccessibleBy()
//                .readWriteDefaultView()
//                .readWriteEnabled()
//                .readWriteAccessibleBy()
                .build();
        SetSheetPublish200Response response = api.setSheetPublish(sheetId, Constants.noContentType, sheetPublishSettings);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Share Sheet
     * <p>
     * Shares a sheet with the specified users and groups.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void shareSheetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Integer accessApiLevel = null;
        Boolean sendEmail = false;
        List<Share> share = List.of(Share.builder().build());
        var response = api.shareSheet(sheetId, accessApiLevel, sendEmail, share);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Get Sheet Share.
     * <p>
     * Gets the share specified in the URL.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void shareSheetGetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        String shareId = TestData.ShareData.shareId;
        Integer accessApiLevel = null; // ?
        Share response = api.shareSheetGet(sheetId, shareId, accessApiLevel);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData.SheetData::assertShare);
    }

    /**
     * Send Sheet via Email
     * <p>
     * Sends the sheet as a PDF attachment via email to the designated recipients.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("totally works - just don't want the spam :)")
    public void sheetSendTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        List<Recipient> sendTo = Stream.of("user@example.com")
                // EmailOrGroupId is a one-of around 2 types so we create the proper value type then wrap it in the one-of
                .map(email -> new Recipient(RecipientIndividual.builder().email(email).build()))
                .collect(Collectors.toList());
        SheetEmail sheetEmail = SheetEmail.builder()
                .sendTo(sendTo)
                .format(SheetEmailFormat.PDF)
                .message("yo!  here's some sheet!")
                .ccMe(true)
                .build();
        GenericResult response = api.sheetSend(sheetId, Constants.noContentType, sheetEmail);

//        log.info("{}", response);
        assertThat(response).satisfies(TestData::successfulResult);
    }

    /**
     * Update Sheet
     * <p>
     * Updates the sheet specified in the URL. To modify sheet contents, see [Add Rows](../.
     * ./tag/rows#operation/rows-addToSheet), [Update Rows](../../tag/rows#operation/update-rows), [Add Columns](../.
     * ./tag/columns#operation/columns-addToSheet), and [Update Column](../../tag/columns#operation/column-updateColumn). This
     * operation can be used to update an individual user&#39;s sheet settings. If the request body contains only the
     * **userSettings** attribute, this operation may be performed even if the user only has read-only access to the sheet (for
     * example, the user has viewer permissions or the sheet is read-only).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void updateSheetTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        Integer accessApiLevel = null;
        UpdateSheet request = null;
        UpdateSheet200Response response = api.updateSheet(sheetId, accessApiLevel, request);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }

    /**
     * Update Sheet Share.
     * <p>
     * Updates the access level of a user or group for the specified sheet.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    @Disabled("need test data")
    public void updateSheetShareTest() throws ApiException {
        Long sheetId = TestData.SheetData.id;
        String shareId = null;
        Integer accessApiLevel = null;
        UpdateReportShareRequest updateReportShareRequest = null;
        UpdateReportShare200Response response =
                api.updateSheetShare(sheetId, shareId, accessApiLevel, updateReportShareRequest);

        log.info("{}", response);
        // TODO: test validations
        assertThat(response).isNotNull();
    }
}
