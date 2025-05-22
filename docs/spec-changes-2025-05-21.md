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
* TODO
  * enums
    * CompatibilityLevel
    * FolderInclude
    * PaperSize
    * ReportInclude
    * SheetExclude
    * SheetInclude
  * classes
    * CellBrief and other *Brief types to address subset types of top-level domain models
