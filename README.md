# Smartsheet OpenApi-Generated SDK

## Purpose
the purpose of this project is to replace the need for the various language-specific Smartsheet-SDK projects with one
generated from the OpenAPI spec file.  the current OpenAPI spec file at https://smartsheet.redoc.ly/ is unusable as-is
so this project contains a heavily modified version of that OpenAPI spec and a gradle build script that will generate
the classes needed to invoke the API.

## Process
### https://editor.swagger.io/
* because the Smartsheet API uses DELETE operations with a request body we must bump the spec to 3.1.0 (the first 
version to support DELETE requests with a body)
  * i'm not sure if this is 100% true; still TBD (currently still on spec 3.0.3)
  
### org.openapi.generator
#### openApiValidate
* working thru issues 
  * mostly it seems to not like endpoints with multiple parameter refs to parameters of other endpoints

## Changes
### Replacements
this is a list of just SOME of the replacements needed to address OpenAPI-generator parser errors of the form
`paths.'/favorites'(get).parameters. There are duplicate parameter values` (which happens mostly when a ref points to an
element of another path definition; e.g., one path pointing to the parameters, request-schema, or response-schema of 
another path definition).

#### Path refs
* the path-schema refs were addressed by creating new types for those requests or responses.
* the path-parameter refs were addressed by creating new parameter definitions and then ref'ing those parameters
* common error responses across paths were addressed by creating a family of `Error_*` responses

```
#/paths/~1contacts/parameters/0			= #/components/parameters/authorizationHeader

#/paths/~1contacts/get/parameters/0	  = #/components/parameters/includeAllBoolean
#/paths/~1contacts/get/parameters/1	  = #/components/parameters/modifiedSince
#/paths/~1contacts/get/parameters/2	  = #/components/parameters/numericDates
#/paths/~1contacts/get/parameters/3	  = #/components/parameters/pageNumber
#/paths/~1contacts/get/parameters/4	  = #/components/parameters/pageSize

#/paths/~1contacts/get/responses/400  = #/components/responses/Error_400
#/paths/~1contacts/get/responses/401  = #/components/responses/Error_401
#/paths/~1contacts/get/responses/404  = #/components/responses/Error_404

#/paths/~1contacts/get/responses/200/content/application~1json/schema/allOf/0 		= #/components/schemas/PagedResult

#/paths/~1favorites/parameters/1	            = #/components/parameters/actorIdHeader
#/paths/~1favorites/get/parameters/3      = #/components/parameters/include_favorite
#/paths/~1favorites/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items	= #/components/schemas/Favorite
#/paths/~1favorites/post/parameters/0	    = #/components/parameters/contentTypeHeader
#/paths/~1favorites/post/responses/200/content/application~1json/schema/allOf/0 	= #/components/schemas/ResultPrefix

#/paths/~1folders~1%7BfolderId%7D/parameters/1 		= #/components/parameters/folderIdInPath
#/paths/~1folders~1%7BfolderId%7D/get/parameters/0 = #/components/parameters/include_folders
#/paths/~1folders~1%7BfolderId%7D/get/responses/200/content/application~1json/schema    = #/components/schemas/Folder
#/paths/~1folders~1%7BfolderId%7D/put/responses/200/content/application~1json/schema/allOf/0	= #/components/schemas/Result

#/paths/~1folders~1%7BfolderId%7D~1copy/post/parameters/1 = #/components/parameters/include_folder_copy
#/paths/~1folders~1%7BfolderId%7D~1copy/post/parameters/2 = #/components/parameters/exclude_folder_copy
#/paths/~1folders~1%7BfolderId%7D~1copy/post/parameters/3 = #/components/parameters/skipRemap_folder_copy
#/paths/~1folders~1%7BfolderId%7D~1copy/post/requestBody/content/application~1json/schema/oneOf/0	= #/components/schemas/ContainerDestination
#/paths/~1folders~1%7BfolderId%7D~1folders/get/responses/200/content/application~1json/schema/allOf/1/properties/result/items = #/components/schemas/Folder
#/paths/~1folders~1%7BfolderId%7D~1move/post/requestBody/content/application~1json/schema/oneOf/0 = #/components/schemas/ContainerDestination

#/paths/~1folders~1%7BfolderId%7D~1sheets/post/parameters/1 = #/components/parameters/include_sheets

#/paths/~1folders~1%7BfolderId%7D~1sheets/post/requestBody/content/application~1json/schema/oneOf/0		= #/components/schemas/SheetTemplate
#/paths/~1folders~1%7BfolderId%7D~1sheets/post/requestBody/content/application~1json/schema/oneOf/1		= #/components/schemas/SheetTemplateId
#/paths/~1folders~1%7BfolderId%7D~1sheets/post/responses/200/content/application~1json/schema/allOf/1/properties/result/oneOf/0	= #/components/schemas/SheetLite_withColumns
#/paths/~1folders~1%7BfolderId%7D~1sheets/post/responses/200/content/application~1json/schema/allOf/1/properties/result/oneOf/1	= #/components/schemas/SheetLite

#/paths/~1folders~1%7BfolderId%7D~1sheets~1import/post/parameters/0 	= #/components/parameters/contentDispositionHeader
#/paths/~1folders~1%7BfolderId%7D~1sheets~1import/post/parameters/1 	= #/components/parameters/contentTypeHeader_CSV_SHEET
#/paths/~1folders~1%7BfolderId%7D~1sheets~1import/post/parameters/2		= #/components/parameters/sheetName
#/paths/~1folders~1%7BfolderId%7D~1sheets~1import/post/parameters/3		= #/components/parameters/headerRowIndex
#/paths/~1folders~1%7BfolderId%7D~1sheets~1import/post/parameters/4		= #/components/parameters/primaryColumnIndex
>> #/paths/~1folders~1%7BfolderId%7D~1sheets~1import/post/responses/200/content/application~1json/schema/allOf/1/properties/result = #/components/schemas/SheetImportResult

#/paths/~1groups/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items 		= #/components/schemas/Group
#/paths/~1groups~1%7BgroupId%7D~1members/post/requestBody/content/application~1json/schema/oneOf/0 		= #/components/schemas/GroupMember 
#/paths/~1groups~1%7BgroupId%7D~1members~1%7BuserId%7D/parameters/2 									= #/components/parameters/userIdInPath
#/paths/~1groups~1%7BgroupId%7D/get/responses/200/content/application~1json/schema/allOf/1/properties/members/items = ???

#/paths/~1home~1folders/get/responses/200/content/application~1json/schema/allOf/1/properties/result/items	= #/components/schemas/Folder/properties/folders/items = #/components/schemas/Folder

#/paths/~1reports~1%7BreportId%7D/parameters/1	= #/parameters/acceptHeader
#/paths/~1reports~1%7BreportId%7D/parameters/2	= #/parameters/accessApiLevel
#/paths/~1reports~1%7BreportId%7D/parameters/3  = #/parameters/reportIdInPath
#/paths/~1reports~1%7BreportId%7D~1publish/get/responses/200/content/application~1json/schema               = #/components/schemas/ReportPublish
#/paths/~1reports~1%7BreportId%7D~1publish/put/responses/200/content/application~1json/schema/allOf/0 = #/components/schemas/BulkItemFailureResult
#/paths/~1reports~1%7BreportId%7D~1shares~1%7BshareId%7D/parameters/1	                                                = #/components/parameters/shareIdInPath
#/paths/~1reports~1%7BreportId%7D~1shares/post/parameters/0	                                                                        = #/components/parameters/sendEmail
#/paths/~1reports~1%7BreportId%7D~1shares/post/requestBody/content/application~1json/schema/oneOf/0 = #/components/schemas/Share

#/paths/~1reports~1%7BreportId%7D~1shares~1%7BshareId%7D/delete/responses/200/content/application~1json/schema = #/components/schemas/Result
#/paths/~1reports~1%7BreportId%7D~1shares~1%7BshareId%7D/get/responses/200/content/application~1json/schema = #/components/schemas/Share

#/paths/~1search~1sheets~1%7BsheetId%7D/parameters/0							            = #/components/parameters/sheetIdInPath

#/paths/~1sheets~1%7BsheetId%7D~1attachments~1%7BattachmentId%7D/parameters/1	= #/components/parameters/attachmentIdInPath
#/paths/~1sheets~1%7BsheetId%7D~1rows~1%7BrowId%7D/parameters/1				= #/components/parameters/rowIdInPath
#/paths/~1sheets~1%7BsheetId%7D~1columns~1%7BcolumnId%7D/parameters/1	= #/components/parameters/columnIdInPath
#/paths/~1sheets~1%7BsheetId%7D~1rows/post/parameters/2							        = #/components/parameters/allowPartialSuccess
#/paths/~1sheets~1%7BsheetId%7D~1rows/post/parameters/3							        = #/components/parameters/overrideValidation
#/paths/~1sheets~1%7BsheetId%7D~1rows/post/requestBody/content/application~1json/schema/oneOf/0		= #/components/schemas/ReportRow/allOf/0 = ...
#/paths/~1sheets~1%7BsheetId%7D~1shares/post/requestBody/content/application~1json/schema/oneOf/0   = #/paths/~1reports~1%7BreportId%7D~1shares/post/requestBody/content/application~1json/schema/oneOf/0 = 
#/paths/~1groups~1%7BgroupId%7D/get/responses/200/content/application~1json/schema/allOf/1/properties/members/items = #/components/schemas/GroupMember

#/paths/~1sheets~1%7BsheetId%7D~1attachments~1%7BattachmentId%7D~1versions/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items = #/components/schemas/Comment/properties/attachments/items = #/components/schemas/Attachment
#/paths/~1sheets~1%7BsheetId%7D~1attachments~1%7BattachmentId%7D~1versions/post/responses/200/content/application~1json/schema/allOf/0  = #/components/schemas/ResultPrefix
#/paths/~1sheets~1%7BsheetId%7D~1automationrules/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items       = #/components/schemas/AutomationRule
#/paths/~1sheets~1%7BsheetId%7D~1automationrules~1%7BautomationRuleId%7D/get/responses/200/content/application~1json/schema/allOf/0	    = #/components/schemas/AutomationRule
#/paths/~1sheets~1%7BsheetId%7D~1rows/post/requestBody/content/application~1json/schema/oneOf/0 = #/components/schemas/Row
#/paths/~1sheets~1%7BsheetId%7D/get/parameters/3	= #/components/parameters/exclude_sheet
#/paths/~1sheets~1%7BsheetId%7D/get/parameters/7	= #/components/parameters/compatibilityLevel
#/paths/~1sheets~1%7BsheetId%7D~1columns/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items	= #/components/schemas/ColumnLite
#/paths/~1sheets~1%7BsheetId%7D~1rows~1%7BrowId%7D~1columns~1%7BcolumnId%7D~1cellimages/post/parameters/2 = #/components/parameters/contentLengthHeader
#/paths/~1sheets~1%7BsheetId%7D~1rows~1%7BrowId%7D~1columns~1%7BcolumnId%7D~1cellimages/post/parameters/3 = #/components/parameters/altText
#/paths/~1sheets~1%7BsheetId%7D~1summary/get/parameters/0	= #/components/parameters/include_sheetSummary
#/paths/~1sheets~1%7BsheetId%7D~1summary/get/parameters/1	    = #/components/parameters/exclude_sheetSummary
#/paths/~1sheets~1%7BsheetId%7D~1publish/get/responses/200/content/application~1json/schema = #/components/schemas/SheetPublish

#/paths/~1sights~1%7BsightId%7D/parameters/1	= #/components/parameters/sightIdInPath
#/paths/~1sights~1%7BsightId%7D~1publish/get/responses/200/content/application~1json/schema = #/components/schemas/SightPublish
#/paths/~1sights~1%7BsightId%7D/get/responses/200/content/application~1json/schema = #/components/schemas/Sight

#/paths/~1workspaces/get/responses/200/content/application~1json/schema/allOf/1/properties/data/items     = #/components/schemas/Scope/properties/workspaces/items = #/components/schemas/Workspace
#/paths/~1workspaces~1%7BworkspaceId%7D/parameters/1 	= #/components/parameters/workspaceIdInPath
#/paths/~1workspaces~1%7BworkspaceId%7D~1shares/post/requestBody/content/application~1json/schema/oneOf/0	
    = #/paths/~1reports~1%7BreportId%7D~1shares/post/requestBody/content/application~1json/schema/oneOf/0 = #/components/schemas/Share

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
#/components/schemas/Attachment/properties/createdAt			= #/components/schemas/DateUnion
#/components/schemas/Attachment/properties/createdBy/allOf/0 = #/components/schemas/NameAndEmail
#/components/schemas/AutomationAction/oneOf/1/properties/recipients/items = #/components/schemas/EmailOrGroupId
#/components/schemas/BulkItemFailure/properties/error 				= #/components/schemas/Error
#/components/schemas/Comment/properties/attachments/items 	= #/components/schemas/Attachment
#/components/schemas/Discussion/properties/accessLevel  			= #/components/schemas/AccessLevel
#/components/schemas/Folder/properties/folders/items 				= #/components/schemas/Folder
#/components/schemas/ReportRow/allOf/0									= #/components/schemas/Row
#/components/schemas/Sheet/properties/accessLevel 					= #/components/schemas/AccessLevel 
#/components/schemas/Sheet/properties/id									= #/components/schemas/Int64
```

#### General replacements
some types are so common (the int-64 long type used for almost all IDs, long[], and string[]) that it saved typing to 
create types for these common patterns.
```
"type":"number|integer"[, "format":"int64"] 	= "$ref": "#/components/schemas/Int64"
"type":"array", "items":{"type":"number"} 		= "$ref": "#/components/schemas/Int64Array"
"type":"array", "items":{"type":"string"} 			= "$ref": "#/components/schemas/StringArray"
"type":"number" = "type":"integer" for many types ("type":"number" maps to a `java.math.BigDecimal` which is almost NEVER what you want/mean)
```
