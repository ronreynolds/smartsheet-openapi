## OpenApi Specification Changes

### Fixes
the general process of fixing the original OpenAPI spec is primarily to replace all `#/paths/...` and many
`#/components/schemas/<type>/<inner-bits>/...` field refs with type-specific refs (refs to the actual type, typically a
top-level `#/components/schemas/` element)

there are also a few bits that seem like actual bugs (so far fields that MUST (?) be the wrong type); this section is
intended to list those changes.

#### Column.contactOptions pointed to single ContactOption
```
#/components/schemas...
      "ColumnObject": {
          "contactOptions": {
            "$ref": "#/components/schemas/Column/properties/contactOptions/items"
          },
```
all references to `"contactOptions"` in the Java-SDK point to `List<Contact>` which would indicate that this type should
be a `ContactOptionArray` and not a single `ContactOption` object.  this also is in keeping with the common theme that
any collection/array of items has a plural name whereas single items have a singular name.

#### UserProfile.alternateEmails is single AlternateEmail
looks like a similar issue to `contactOptions` above

### Replacements
this is a list of just SOME replacements needed to address OpenAPI-generator parser errors of the form
`paths.'/favorites'(get).parameters. There are duplicate parameter values` (which happens mostly when a ref points to an
element of another path definition; e.g., one path pointing to the parameters, request-schema, or response-schema of
another path definition).

#### `$ref` caveat
* (this note may only apply to OpenAPI versions <3.1) - https://stackoverflow.com/questions/69173235/how-to-reference-response-components-in-openapi
* the `$ref` directive replaces ALL its sibling nodes so any time you want to add, say, a `description` to a `$ref` you
  must wrap the `$ref` in `"allOf":[{"$ref": "..."}]` or something similar - https://swagger.io/docs/specification/v3_0/using-ref/
    * note, this straight-forward solution doesn't work for path responses
        * there you have to nest the `$ref` within `"content": { "application/json": { "schema": { ... }}}` and reference a SCHEMA, not a RESPONSE. :(
        * i actually found it easier to inline the schema data back into the few (all error) responses that had descriptions

#### `default` for `Int64` type
had to remove the default because the Java won't compile (the value `604799` is `int`; can't be converted to `Long`)
```json
          "expires_in": {
            "description": "Number of seconds token is valid once issued.",
            "allOf": [
              {
                "$ref": "#/components/schemas/Int64"
              }
            ],
            "default": 604799,
            "readOnly": true
```

#### Path refs
* the path-schema refs were often addressed by reusing existing or creating new types for those requests or responses.
    * this only adds value when a type is used at least twice; otherwise leaving it "inline" seemed fine
    * tho it does mean the model is distributed between paths and schemas which makes it slightly less human-readable IMHO.
* the path-parameter refs were addressed by creating new parameter definitions and then ref'ing those parameters
* common error responses across paths were addressed by creating a family of `Error_*` responses
```
#/paths/~1contacts/parameters/0       = #/components/parameters/authorizationHeader
#/paths/~1contacts/get/parameters/0   = #/components/parameters/includeAllBoolean
#/paths/~1contacts/get/parameters/1   = #/components/parameters/modifiedSince
#/paths/~1contacts/get/parameters/2   = #/components/parameters/numericDates
#/paths/~1contacts/get/parameters/3   = #/components/parameters/pageNumber
#/paths/~1contacts/get/parameters/4   = #/components/parameters/pageSize

#/paths/~1contacts/get/responses/400  = #/components/responses/Error_400
#/paths/~1contacts/get/responses/401  = #/components/responses/Error_401
#/paths/~1contacts/get/responses/404  = #/components/responses/Error_404

#/paths/~1contacts/get/responses/200/content/application~1json/schema/allOf/0 = #/components/schemas/PagedResult

#/paths/~1favorites/parameters/1      = #/components/parameters/actorIdHeader
#/paths/~1favorites/get/parameters/3  = #/components/parameters/include_favorite
#/paths/~1favorites/post/parameters/0 = #/components/parameters/contentTypeHeader_JSON
#/paths/~1favorites/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items  = #/components/schemas/Favorite
#/paths/~1favorites/post/responses/200/content/application~1json/schema/allOf/0                       = #/components/schemas/ResultPrefix

#/paths/~1folders~1%7BfolderId%7D/parameters/1                                                = #/components/parameters/folderIdInPath
#/paths/~1folders~1%7BfolderId%7D/get/parameters/0                                            = #/components/parameters/include_folders
#/paths/~1folders~1%7BfolderId%7D/get/responses/200/content/application~1json/schema          = #/components/schemas/Folder
#/paths/~1folders~1%7BfolderId%7D/put/responses/200/content/application~1json/schema/allOf/0  = #/components/schemas/Result

#/paths/~1folders~1%7BfolderId%7D~1copy/post/parameters/1 = #/components/parameters/include_folder_copy
#/paths/~1folders~1%7BfolderId%7D~1copy/post/parameters/2 = #/components/parameters/exclude_folder_copy
#/paths/~1folders~1%7BfolderId%7D~1copy/post/parameters/3 = #/components/parameters/skipRemap_folder_copy
#/paths/~1folders~1%7BfolderId%7D~1copy/post/requestBody/content/application~1json/schema/oneOf/0    = #/components/schemas/ContainerDestination
#/paths/~1folders~1%7BfolderId%7D~1folders/get/responses/200/content/application~1json/schema/allOf/1/properties/result/items = #/components/schemas/Folder
#/paths/~1folders~1%7BfolderId%7D~1move/post/requestBody/content/application~1json/schema/oneOf/0 = #/components/schemas/ContainerDestination

#/paths/~1folders~1%7BfolderId%7D~1sheets/post/parameters/1                                         = #/components/parameters/include_sheets
#/paths/~1folders~1%7BfolderId%7D~1sheets/post/requestBody/content/application~1json/schema/oneOf/0 = #/components/schemas/SheetTemplate
#/paths/~1folders~1%7BfolderId%7D~1sheets/post/requestBody/content/application~1json/schema/oneOf/1 = #/components/schemas/SheetTemplateId
#/paths/~1folders~1%7BfolderId%7D~1sheets/post/responses/200/content/application~1json/schema/allOf/1/properties/result/oneOf/0    = #/components/schemas/SheetLite_withColumns
#/paths/~1folders~1%7BfolderId%7D~1sheets/post/responses/200/content/application~1json/schema/allOf/1/properties/result/oneOf/1    = #/components/schemas/SheetLite

#/paths/~1folders~1%7BfolderId%7D~1sheets~1import/post/parameters/0 = #/components/parameters/contentDispositionHeader
#/paths/~1folders~1%7BfolderId%7D~1sheets~1import/post/parameters/1 = #/components/parameters/contentTypeHeader_CSV_SHEET
#/paths/~1folders~1%7BfolderId%7D~1sheets~1import/post/parameters/2 = #/components/parameters/sheetName
#/paths/~1folders~1%7BfolderId%7D~1sheets~1import/post/parameters/3 = #/components/parameters/headerRowIndex
#/paths/~1folders~1%7BfolderId%7D~1sheets~1import/post/parameters/4 = #/components/parameters/primaryColumnIndex
#/paths/~1folders~1%7BfolderId%7D~1sheets~1import/post/responses/200/content/application~1json/schema/allOf/1/properties/result = #/components/schemas/SheetImportResult

#/paths/~1groups/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items             = #/components/schemas/Group
#/paths/~1groups~1%7BgroupId%7D/get/responses/200/content/application~1json/schema/allOf/1/properties/members/items = #/components/schemas/GroupMember
#/paths/~1groups~1%7BgroupId%7D~1members/post/requestBody/content/application~1json/schema/oneOf/0             = #/components/schemas/GroupMember 
#/paths/~1groups~1%7BgroupId%7D~1members~1%7BuserId%7D/parameters/2                                         = #/components/parameters/userIdInPath

#/paths/~1home~1folders/get/responses/200/content/application~1json/schema/allOf/1/properties/result/items 
  = #/components/schemas/Folder/properties/folders/items = #/components/schemas/Folder

#/paths/~1reports~1%7BreportId%7D/parameters/1  = #/parameters/acceptHeader
#/paths/~1reports~1%7BreportId%7D/parameters/2  = #/parameters/accessApiLevel
#/paths/~1reports~1%7BreportId%7D/parameters/3  = #/parameters/reportIdInPath
#/paths/~1reports~1%7BreportId%7D~1publish/get/responses/200/content/application~1json/schema         = #/components/schemas/ReportPublish
#/paths/~1reports~1%7BreportId%7D~1publish/put/responses/200/content/application~1json/schema/allOf/0 = #/components/schemas/BulkItemFailureResult
#/paths/~1reports~1%7BreportId%7D~1shares~1%7BshareId%7D/parameters/1                                 = #/components/parameters/shareIdInPath
#/paths/~1reports~1%7BreportId%7D~1shares/post/parameters/0                                           = #/components/parameters/sendEmail
#/paths/~1reports~1%7BreportId%7D~1shares/post/requestBody/content/application~1json/schema/oneOf/0   = #/components/schemas/Share
#/paths/~1reports~1%7BreportId%7D~1shares~1%7BshareId%7D/delete/responses/200/content/application~1json/schema = #/components/schemas/Result
#/paths/~1reports~1%7BreportId%7D~1shares~1%7BshareId%7D/get/responses/200/content/application~1json/schema = #/components/schemas/Share

#/paths/~1search~1sheets~1%7BsheetId%7D/parameters/0  = #/components/parameters/sheetIdInPath

#/paths/~1sheets~1%7BsheetId%7D/get/parameters/3      = #/components/parameters/exclude_sheet
#/paths/~1sheets~1%7BsheetId%7D/get/parameters/7      = #/components/parameters/compatibilityLevel
#/paths/~1sheets~1%7BsheetId%7D~1attachments~1%7BattachmentId%7D/parameters/1 = #/components/parameters/attachmentIdInPath
#/paths/~1sheets~1%7BsheetId%7D~1attachments~1%7BattachmentId%7D~1versions/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items 
  = #/components/schemas/Comment/properties/attachments/items = #/components/schemas/Attachment
#/paths/~1sheets~1%7BsheetId%7D~1attachments~1%7BattachmentId%7D~1versions/post/responses/200/content/application~1json/schema/allOf/0  = #/components/schemas/ResultPrefix
#/paths/~1sheets~1%7BsheetId%7D~1attachments/post/requestBody/content/application~1json/schema = #/components/schemas/URLAttachmentRequest
#/paths/~1sheets~1%7BsheetId%7D~1automationrules/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items       = #/components/schemas/AutomationRule
#/paths/~1sheets~1%7BsheetId%7D~1automationrules~1%7BautomationRuleId%7D/get/responses/200/content/application~1json/schema/allOf/0        = #/components/schemas/AutomationRule

#/paths/~1sheets~1%7BsheetId%7D~1columns~1%7BcolumnId%7D/parameters/1         = #/components/parameters/columnIdInPath
#/paths/~1sheets~1%7BsheetId%7D~1discussions/get/parameters/0                 = #/components/parameters/include_discussion
#/paths/~1sheets~1%7BsheetId%7D~1discussions~1%7BdiscussionId%7D/parameters/1 = #/components/parameters/discussionIdInPath
#/paths/~1sheets~1%7BsheetId%7D~1proofs~1%7BproofId%7D/parameters/1           = #/components/parameters/proofIdInPath
#/paths/~1sheets~1%7BsheetId%7D~1rows/post/parameters/2                       = #/components/parameters/allowPartialSuccess
#/paths/~1sheets~1%7BsheetId%7D~1rows/post/parameters/3                       = #/components/parameters/overrideValidation
#/paths/~1sheets~1%7BsheetId%7D~1rows/post/requestBody/content/application~1json/schema/oneOf/0        = #/components/schemas/ReportRow/allOf/0 = ...
#/paths/~1sheets~1%7BsheetId%7D~1rows/post/responses/200/content/application~1json/schema/allOf/0 = #/components/schemas/ResultPrefix
#/paths/~1sheets~1%7BsheetId%7D~1rows~1%7BrowId%7D/parameters/1               = #/components/parameters/rowIdInPath
#/paths/~1sheets~1%7BsheetId%7D~1shares/post/requestBody/content/application~1json/schema/oneOf/0   = #/paths/~1reports~1%7BreportId%7D~1shares/post/requestBody/content/application~1json/schema/oneOf/0 = 

#/paths/~1sheets~1%7BsheetId%7D~1rows/post/requestBody/content/application~1json/schema/oneOf/0 = #/components/schemas/Row
#/paths/~1sheets~1%7BsheetId%7D~1columns/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items    = #/components/schemas/ColumnLite
#/paths/~1sheets~1%7BsheetId%7D~1rows~1%7BrowId%7D~1columns~1%7BcolumnId%7D~1cellimages/post/parameters/2 = #/components/parameters/contentLengthHeader
#/paths/~1sheets~1%7BsheetId%7D~1rows~1%7BrowId%7D~1columns~1%7BcolumnId%7D~1cellimages/post/parameters/3 = #/components/parameters/altText
#/paths/~1sheets~1%7BsheetId%7D~1summary/get/parameters/0 = #/components/parameters/include_sheetSummary
#/paths/~1sheets~1%7BsheetId%7D~1summary/get/parameters/1 = #/components/parameters/exclude_sheetSummary
#/paths/~1sheets~1%7BsheetId%7D~1publish/get/responses/200/content/application~1json/schema = #/components/schemas/SheetPublish

#/paths/~1sights~1%7BsightId%7D/parameters/1    = #/components/parameters/sightIdInPath
#/paths/~1sights~1%7BsightId%7D~1publish/get/responses/200/content/application~1json/schema = #/components/schemas/SightPublish
#/paths/~1sights~1%7BsightId%7D/get/responses/200/content/application~1json/schema = #/components/schemas/Sight

#/paths/~1users/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items = #/components/schemas/User

#/paths/~1workspaces/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items     = #/components/schemas/Scope/properties/workspaces/items = #/components/schemas/Workspace
#/paths/~1workspaces~1%7BworkspaceId%7D/parameters/1     = #/components/parameters/workspaceIdInPath
#/paths/~1workspaces~1%7BworkspaceId%7D~1shares/post/requestBody/content/application~1json/schema/oneOf/0 = #/paths/~1reports~1%7BreportId%7D~1shares/post/requestBody/content/application~1json/schema/oneOf/0 = #/components/schemas/Share

...and so forth...  (dozens left out)
```

#### Schema refs
some refs point to the types of fields within schema components (sort of like defining one class by pointing to the type
of another class's field); these can generate similar errors to the paths-refing-paths issue.  this is brittle because
it assumes the structure of another type which could potentially change, thus breaking those refs.  it also makes the
ref'ing type less clear since you have to follow those links to other fields in other types.  also these types might not
always match; that seems to be an assumption of the openapi-spec generator (that types are immutable and that it should
simply ref to the first type it created; also it seems to default to generating types within the first path where they're
used).  in short most of these issues start with the assumptions passed into or built into the openapi-spec generator.
```
#/components/schemas/Attachment/properties/createdAt                      = #/components/schemas/DateUnion
#/components/schemas/Attachment/properties/createdBy/allOf/0              = #/components/schemas/NameAndEmail
#/components/schemas/AutomationAction/oneOf/1/properties/recipients/items = #/components/schemas/EmailOrGroupId
#/components/schemas/BulkItemFailure/properties/error                     = #/components/schemas/Error
#/components/schemas/Comment/properties/attachments/items                 = #/components/schemas/Attachment
#/components/schemas/Discussion/properties/accessLevel                    = #/components/schemas/AccessLevel
#/components/schemas/Folder/properties/folders/items                      = #/components/schemas/Folder
#/components/schemas/ReportRow/allOf/0                                    = #/components/schemas/Row
#/components/schemas/Sheet/properties/accessLevel                         = #/components/schemas/AccessLevel 
#/components/schemas/Sheet/properties/id                                  = #/components/schemas/Int64
```

#### General replacements
some types are so common (the int-64 long type used for almost all IDs, long[], and string[]) that it saved typing to
create types for these common patterns.
* `"type":"number|integer"[, "format":"int64"]` = `"$ref": "#/components/schemas/Int64"`
* `"type":"number"` = `"type":"integer"` for many types 
  * `"type":"number"` maps to a `java.math.BigDecimal` which is almost NEVER what you want/mean

#### CodeGen-limitation changes
* because there's a `Cell.objectValue` field it generated a type which conflicted with the type generated for 
`#/components/schemas/CellObjectValue` so i renamed the type `#/components/schemas/CellObjectValueObj`
* any `anyOf`'s that contain only 1 type were changed to `oneOf` to simplify the generated code (and because it's more precise)

#### missing, misnamed, and mistyped fields
some parts of the Spec don't match the server responses at all
* `#/components/schemas/ServerInfo` missing fields `featureInfo`, `appleAuthInfos`, `azureAuthInfo`, and `serverVersion` 
* `#/components/schemas/Row`'s field `permaLink` is actually `permalink`
  * like most programming languages the spec is case-sensitive
* `#/components/schemas/Sheet` missing fields `filters` and `ganttConfig` which in turn required adding new types:
  * `#/components/schemas/Filter`
  * `#/components/schemas/DayOfWeek`
  * `#/components/schemas/Month`
* `#/components/schemas/UserProfile` missing field `status`
* `#/paths/templates` and `#/paths/templates/public` response 
  * moved the `TemplateArray` into a `data` field
* `#/paths/folders/personal` response
  * added `id`, `name`, and `permalink` to response schema
* `#/paths/users/{userId} DELETE` response
  * missing fields `sheetsRemovedFromSharing` and `workspacesRemovedFromSharing`
* `#/components/schemas/Workspace` 
  * missing many fields, including:
    * `sheets` - array of minimal Sheet records
    * `folders` - array of minimal Folder records
    * `reports` - array of minimal Report records
* `#/paths/workspaces/{workspaceId}/folders POST` response 
  * missing fields `id`(Int64) and `permalink`(string)
* `#/paths/users/{userId} PUT` response 
  * field `result` changed type `UserProfileImageResponseArray` to `User`
* `#/paths/workspaces POST` request 
  * type changed from `Workspace` to `WorkspaceLite`
    * `Workspace` without child containers (which are not allowed in create-workspace request)
* `#/components/schemas/Folder` added fields
  * `accessLevel`, `createdAt`, `modifiedAt`
* `#/components/schemas/Column` added `virtualId` and `sheetNameColumn` fields from (unused and deleted) `ReportColumn` 
* `#/components/schemas/Cell` added `virtualColumnId` from (unused and deleted) `ReportCell`
* `#/components/schemas/Row` added `dataModifiedAt` (returned by server; not in latest spec)
* ...

##### result/data response field mismatch (GET returns 'data'; PUT/POST returns 'result')
* `result` response field renamed to `data`
    * `#/paths/folders/{folderId}/folders GET`
    * `#/paths/home/folders GET`
    * `#/paths/workspaces/{workspaceId}/shares GET`
    * `#/paths/reports/{reportId}/shares GET`
    * `#/paths/sheets/{sheetId}/shares GET`
    * `#/paths/sights/{sightId}/shares GET`
* `data` response field renamed to `result`
    * `#/paths/workspaces POST`
    * `#/paths/users/{userId} PUT`
    * `#/paths/workspaces POST`

##### IDs are longs not strings
* `string` fields that are actually `#/components/schemas/Int64` (i.e., `long`)
  * `#/components/parameters/attachmentIdInPath`
  * `#/components/parameters/commentIdInPath`
  * `#/components/parameters/discussionIdInPath`
  * `#/components/parameters/proofIdInPath`
  * `#/components/parameters/sightIdInPath`
  * `#/components/parameters/webhookIdInPath`
  * `#/components/parameters/workspaceIdInPath`

#### further improvements
* added `#/components/schemas/ShareScope` enum to replace `string` for `#/components/schemas/Share.scope`
* added `#/components/schemas/ShareType` enum to replace `string` for `#/components/schemas/Share.type`
* added `#/components/schemas/UserStatus` enum to replace `string-enum` for `#/components/schemas/User.status` 
* added `#/components/schemas/GroupId`
* changed `#/components/schemas/EmailOrGroupId` to use `#/components/schemas/EmailAddress` and `#/components/schemas/GroupId`
* removed unused `#/components/schemas/Recipient` (also identical in structure to EmailOrGroupId)
* added `#/components/schemas/SourceType` enum to replace `string` for `#/components/schemas/Source.type`
* changed `#/components/schemas/SheetEmail.formatDetails.paperSize` from `string-enum` to `#/components/schemas/PaperSize` 
* added `#/components/schemas/SheetEmailFormat`
* changed `#/components/schemas/SheetEmail.format` from `string-enum` to `#/components/schemas/SheetEmailFormat`
* renamed `#/components/schemas/AttachmentType_smar` to `#/components/schemas/AttachmentType` (matches Java-SDK enum)
* changed `#/components/schemas/Sheet.effectiveAttachmentOptions` from array-string to array-AttachmentType
* changed `#/components/schemas/Attachment.attachmentSubType` from string-enum to `AttachmentSubType` enum
* added `#/components/schemas/ReportBrief` and `#/components/schemas/ReportBriefArray` to replace `getReports` response data
* renamed all "*Lite" types to "*Brief"
  * `CellLite`
  * `ColumnLite`
  * `CommentLite`
  * `SheetLite`
  * `SheetLite_withColumns`
  * `SightLite`
  * `WorkspaceLite`