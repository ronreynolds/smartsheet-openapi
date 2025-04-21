## Integration Tests
this project contains generated integration tests for all generated domain-specific `Api` classes.  the purpose of this doc is to
track the progress of their implementations.  all such tests live in `src/test/java/.../it/` and most of them are currently disabled.
the package also contains a `TestData` class in an attempt to consolidate constants used to query or validate the API.

### AlternateEmailAddressApiTest 
* In-Progress
    * `addAlternateEmailTest`
      * failed with a 500; oops!
    * `deleteAlternateEmailTest`
      * need to add alternate email to delete
    * `promoteAlternateEmailTest`
      * need to add alternate email to test with
* Blocked
  * unable to create new alternate-email so makes it difficult to continue testing
    * `addAlternateEmail call failed with: 500 - { "errorCode" : 4000, "message" : "An unexpected error has occurred. Please contact the Support team at https://help.smartsheet.com/contact for assistance.", "refId" : "8ocvwq"}`
* Done
  * `listAlternateEmailsTest`
  * `getAlternateEmailTest`

### AttachmentsApiTest
* To-Do
  * `attachmentsAttachToCommentTest`
  * `attachmentsAttachToSheetTest`
  * `attachmentsDeleteTest`
  * `attachmentsGetTest`
  * `attachmentsListOnRowTest`
  * `attachmentsListOnSheetTest`
  * `attachmentsVersionListTest`
  * `attachmentsVersionUploadTest`
  * `attachmentsVersionsDeleteTest`
  * `discussionListAttachmentsTest`
  * `rowAttachmentsAttachFileTest`
* In-Progress
* Blocked
* Done

### AutomationRulesApiTest
* To-Do
* In-Progress
    * `automationruleDeleteTest`
      * need to add ID; unfortunately can't create automation-rules via API so will have to create via Web-UI
    * `automationruleGetTest`
      * failing with 404; not sure why (possibly replication delay?)
    * `automationruleUpdateTest`
      * failing with 404; also possibly replication delay
    * `automationrulesListTest`
      * needs more validation logic but response is parsing but is currently empty (even tho rule created via Web-UI)
* Blocked
  * possibly replication-delay of newly-created rule to be visible via API
* Done

### CellImagesApiTest
* To-Do
  * `addImageToCellTest`
  * `listImageUrlsTest`
* In-Progress
* Blocked
* Done

### CellsApiTest
* In-Progress
  * `cellHistoryGetTest`
    * works; just needs more validation
* Blocked
* Done

### ColumnsApiTest
* To-Do
  * `columnDeleteTest`
  * `columnGetTest`
  * `columnUpdateColumnTest`
  * `columnsAddToSheetTest`
  * `columnsListOnSheetTest`
* In-Progress
* Blocked
* Done

### CommentsApiTest
* To-Do
  * `commentDeleteTest`
  * `commentEditTest`
  * `commentGetTest`
  * `commentsCreateTest`
* In-Progress
* Blocked
* Done

### ContactsApiTest
* To-Do
  * `getContactTest`
  * `listContactsTest`
* In-Progress
* Blocked
* Done

### CrossSheetReferencesApiTest
* To-Do
  * `addCrosssheetReferenceTest`
  * `getCrosssheetReferenceTest`
  * `listCrosssheetReferencesTest`
* In-Progress
* Blocked
* Done

### DashboardsApiTest
* To-Do
  * `copySightTest`
  * `deleteSightTest`
  * `deleteSightShareTest`
  * `getSightTest`
  * `getSightPublishStatusTest`
  * `listSightSharesTest`
  * `listSightsTest`
  * `moveSightTest`
  * `setSightPublishStatusTest`
  * `shareSightTest`
  * `shareSightGetTest`
  * `updateSightTest`
  * `updateSightShareTest`
* In-Progress
* Blocked
* Done

### DiscussionsApiTest
* To-Do
  * `discussionDeleteTest`
  * `discussionGetTest`
  * `discussionsCreateTest`
  * `discussionsListTest`
  * `rowDiscussionsCreateTest`
  * `rowDiscussionsListTest`
* In-Progress
* Blocked
* Done

### EventsApiTest :partying_face:
* Done
  * `listEventsTest`
    * however it fails (expectedly) because our account plan doesn't support this operation :shrug:

### FavoritesApiTest
* To-Do
  * `addFavoriteTest`
  * `deleteFavoritesByTypeTest`
  * `deleteFavoritesByTypeAndIdTest`
  * `getFavoritesTest`
  * `isFavoriteTest`
* In-Progress
* Blocked
* Done

### FoldersApiTest
* To-Do
  * `copyFolderTest`
  * `createFolderFolderTest`
  * `deleteFolderTest`
  * `getFolderTest`
  * `listFoldersTest`
  * `moveFolderTest`
  * `updateFolderTest`
* In-Progress
* Blocked
* Done

### GroupMembersApiTest
* To-Do
  * `addGroupMembersTest`
  * `deleteGroupMembersTest`
* In-Progress
* Blocked
* Done

### GroupsApiTest
* To-Do
  * `addGroupTest`
  * `deleteGroupTest`
  * `getGroupTest`
  * `listGroupsTest`
  * `updateGroupTest`
* In-Progress
* Blocked
* Done

### HomeApiTest
* To-Do
  * `createHomeFolderTest`
  * `homeListFoldersTest`
  * `listHomeContentsTest`
* In-Progress
* Blocked
* Done

### ImportApiTest
* To-Do
  * `importSheetIntoFolderTest`
  * `importSheetIntoSheetsFolderTest`
  * `importSheetIntoWorkspaceTest`
* In-Progress
* Blocked
* Done

### ProofsApiTest
* To-Do
  * `proofsAttachToProofTest`
  * `proofsCreateTest`
  * `proofsCreateDiscussionTest`
  * `proofsCreateProofRequestsTest`
  * `proofsCreateVersionTest`
  * `proofsDeleteTest`
  * `proofsDeleteProofRequestsTest`
  * `proofsDeleteVersionTest`
  * `proofsGetTest`
  * `proofsGetAllProofsTest`
  * `proofsGetVersionsTest`
  * `proofsListAttachmentsTest`
  * `proofsListDiscussionsTest`
  * `proofsListRequestActionsTest`
  * `proofsUpdateTest`
* In-Progress
* Blocked
* Done

### ReportsApiTest
* To-Do
  * `deleteReportShareTest`
  * `getReportTest`
  * `getReportPublishTest`
  * `getReportsTest`
  * `listReportSharesTest`
  * `sendReportViaEmailTest`
  * `setReportPublishTest`
  * `shareReportTest`
  * `shareReportGetTest`
  * `updateReportShareTest`
* In-Progress
* Blocked
* Done

### RowsApiTest
* To-Do
  * `copyRowsTest`
  * `deleteRowsTest`
  * `moveRowsTest`
  * `rowGetTest`
  * `rowsAddToSheetTest`
  * `rowsSendTest`
  * `rowsSortTest`
  * `updateRowsTest`
* In-Progress
* Blocked
* Done

### SearchApiTest
* In-Progress
    * `listSearchTest`
    * `listSearchSheetTest`
      * responses parsing; just need to add more validation logic
* Blocked
* Done

### ServerInfoApiTest :partying_face:
* Done
  * `serverinfoGetTest`

### SharingApiTest
* To-Do
  * `deleteReportShareTest`
  * `deleteSheetShareTest`
  * `deleteSightShareTest`
  * `deleteWorkspaceShareTest`
  * `listReportSharesTest`
  * `listSheetSharesTest`
  * `listSightSharesTest`
  * `listWorkspaceSharesTest`
  * `shareReportTest`
  * `shareReportGetTest`
  * `shareSheetTest`
  * `shareSheetGetTest`
  * `shareSightTest`
  * `shareSightGetTest`
  * `shareWorkspaceTest`
  * `shareWorkspaceGetTest`
  * `updateReportShareTest`
  * `updateSheetShareTest`
  * `updateSightShareTest`
  * `updateWorkspaceShareTest`
* In-Progress
* Blocked
* Done

### SheetsApiTest
* To-Do
  * `copySheetTest`
  * `createSheetInFolderTest`
  * `createSheetInSheetsFolderTest`
  * `createSheetInWorkspaceTest`
  * `deleteSheetTest`
  * `deleteSheetShareTest`
  * `getSheetPublishTest`
  * `getSheetVersionTest`
  * `listOrgSheetsTest`
  * `listSheetSharesTest`
  * `listSheetsTest`
  * `moveSheetTest`
  * `setSheetPublishTest`
  * `shareSheetTest`
  * `shareSheetGetTest`
  * `sheetSendTest`
  * `updateSheetTest`
  * `updateSheetShareTest`
* In-Progress
    * `getSheetTest`
      * works; just haven't added much validation logic yet
* Blocked
* Done

### SheetSummaryApiTest
* To-Do
  * `addImageSummaryFieldTest`
  * `addSummaryFieldsTest`
  * `deleteSummaryFieldsTest`
  * `listSummaryFieldsTest`
  * `listSummaryFieldsPaginatedTest`
  * `updateSummaryFieldsTest`
* In-Progress
* Blocked
* Done

### TemplatesApiTest
* To-Do
  * `templatesListTest`
  * `templatesListPublicTest`
* In-Progress
* Blocked
* Done

### TokenApiTest
* To-Do
  * `tokensDeleteTest`
  * `tokensGetOrRefreshTest`
* In-Progress
* Blocked
* Done

### UpdateRequestsApiTest
* To-Do
  * `sentupdaterequestDeleteTest`
  * `sentupdaterequestGetTest`
  * `sentupdaterequestsListTest`
  * `updaterequestsCreateTest`
  * `updaterequestsDeleteTest`
  * `updaterequestsGetTest`
  * `updaterequestsListTest`
  * `updaterequestsUpdateTest`
* In-Progress
* Blocked
* Done

### UsersApiTest
* To-Do
  * `addUserTest`
  * `deactivateUserTest`
  * `listUsersTest`
  * `reactivateUserTest`
  * `removeUserTest`
  * `updateUserTest`
  * `updateUserProfileImageTest`
* In-Progress
* Blocked
* Done
  * `getCurrentUserTest`
  * `getUserTest`

### WebhooksApiTest
* To-Do
  * `createWebhookTest`
  * `deleteWebhookTest`
  * `getWebhookTest`
  * `listWebhooksTest`
  * `resetSharedSecretTest`
  * `updateWebhookTest`
* In-Progress
* Blocked
* Done

### WorkspacesApiTest
* To-Do
  * `copyWorkspaceTest`
  * `createWorkspaceTest`
  * `createWorkspaceFolderTest`
  * `deleteWorkspaceTest`
  * `deleteWorkspaceShareTest`
  * `getWorkspaceTest`
  * `getWorkspaceFoldersTest`
  * `listWorkspaceSharesTest`
  * `listWorkspacesTest`
  * `shareWorkspaceTest`
  * `shareWorkspaceGetTest`
  * `updateWorkspaceTest`
  * `updateWorkspaceShareTest`
* In-Progress
* Blocked
* Done
