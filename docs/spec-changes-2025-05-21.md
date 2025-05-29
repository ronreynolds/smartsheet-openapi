# Smartsheet OpenAPI Spec Changes based on 2025-05-21 YAML version 
the purpose of this new spec-changes doc is to track all changes based on the 2025-05-21 Smartsheet OpenAPI YAML version.
as we have drifted further from the current spec (which has undergone many changes) it makes sense to reset and start over (tho
hopefully not having to do all the previous changes again; i am still hopeful) ;-)

## Fixes
### Parsing
* none! :partying_face:

### Compilation
* issues with compiling OpenAPI one-of code; this has been observed before when one-ofs contain collections rather than scalars
  * `ShareReportRequest`
    * added `ShareArray` for all uses of `type:array item:Share`
    * replaced `oneOf('Share', 'ShareArray')` with just `ShareArray`
      * the difference between a single item and a single-element array is miniscule as far as bandwidth and likely follows the 
        exact same server code-path
      * note, this DOES require a tweak to Jackson `ObjectMapper` to handle singles as an array of 1
        * `enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)`
    * changed paths:
      * `/reports/{reportId}/shares POST` request and response
      * `/reports/{reportId}/shares GET` response
      * `/sheets/{sheetId}/shares POST` request and response
      * `/sheets/{sheetId}/shares GET` response
      * `/sights/{sightId}/shares POST` response
      * `/workspaces/{workspaceId}/shares POST` request and response
      * `/workspaces/{workspaceId}/shares GET` response
  * `AddGroupMembersRequest`, `RowsAddToSheetRequest`
    * added `GroupMemberArray` to replace arrays of `GroupMember`
    * replaced `oneOf(Group, GroupMemberArray)` with just `GroupMemberArray`
    * changed paths:
      * `/groups/{groupId} GET` response
      * `/groups/{groupId}/members POST` request and response
  * `UpdateRowsRequest`
    * added `RowArray` and replaced `oneOf(Row,RowArray)` with just `RowArray` 
    * changed paths:
      * `/sheets/{sheetId}/rows PUT` request
      * `/sheets/{sheetId}/rows POST` request
  * `AddFavoriteRequest`
    * added `FavoriteArray` and replaced `oneOf(Favorite,FavoriteArray)` with just `FavoriteArray`
    * changed paths:
      * `/favorites POST` request and response
  * `AddAlternateEmailRequest`
    * added `AddAlternateEmailArray` and replaced `oneOf(AddAlternateEmail,AddAlternateEmailArray)` with `AddAlternateEmailArray` 
    * changed paths:
      * `/users/{userId}/alternateemails POST` request
* openapi-generated code compiles at this point :partying_face:

### Functional
#### ID type fixes
* added `#/components/schemas/Int64` to simplify referencing `long` type
* changed all numeric IDs that are actually 'long' to `$ref: '#/components/schemas/Int64'`
  * `type:number` maps to `BigDecimal` in Java; almost nothing uses `BigDecimal` in the API except the generic Cell.value (AFAIK)
* used `allOf:- $ref: '#/components/schemas/Int64'` for those fields that have other properties since `$ref` replaces ALL siblings
* `contactId` is `string` not `number`
* `attachmentId`, `automationRuleId`, `commentId`, `crossSheetReferenceId`, `discussionId`, `proofId`, `sightId`, 
  `updateRequestId`, `webhookId`, and `workspaceId` params `in:path` are all longs, not strings

#### other type fixes
* `version` is `integer`, not `number`

##### result/data response field mismatch (GET returns 'data'; PUT/POST returns 'result')
* `result` response field renamed to `data`
  * `/folders/{folderId}/folders GET`
  * `/home/folders GET`
  * `/workspaces/{workspaceId}/shares GET`
  * `/reports/{reportId}/shares GET`
  * `/sheets/{sheetId}/shares GET`
  * `/sights/{sightId}/shares GET`
* `data` response field renamed to `result`
  * `/workspaces POST`
  * `/users/{userId} PUT`

## Changes
### Extracting types to avoid excessive inner-classes/enums
any type defined within request or response content or larger schema object is generated as an inner-type and thus not shared 
across equivalent types, which makes using the generated API less pleasant.  to address that i move inner-types to top-level
schema types to maximize reuse across equivalent sub-models.

note, since OpenAPI v3.0.3 doesn't have a `const` keyword some fields use single-value `enum` to get the same approximate result.
no point, IMHO, to migrate those enums to top-level types. OpenAPI v3.1.0 does have a `const` keyword to make this practice 
obsolete (but we're not there yet).  

* enums
  * `AttachmentParentType`
    * used by `Attachment.parentType`
  * `AttachmentSubType`
    * used by `Attachment.attachmentSubType`. `URLAttachmentRequest.attachmentSubType`
  * `AttachmentTypeWithSmartsheet`
    * used by `ShortcutDataItem.attachmentType`
  * `AttachmentTypeWithTrello`
    * used by `Attachment.attachmentType`, `URLAttachmentRequest.attachmentType`
  * `AutomationActionFrequency`
    * used by `AutomationAction.frequency`
  * `AutomationActionType`
    * used by `AutomationAction.type`
  * `AutomationRuleDisabledReason`
    * used by `AutomationRule.disabledReason`
  * `CallbackEventType`
    * used by `CallbackEvent.eventType`
  * `CellLinkStatus`
    * used by `CellLink.status`
  * `CellLinkWidgetContentType`
    * used by `CellLinkWidgetContent.type`
  * `ColumnTag`
    * used by `Column.tags`
  * `ColumnType`
    * used by `Column.type`, `SummaryField.type`, `SummaryFieldAddImage.type`, `GetRowObject.properties-type`,
    `SummaryFieldUpdateRequest.type`, `UpdateColumn.type`, `AddColumns.type`, `ColumnObjectAttributes.type`, 
    `ContainerDestinationForCopy.type`, `GetColumn.type`, `ChartColumnInfo.type` 
  * `ColumnObjectVersion`
    * used by `ColumnObject.version`
  * `ColumnVersion`
    * used by `Column.version`
  * `ContainerDestinationType`
    * used by `ContainerDestinationForMove.destinationType`, `ContainerDestinationForCopy.destinationType`
  * `CriterionOperator`
    * used by `Criteria.operator` ("Criteria" is the plural of "Criterion")
  * `CrossSheetReferenceStatus`
    * used by `CrossSheetReference.status`
  * `DataLabelType`
    * used by `WidgetChartDataLabel.labelType`
  * `DataSource`
    * used by `CellDataItem.dataSource`
  * `DayDescriptor`
    * used by `Schedule.dayDescriptors`
  * `DayOrdinal`
    * used by `Schedule.dayOrdinal`
  * `DiscussionParentType`
    * used by `Discussion.parentType`
  * `EventObjectType`
    * used by `Event.objectType`
  * `EventSource`
    * used by `Event.source`
  * `FavoriteType`
    * used by `Favorite.type`
  * `FilterType`
    * used by `Filters.filterType`
  * `FontFamilyTrait`
    * used by `FontFamily.traits`
  * `ImageWidgetContentFit`
    * used by `ImageWidgetContent.fit`
  * `ImageWidgetContentMargin`
    * used by `ImageWidgetContent.margin`
  * `LineType`
    * used by `Series.lineType`
  * `Location`
      * used by `Axis.location`, `Legend.location`, `Series.axisLocationX`, `Series.axisLocationY`
  * `ObjectType`
    * used by `CallbackEvent.objectType`
  * `PaperSize`
    * used by `FormatDetails.paperSize`, `parameters/paperSize`
  * `PredecessorType`
    * used by `Predecessor.type`
  * `ProofType`
    * used by `Proof.type`
  * `ProofRequestActionStatus`
    * used by `ProofRequestAction.actionStatus`
  * `ProofRequestStatus`
    * used by `ProofRequest.status`
  * `QueryOperator`
    * used by `Query.operator`
  * `ResourceManagementType`
    * used by `Sheet.resourceManagementType`
  * `ResultCode`
    * used by `GenericResult.resultCode`
  * `ResultMessage`
    * used by `GenericResult.message`
  * `ScheduleType`
    * used by `Schedule.type`
  * `SeriesSelectionOrder`
    * used by `Series.seriesSelectionOrder`
  * `SeriesType`
    * used by `Series.seriesType`
  * `ShareScope`
    * used by `Share.scope`
  * `SheetEmailFormat`
    * used by `SheetEmail.format`
  * `SheetPublishAccess`
    * used by `SheetPublish.readWriteAccessibleBy`, `SheetPublish.readOnlyFullAccessibleBy`
  * `SheetPublishView`
    * used by `SheetPublish.readOnlyFullDefaultView`, `SheetPublish.readWriteDefaultView`
  * `SheetUserPermission`
    * used by `SheetUserPermissions.summaryPermissions`
  * `ShortcutWidgetContentType`
    * used by `ShortcutWidgetContent.type`
  * `SightPublishAccess`
    * used by `SightPublish.readOnlyFullAccessibleBy`
    * added `SHARED` as value per description (not sure why it wasn't included in enum values)
  * `SortDirection`
    * used by `SortCriterion.direction`
  * `SourceType`
    * used by `Source.type`
    * field description indicated an enumeration of values (not sure why this was an non-enum string)
  * `SystemColumnType` (renamed from `systemColumnType` (the type, not the fields that reference the type))
    * used by `ColumnObjectAttributes.systemColumnType`, `ColumnToCreateAsSheet.systemColumnType`, `Column.systemColumnType`
  * `TemplateGlobalType`
    * used by `Template.globalTemplate`
  * `TemplateLocale`
      * used by `Template.locale`
  * `TemplateType`
    * used by `Template.type`
  * `TooltipLabelType`
    * used by `ChartTooltipStyle.labelType`
  * `UpdateRequestStatus`
    * used by `SendUpdateRequest.status`
  * `UserStatus`
    * used by `User.status`
  * `WebhookStatus`
    * used by `Webhook.status`
  * `WidgetHyperlinkInteractionType`
    * used by `WidgetHyperlink.interactionType`
  * `WidgetType`
    * used by `Widget.type`
  * `WidgetViewMode`
    * used by `Widget.viewMode`
  * `WorkingDay`
    * used by `ProjectSettings.workingDays`
* objects
  * `EventAdditionalDetails`
    * used by `Event.additionalDetails`
  * `WorkspaceReference`
    * used by `Sight.workspace`

### renaming some types
* `cellObjectForRows` -> `CellObjectForRows`
* `Axes` -> `Axis`
* `CallbackEvents` -> `CallbackEventArray`
* `ColumnObjectAttributes` -> `ColumnObject`
* `Criteria` -> `Criterion`
* `Timestamp_date-time` -> `Timestamp_string` (matches `Timestamp_number` format)
  * also replaced all other use of strings with `format: date-time` with `Timestamp_string` refs
* `components-schemas-Sheet` -> `SheetReference`
* `SheetList` is not a list of Sheets; it's a slightly-more-than-SheetReference object; 
  * `SheetSummary` seems to fit (it's even in the path description) but is already taken so `OrganizationSheet` instead
* `schemas-Sheet` doesn't make sense at all (especially since there's already a Sheet type in the schemas section)
  * seems a good candidate for `MiniSheet`
* `cellObjectForRows` -> `CellObjectForRows`

### inlining description+type "types"
there are many single-use (or narrow-use) types that are just a combo of type and description; these should be inlined back to
where they're referenced for clarity and to avoid muddying up the schema section.
* contactOptions
* format
* formula
* id
* index
* locked
* name
* options
* permalink
* primary
* properties-contactOptions
* properties-id
* properties-options
* properties-symbol
* properties-title
* property-type (is an alias for ColumnType)
* validation
* icalEnabled
* readOnlyFullEnabled
* readOnlyFullShowToolbar
* readOnlyLiteEnabled
* readWriteEnabled
* readWriteShowToolbar
* symbol
* title
* type (alias for ColumnType)
* validation
* version
* width (but not height?)

### fixed type
* `ColumnObject.contactOptions`
  * was single `ContactOption` but name and SDK code indicate this should be an array of `ContactOption`
* `ProfileImage.height/width` 
  * were string but value is definitely integer

### Removed unused types
* `ContainerDestination`

### Oddities
* unquoting `'y'` and `'Y'` (not sure why these were specifically quoted)

### Parameters
extracting enums within parameters to top-level schema types for cleaner generated code, cleaner parameter definitions, and easier 
detection of enums with duplicate values that can potentially be consolidated.

#### new Enums
* `AcceptEncoding`
* `FavoriteInclude`
* `FolderWorkspaceInclude`
* `SheetCopyInclude`
* `SkipRemap`
* `SheetTemplateInclude`
* `ReportInclude`
* `ReportExclude`
* `SharingInclude`
* `SearchScope`
* `SourceInclude`
* `SheetInclude`
* `SheetExclude`
* `DiscussionInclude`
* `ProofInclude`
* `CopyRowsInclude`
* `MoveRowsInclude`
* `RowInclude`
* `CellHistoryInclude`
* `SheetSummaryInclude`
* `SheetSummaryExclude`
* `SightInclude`
* `SightLevel`
* `WorkspaceCreateInclude`
* `SheetLevel`

#### `explode: false` for comma-separated `in: query` parameters
any parameter that is sent as a "comma-separated list" should (must?  couldn't find the default explode value documented) include 
`explode:false` so it's sent `key=v1,v2,v3` rather than `key=v1&key=v2&key=v3`
* `favoriteInclude`
* `favoriteIds`
* `folderWorkspaceInclude`
* `reportExclude`
* `scopes`
* `sheetCopyInclude`
* `skipRemap`
* `include`
* `reportInclude`
* `reportExclude`
* `scopes`
* `sheetInclude`
* `parameters-sheetInclude` (rather unfortunate name)
* `sheetExclude`
* `sheetColumnIds`
* `sheetRowIds`
* `sheetRowNumbers`
* `discussionInclude`
* `proofInclude`
* `rowIds`
* `copyRowsInclude`
* `moveRowsInclude`
* `rowInclude`
* `cellHistoryInclude`
* `sheetSummaryInclude`
* `sheetSummaryExclude`
* `sheetSummaryFieldIds`
* `sightInclude`
* `emailInclude`
* `workspaceCreateInclude`

#### `type: array` for multi-value parameters
any parameters that are described as "list" are not of `type: array` so short of building a comma-delimited list by hand as a 
string (worst-case solution) it's not possible to specify multiple values for these params
* `favoriteInclude`
* `favoriteIds`
* `folderWorkspaceInclude`
* `scopes`
* `sheetCopyInclude`
* `skipRemap`
* `include`
* `reportInclude`
* `reportExclude`
* `scopes`
* `sheetInclude`
* `parameters-sheetInclude`
* `sheetExclude`
* `sheetColumnIds`
* `sheetRowIds`
* `sheetRowNumbers`
* `discussionInclude`
* `proofInclude`
* `rowIds`
* `copyRowsInclude`
* `moveRowsInclude`
* `rowInclude`
* `cellHistoryInclude`
* `sheetSummaryInclude`
* `sheetSummaryExclude`
* `sheetSummaryFieldIds`
* `sightInclude`
* `emailInclude`
* `workspaceCreateInclude`

#### type changes
some parameters are the wrong type for the data payload
* `favoriteIds` item type changed from `string` to `Int64`
  * matches type of `favoriteId` parameter
* `sheetColumnIds`, `sheetRowIds`, `rowIds`, `sheetSummaryFieldIds` changed from `string` to `Int64`
  * generally all IDs are longs except shareId (which is a stringy blob)
* `sheetLevel` changed from `integer` to `enum` of integer values (based on description)
* `sheetRowNumbers` changed from `string` to array of `integer` (since "numbers" are ints, being scoped to the container)
* `sightLevel` changed from open-ended `integer` to `enum` to restrict it to expected values

#### concerns/observations
* `sortRows` query-string parameter has a name of "include&exclude" which would have to be escaped constantly since `&` is the QS key-value delimiter 
* `lastLoginInclude` description sounds like it could be a 1-value enum

### Timestamp issues
* renamed `schema/Timestamp` to `schema/Datetime` because OpenAPI-codegen registers `java.sql.Timestamp` as the `Timestamp` type
  * [OpenAPI-codegen code](https://github.com/OpenAPITools/openapi-generator/blob/05e672d85672a2f7a97fe0d3601aadc26d43e572/modules/openapi-generator/src/main/java/org/openapitools/codegen/languages/AbstractJavaCodegen.java#L285)
* removing support for `Timestamp_number` as doing so triggers another feature (this time a good one) of OpenAPI-codegen: the use of `OffsetDateTime` for timestamps rather than a custom type

### renamed fields to match server response
* `Row.permaLink` MUST be `Row.permalink` to match the exact case of the server response

### adding fields to match server response
* `Row`
  * parentId, toTop, toBottom, above, indent, outdent, dateModifiedAt