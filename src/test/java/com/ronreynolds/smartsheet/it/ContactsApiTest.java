/*
 * Smartsheet API Reference
 *  Updated 2025-01-22    <br/><br/><br/><br/>  # Introduction  The Smartsheet API enables you to access and manage your
 * organization's Smartsheet resources programmatically. Here are some of the things you can do:  - Read and update sheets -
 * Manage folders and workspaces - Administer Smartsheet plans and user accounts  The [API resource specification]
 * (/#section/RESOURCES) includes object schemas, method specifications, and code snippets for [Folders]
 * (/#tag/foldersDescription), [Sheets](/#tag/sheetsDescription), [Cells](/#tag/cellsDescription), and more.  > **IMPORTANT:**
 * > * The Smartsheet API is restricted to users on Business and Enterprise plans > * The [Developer Agreement](https://www
 * .smartsheet.com/legal/developer-program-agreement) governs the use of the Smartsheet API and Smartsheet software development
 *  kits (SDKs)  Let's start with the essentials.  ### Base URL  `https://api.smartsheet.com/2.0`  ### Authentication  The API
 * authenticates using access tokens (API keys). You can [generate access tokens](https://help.smartsheet
 * .com/articles/2482389-generate-API-key) in the Smartsheet UI.  Each API request requires passing in an access token as the
 * `Bearer` value in your authorization header. For example,  `Authorization: Bearer JKlMNOpQ12RStUVwxYZAbcde3F5g6hijklM789`
 * ### Request  For example, you can list your sheets by executing the following `GET /sheets` method (swap in *your* access
 * token).  ```bash curl -X GET -H \"Authorization: Bearer JKlMNOpQ12RStUVwxYZAbcde3F5g6hijklM789\" \\ \"https://api.smartsheet
 * .com/2.0/sheets\" ```  ### Response  The JSON response should look something like this (after formatting):  ```json {
 * \"pageNumber\": 1,   \"pageSize\": 100,   \"totalPages\": 1,   \"totalCount\": 2,   \"data\": [     {       \"id\":
 * 6141831453927300,       \"name\": \"My first sheet\",       \"accessLevel\": \"ADMIN\",       \"permalink\": \"https://app
 * .smartsheet.com/b/home?lx=8enlO7GkdYSz-cHHVus33A\",       \"createdAt\": \"2023-09-25T17:38:02Z\",       \"modifiedAt\":
 * \"2023-09-25T17:38:09Z\"     },     {       \"id\": 6141831453927300,       \"name\": \"Sheet shared to me\",
 * \"accessLevel\": \"VIEWER\",       \"permalink\": \"https://app.smartsheet.com/b/home?lx=8enlO7GkdYSz-cHHVus33A\",
 * \"createdAt\": \"2017-06-27T21:17:15Z\",       \"modifiedAt\": \"2023-04-19T17:16:05Z\"     }   ] } ```  Congratulations on
 * executing your first Smartsheet API request!  ### What's next?  - [API Basics](/#section/API-Basics) (recommended) explains
 * the fundamentals of using the Smartsheet API - [Resources](/#section/RESOURCES): skip ahead to learn about resource
 * endpoints and objects, including [Folders](/#tag/foldersDescription), [Sheets](/#tag/sheetsDescription), [Cells]
 * (/#tag/cellsDescription), and more - [Smartsheet SDKs and samples](/#section/sdks-and-samples), for streamlined interfaces
 * in C#, Java, Node.js, and Python   # API Basics  ## Authentication and Access Tokens  The API authenticates using access
 * tokens (API keys). An access token must accompany every request.   ### Generate an Access Token  You can [generate access
 * tokens](https://help.smartsheet.com/articles/2482389-generate-API-key) in the Smartsheet UI.  1. Go to the Smartsheet UI. 1.
 *  Click the **Account** button in the lower-left corner of the Smartsheet screen, and then click **Personal Settings**. 2.
 * Click the **API Access** tab. 3. Click the **Generate new access token** button to obtain an access token.  > IMPORTANT:
 * Store your access token in a secure location. See [Access Token Best Practices]
 * (/#section/Security/Access-Token-Best-Practices).  ### Use Access Tokens in Requests  Pass in your access token as the
 * `Bearer` value in your request's authorization header. For example,  `Authorization: Bearer
 * JKlMNOpQ12RStUVwxYZAbcde3F5g6hijklM789`  The example request below, gets a listing of sheets belonging to the user
 * associated with the access token. You can try the command by swapping in your own access token.  ```bash curl -X GET -H
 * \"Authorization: Bearer JKlMNOpQ12RStUVwxYZAbcde3F5g6hijklM789\" \\ \"https://api.smartsheet.com/2.0/sheets\" ````  >
 * IMPORTANT: Since the access token is being transmitted in clear text, all API calls are done over HTTPS.  As an alternative
 * to using manually generated access tokens, you can authenticate consenting users on the fly via [OAuth with Smartsheet]
 * (/#section/Security/Application-Authentication-Approaches).  See the [Security](/#section/Security) section for details and
 * security best practices.  ## Dates and Times  The Smartsheet API returns all dates and times in [UTC](https://en.wikipedia
 * .org/wiki/Coordinated_Universal_Time) and formatted for [ISO-8601](https://en.wikipedia.org/wiki/ISO_8601),
 * YYYY-MM-DDTHH:MM:SSZ. If you specify a date and time, you should also send that information in ISO-8601 format. If a
 * date/time needs to be displayed to an end-user in their local time zone, you must do the conversion using the user's time
 * zone, which you can obtain by [getting the current user](/#tag/users/operation/get-current-user).  You can optionally choose
 *  to receive and send dates/times in numeric format, as milliseconds since the UNIX epoch (midnight on January 1, 1970, in
 * UTC), using the query string parameter numericDates with a value of true. This query parameter works for any API request.
 * NOTE: Some SDK methods use language-specific Date objects, which require different date formats.  ## Filtering  You cannot
 * create or update filters using the API; however, you can query which rows have been filtered out and you can get filter
 * definitions.  For details, see [Filters object](/tag/sheetsObjects#section/Filters-Object).  ## Formatting  To set or read
 * formatting programmatically, Smartsheet uses a compact format string, **cell.format**, which looks something like this: `\",
 * ,1,1,,,,,,,,,,,,,\"`. The position and sample values in this string are explained in the following format descriptor table:
 *  **Format Descriptor Table**  **Position** | **Lookup Property** | **Example Value** | **Format String**
 * -----|-----|-----|-----| 0 | **fontFamily** | 0 = Arial, default | `\"0,,,,,,,,,,,,,,,,\"` 1 | **fontSize** | 0 = 10 pt,
 * default | `\",0,,,,,,,,,,,,,,,\"` 2 | **bold** | 1 = on | `\",,1,,,,,,,,,,,,,,\"` 3 | **italic** | 1 = on | `\",,,1,,,,,,,,,
 * ,,,,\"` 4 | **underline** | 1 = on | `\",,,,1,,,,,,,,,,,,\"` 5 | **strikethrough** | 1 = on | `\",,,,,1,,,,,,,,,,,\"` 6 |
 * **horizontalAlign** | 2 = center | `\",,,,,,2,,,,,,,,,,\"` 7 | **verticalAlign** | 2 = middle | `\",,,,,,,2,,,,,,,,,\"` 8 |
 * **color** (text) | 4 = #FEEEF0 | `\",,,,,,,,4,,,,,,,,\"` 9 | **color** (background) | 8 = #E6F5FE | `\",,,,,,,,,8,,,,,,,\"`
 * 10 | **color** (taskbar) | 9 = #F3E5FA | `\",,,,,,,,,,9,,,,,,\"` 11 | **currency** | 13 = USD | `\",,,,,,,,,,,13,,,,,\"` 12
 * | **decimalCount** | 3 = three decimal places | `\",,,,,,,,,,,,3,,,,\"` 13 | **thousandsSeparator** | 1 = on | `\",,,,,,,,,,
 * ,,,1,,,\"` 14 | **numberFormat** | 2 = currency | `\",,,,,,,,,,,,,,2,,\"` 15 | **textWrap** | 1 = on | `\",,,,,,,,,,,,,,,1,
 * \"` 16 | **dateFormat** | 1 = mmmm d yyyy (December 8, 1997) | `\",,,,,,,,,,,,,,,,1\"`  NOTES: * Formats that have not been
 * explicitly set are omitted in the descriptor string. For example, a cell that has been set to bold and italic, but has no
 * other formats applied to it, has a format descriptor of <code>\",,1,1,,,,,,,,,,,,,\"</code>. * Use <code>GET
 * /serverinfo</code> to return the [FormatTables](/tag/commonObjects#section/FormatTables-Object) object, which tells you both
 *  the default settings and what formatting options are available.  **Applying Formatting**  Use the \"include=format\"
 * query-string parameter on API operations that return detailed objects, such as `GET /sheets/{sheetId}` or `GET
 * sheets/{sheetId}/rows/{rowId}`. If there is formatting other than default settings, the return includes a **format**
 * property. If an object has conditional formatting, the **format** property returned will have a **conditionalFormat** value.
 *   Setting the format of a row object or column object through the API simply sets the baseline format for new or blank cells
 *  in that row or column.  It does not affect cells that already have a value.  If you want to change the formatting of cells
 * that already have content, for instance you want to make a row bold, then you have to set the format for each cell
 * individually.  ## Formulas   Formulas are processed per cell in the UI. Use the [Cell object]
 * (/tag/cellsObjects#section/Cell-Object) to manipulate formulas via the API.  For requests, use [Update Rows]
 * (/tag/rows#operation/update-rows) to add or update the formula in a cell.  For response payloads, formulas (when present)
 * are returned whenever the Cell object is returned, so for example, GET /sheets/(id) returns the Cell object for each cell
 * and that object contains a **formula** value when the cell contains a formula.  ## HTTP and REST  The REST URL structure
 * follows typical resource-oriented conventions.  To get a list of sheets, use the following:  `GET https://api.smartsheet
 * .com/2.0/sheets`  This returns a list of Sheet objects, where each sheet has an **id** attribute.  To get details on the
 * sheet with **id 123456**, use the following:  `GET https://api.smartsheet.com/2.0/sheets/123456`  This Id pattern is
 * repeated throughout the API. Columns, rows, cells, comments, attachments, or any other data element have a unique Id.  If
 * you don't want to make raw HTTP calls, Smartsheet also has several Software Development Kits (SDKs) that provide a higher
 * level interface for popular programming languages. For more information, see [SDKs and Samples](/#section/SDKs-and-Samples).
 *   ## HTTP Headers  Unless otherwise specified, all API endpoints expect request body data to be in [JSON](https://en
 * .wikipedia.org/wiki/JSON), and the response body data is returned as JSON.  The following HTTP request headers may be
 * required, depending on the operation and endpoint being invoked:  Header | Definition | Example | -----|-----|-----|
 * **Authorization** | Required for all endpoints, except for `POST /token`. The access token. | Bearer
 * JKlMNOpQ12RStUVwxYZAbcde3F5g6hijklM789 **Content-Type** | Required for POST and PUT requests. Defines the structure for the
 * response. | application/json **Assume-User** | Optional. Allows an admin to act on behalf of, or impersonate, the user to
 * make API calls. The email address used to identify the user must be URI-encoded. | jane.doe%40smartsheet.com  ## HTTP Verbs
 *  Call the API using the following [standard HTTP methods](https://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html):  * GET
 * (to retrieve an object or list multiple objects in a specific category) * POST (to create) * PUT (to modify) * DELETE  ##
 * HTTP Status Codes  Smartsheet uses a combination of [HTTP status codes](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10
 * .html) and custom error codes with a descriptive message in JSON-formatted [Error objects]
 * (/tag/commonObjects#section/Error-Object) to give you a more complete picture of what has happened with your request.
 * HTTP<br/>status code | Meaning | To Retry or Not to Retry? | -----|-----|-----| 2xx | Request was successful
 * .<br/><br/>Example: 200 Success | -- 4xx | A problem with request prevented it from executing successfully. | Never
 * automatically retry the request.<br/><br/>If the error code indicates a problem that can be fixed, fix the problem and retry
 *  the request. 5xx | The request was properly formatted, but the operation failed on Smartsheet's end. | In some scenarios,
 * requests should be automatically retried using [exponential backoff](https://en.wikipedia.org/wiki/Exponential_backoff).
 * For example, doing a `GET` on a non-existent sheet at `https://api.smartsheet.com/2.0/sheets/123456` results in an HTTP
 * status code of **404**, indicating the resource was not found.  ```json {   \"errorCode\": 1006,   \"message\": \"Not
 * Found\" } ``` <br/>  Some errors may contain a **detail** attribute set to an object with additional error details that may
 * be useful in programmatically handling the error.  If so, it is noted in the specific API operation for which the error may
 * occur.  NOTE: Smartsheet has custom error codes to help you troubleshoot issues. See [Error Codes](/#section/Error-Codes).
 * ## Limitations  While Smartsheet is improving capacity frequently, there are some hard limits that might be helpful to know:
 *   * When adding or updating rows, limit your request to 500 rows at a time. * A sheet cannot exceed a total of 500,000 cells
 * . To determine how close you are to the 500,000 cell limit, multiply the number of columns in your sheet by the number of
 * rows. For example, a sheet with 20,000 rows can only have 25 or fewer columns, and a sheet with 400 columns can only have 1,
 * 250 or fewer rows. * Any one sheet can have up to 500,000 inbound cell links. (Smartsheet Gov has an inbound cell link limit
 *  of 100,000.) * No cell can contain more than 4,000 characters. * When using a GET /reports/{reportId} call with paging, the
 *  default is 100 rows. If you need larger sets of data from your report, this operation can return a maximum of 10,000 rows
 * per request. * Reports are limited to 50,000 rows. * When sharing emails, you can send 1000 per API call.  The following
 * features aren't yet supported for sheets with more than 5000 rows, or more than 200 columns:  * Bridge by Smartsheet * Quip
 * connector * Tableau connector * PowerBI connector * Zapier connector * Google Docs Merge connector * Google Forms sync
 * connector * LiveData connector * Calendar App  ## Looping  Looping is an expected action when working with the Smartsheet
 * API. But when do you run the For Loop? Do you loop through multiple endpoint calls or is there a more efficient way?  For
 * instance, if you're looking for multiple values on a given sheet, fetch the entire sheet. Do the searching on the return
 * data in one single For Loop rather than calling the search endpoint multiple times with different values.  ## Multi-contact
 * or Multi-picklist: Working with Complex Objects  New column types, such as MULTI_CONTACT_LIST and MULTI_PICKLIST, offer more
 *  complex ways to work with columns. Smartsheet has provided a backwards compatible way for these payloads to work with your
 * existing integrations while also giving you a way to query for their content.  With either column type, there are two ways
 * of receiving the response:  * If you do nothing to your code, column types will display as TEXT_NUMBER * If you use the
 * level query parameter as detailed below, column types will display as MULTI_CONTACT_LIST or MULTI_PICKLIST and return a
 * complex object (aka a non-primitive type)  Smartsheet uses two indicators to help you discover changes in the return data
 * for your API calls:  * level: a query parameter that is incremented when a new feature or column type is added * version:
 * each element in the Columns array is set to one of the following values:  Text | Multi-contact | Multi-picklist
 * -----|-----|-----| 0 | 1 | 2  You must use the level query parameter, for example **level=2**, to return a complex object
 * with the new column type. Without the query parameter, the response will be backwards-compatible, that is a string. The
 * <b>include=objectValue</b> query parameter is necessary to see the return as a complex value, such as actual email addresses
 *  rather than display names.  Use the highest possible level for each endpoint, as in the following table:  Endpoint category
 *  | Level -----|-----| Dashboards | 4 Reports | 3 Sheets | 2  ## Object Details vs List Summaries  Many of the List All
 * commands, for example, `GET /sheets`, return only an abbreviated object for each object returned. For full details, read a
 * single item, such as `GET /sheets/{sheetId}`. In many cases, you can refine the exact object properties to be returned by
 * using **include** and **exclude** query parameters.  The JSON representation of the objects returned from the List All REST
 * endpoints will only include a subset of the properties documented here. However, the objects returned from the Java and C#
 * SDKs will represent the omitted properties with NULLs.  ## Query Strings  Many API calls can be modified by including one or
 *  more of these common query strings:  Query Parameter | Type | Description | More Info | -----|-----|-----|-----|
 * **accessApiLevel** | Number | Allows COMMENTER access for inputs and return values. For backwards-compatibility, VIEWER is
 * the default. For example, to see whether a user has COMMENTER access for a sheet, use accessApiLevel=1. |
 * **allowPartialSuccess** | Boolean | If **true**, allows bulk operations to process even if one or more operations are
 * invalid for some reason, for example, **allowPartialSuccess=true**. | [Bulk Operations]
 * (/#section/Work-at-Scale/Bulk-Operations) **include** or **exclude** | String | When applicable for a specific object,
 * various **include** or **exclude** parameters are available, for example, **include=format**. | Object reference or
 * [Formatting](/#section/API-Basics/Formatting) **includeAll** | Boolean | If **true**, includes all results, for example,
 * **includeAll=true**. | [Paging](/#section/Work-at-Scale/Paging) **level** | Number | Use for complex objects. | [Working
 * with Complex Objects](/#section/API-Basics/Multi-contact-or-Multi-picklist:-Working-with-Complex-Objects) **numericDates** |
 *  Boolean | If **true**, allows you to input or receive dates in numeric format, for example, **numericDates=true**. | [Dates
 *  and Times](/#section/API-Basics/Dates-and-Times) **page** | String | Specifies which page to return, for example,
 * **page=4**. | [Paging](/#section/Work-at-Scale/Paging) **pageSize** | Number | Specifies the maximum number of items to
 * return per page, for example, **pageSize=25**. | [Paging](/#section/Work-at-Scale/Paging)  NOTE: Query strings are case
 * sensitive. If you do not see the expected response, confirm that your query is formatted correctly.  ##
 * Sheets/Columns/Rows/Cells  Sheets have a core hierarchy of Sheet > Column > Row > Cell. The strict hierarchy tells you how
 * to associate the objectId with the object. For example, your return might include a Sheet object with many Row objects. Each
 *  object has an objectId. The strict hierarchy helps you map the objectId to the sheet or specific row.  Cells are a little
 * different. You identify a cell by its location in the grid, so you need both a column Id and a row Id to pinpoint a specific
 *  cell. The following table defines these terms and points you to places in this documentation where you can find more
 * information:  UI Element | Description | More Info -----|-----|-----| sheet | A sheet can exist in a user's [Home]
 * (/#tag/homeDescription) folder, in a [folder](/#tag/foldersDescription), or in a [workspace](/#tag/workspacesDescription/).
 * It is comprised of columns, rows, and cells, and may optionally contain attachments or discussions. | [Sheet object]
 * (/tag/sheetsObjects#section/Sheet-Object) column | A Column object *defines* the type of the column, but does not actually
 * contain cells. The Column Id identifies the cells in a row. | [Column object](/#tag/columnsObjects#section/Column-Object),
 * [Column types](/#tag/columnsRelated#section/Column-Types) row | A row is a component of a sheet or report. Each row is
 * composed of a collection of cells, and may optionally contain attachments or discussions. | [Row object]
 * (/tag/rowsObjects#section/Row-Object) cell | A cell is a location within a sheet that may contain a value. A collection of
 * cells comprises each row in a sheet. | [Cell object](/tag/cellsObjects#section/Cell-Object), [Cell reference]
 * (/tag/cellsRelated#section/Cell-Reference)  ## Sheet Responses  Many Smartsheet API operations handle sheets, rows, columns,
 *  and cells. Each is identified by an Id and it is important to understand the relationship between these objects. Typically
 * you loop through the columns to determine the Id of the columns you are interested in. Then you loop through the rows and
 * contained cells to find actual values. The annotated sample response below illustrates these concepts by calling a very
 * simple sheet called \"Employee Roster\".  <img src=\"/images/Employee_roster.png\" alt=\"Basic sheet with 2 rows and 2
 * columns\" />  Before you begin, you should already have an access token, which you used in the exercise above. Use the same
 * access token for this walkthrough.  **Step 1:** The first thing you must have is a sheetId. To find a sheetId through the
 * UI, with the sheet open, click \"Sheet Actions\" in the left toolbar and select \"Properties\". **NOTE:** use [List Sheets]
 * (/tag/sheets#operation/list-sheets) if you want to do this programmatically.  <img src=\"/images/sheet-properties.png\"
 * alt=\"Image of the Sheet Properties window\" />  **Step 2:** Copy the sheetId into the API call, `GET /sheets`, as below:
 * `curl -X GET -H \"Authorization: Bearer JKlMNOpQ12RStUVwxYZAbcde3F5g6hijklM789\" \"https://api.smartsheet.com/2
 * .0/sheets/6141831453927300\"`  **Step 3:** The sample request and response are displayed below. **NOTE:** while JSON doesn't
 *  have a comment feature, this sample uses comments to help you identify the objects in the response.  ```json {   \"id\":
 * 6141831453927300,    // Sheet Id   \"name\": \"My first sheet\",   \"columns\": [{              // Each Column object
 * associates column Id                              // to title and defines the                              // column details
 *           \"id\": 2517104256673668,   // Column Id         \"index\": 0,         \"title\": \"Name\",         \"type\":
 * \"TEXT_NUMBER\",         \"primary\": true,         \"width\": 150       },       {         \"id\": 7020703884044164,   //
 * Next column Id         \"index\": 1,         \"title\": \"EmployeeId\",         \"type\": \"TEXT_NUMBER\",
 * \"width\": 150       }   ],   \"rows\": [{                       // A Row object          \"id\": 564480076736388,    // Row
 *  Id          \"rowNumber\": 1,          \"expanded\": true,          \"createdAt\": \"2017-05-12T16:52:38Z\",
 * \"modifiedAt\": \"2017-05-22T20:40:14Z\",          \"cells\": [{  // Each row contains an array of cells,
 *     // which have the actual content                       \"columnId\": 2517104256673668,                      // The
 * column Id can be interpreted by                      // looking at the array of column                      // definitions
 * above. That tells you                      // this is the \"Name\" column                       \"value\": \"John Doe\",
 *                   \"displayValue\": \"John Doe\"                    },                    {
 * \"columnId\": 7020703884044164,                      \"value\": 12345,            // Actual cell value
 * \"displayValue\": \"12,345\"                      // How the cell value is displayed in the UI                    }
 *  ]},        {          \"id\": 5068079704106884,          \"rowNumber\": 2,          \"siblingId\": 564480076736388,
 *   \"expanded\": true,          \"createdAt\": \"2017-05-12T16:52:38Z\",          \"modifiedAt\": \"2017-05-22T20:40:14Z\",
 *         \"cells\": [{                      \"columnId\": 2517104256673668,                      \"value\": \"Jane Roe\",
 *                   \"displayValue\": \"Jane Roe\"                    },                    {
 * \"columnId\": 7020703884044164,                      \"value\": 67890,                      \"displayValue\": \"67890\"
 *                }          ]        }   ] } ``` <br/>  This core hierarchy of Sheet > Column > Row > Cell is essential to
 * working with the Smartsheet API. As your user's sheets grow in complexity, the responses do too. This walkthrough has given
 * you some navigational aid in finding the right value to plug into your API calls. Use the API Reference and the example
 * language tabs to learn more.  ## Troubleshooting  Should you encounter issues with the Smartsheet API while building an
 * integration using a particular programming language, for example C#, Java, Node.js, Python, or Ruby, keep the following
 * troubleshooting techniques in mind.  Try executing the same API Request using a tool like [cURL](https://curl.haxx.se/) or
 * [Postman](https://www.postman.com/). By taking your code out of the equation, you can isolate troubleshooting to the raw
 * Request / Response.  * If you receive a similar error when you execute the Request using cURL or Postman, this suggests an
 * issue with the Request format or contents. Once you have the Request working in cURL or Postman, update your code
 * accordingly. * If you can execute the Request successfully using cURL or Postman, but not via your code, this suggests that
 * the Request your code is sending is somehow different than what you intend. Compare the (successful) Request from cURL or
 * Postman with the (unsuccessful) Request that your code generates. (See step #2 below.)  Examine the Request that your code
 * is sending (including the HTTP verb, URI, headers, and Request body) and the Response that it's receiving back from
 * Smartsheet (including the HTTP status code, headers, and response body).  * If you've implemented Request / Response logging
 *  in your application, inspect the full trace of Request and Response in the log file. Compare the Request that your
 * application is logging with the (successful) Request from cURL or Postman, and update your code to correct any discrepancies
 * . * Alternatively, you may choose to use a tool like [Fiddler](https://www.telerik.com/fiddler) or [Charles HTTP Proxy]
 * (https://www.charlesproxy.com/) to inspect the full trace of Request and Response as it goes across the wire. Compare the
 * Request trace that your application generates with the (successful) Request from cURL or Postman, and update your code to
 * correct any discrepancies.  Check for capitalization errors. **NOTE:** URL endpoints are all lower case, while object
 * properties and query parameters are camelCase.  ## User Impersonation  Allows an admin to act on behalf of, or impersonate,
 * the user to make API calls. You might do this to troubleshoot a user problem or cover for vacations and sick time. As with
 * cURL, the email address used to identify the user must be URI-encoded.  An admin cannot impersonate another admin.  NOTE:
 * You must manually generate a token to assume user.  **cURL example** ```json curl https://api.smartsheet.com/2.0/sheets \\
 * -H \"Authorization: Bearer ll352u9jujauoqz4gstvsae05\" \\ -H \"Assume-User: jane.doe%40smartsheet.com\" \\ ```  **C#
 * example** ```csharp SmartsheetClient smartsheet = new SmartsheetBuilder()   .SetAccessToken(accessToken)   .SetAssumedUser
 * (\"jane.doe@smartsheet.com\")   .Build(); ```  **Java example** ```java smartsheet.setAssumedUser(\"jane.doe@smartsheet
 * .com\"); ```  **Node.js example** ```javascript // Set options var options = {   assumeUser: \"jane.doe@smartsheet.com\"
 * };  // List Sheets smartsheet.sheets.listSheets(options)   .then(function(sheetList) {     console.log(sheetList);   })
 * .catch(function(error) {     console.log(error);   }); ```  **Python example** ```python smartsheet_client.assume_user
 * (\"jane.doe@smartsheet.com\") ```  **Ruby example** ```ruby smartsheet.sheets.list(   header_override: {:'Assume-User' =>
 * CGI::escape('jane.doe@smartsheet.com')} ) ```  ## Versioning and Changes  Smartsheet will add new functionality and bug
 * fixes to the API over time. Make sure that your code can handle new JSON properties gracefully. Also, make sure your code
 * does not depend on the order in which JSON objects are returned, unless it is explicitly stated in this documentation.  When
 *  there is new functionality that is not compatible with existing code, say in the case of a new concept, Smartsheet
 * increments the level to indicate the new feature can be ignored until you are ready to implement the code to work with the
 * new level.  See also: [Multi-contact or Multi-picklist: Working with Complex Objects]
 * (/#section/API-Basics/Multi-contact-or-Multi-picklist:-Working-with-Complex-Objects)  # Error Codes  For an explanation of
 * the logic behind Smartsheet error codes and error handling, see the [HTTP and REST](/#section/API-Basics/HTTP-and-REST)
 * portion of the Introduction.  ## 400-Level Error Codes  400-level error codes generally indicate that there is something you
 *  should fix or add to your request before you try the request again.  HTTP status code | Smartsheet errorCode | Smartsheet
 * message -----|-----|-----| 401 | 1001 | An Access Token is required. 401 | 1002 | Your Access Token is invalid. 401 | 1003 |
 *  Your Access Token has expired. 403 | 1004 | You are not authorized to perform this action. 401 | 1005 | Single Sign-On is
 * required for this account. 404 | 1006 | Not Found. 404 | 1007 | Version not supported. 400 | 1008 | Unable to parse request.
 *  The following error occurred: {0} 400 | 1009 | A required parameter is missing from your request: {0}. 405 | 1010 | HTTP
 * Method not supported. 400 | 1011 | A required header was missing or invalid: {0} 400 | 1012 | A required object attribute is
 *  missing from your request: {0}. 403 | 1013 | The operation you are attempting to perform is not supported by your plan. 403
 *  | 1014 | There are no licenses available on your account. 403 | 1015 | The user exists in another account. The user must be
 *  removed from that account before they can be added to yours. 403 | 1016 | The user is already a member of your account. 403
 *  | 1017 | The user already has a paid account. The user must cancel that account before they can be added to yours. 400 |
 * 1018 | The value {0} was not valid for the parameter {1}. 400 | 1019 | Cannot transfer to the user specified. User not found
 * . 404 | 1020 | User not found. 403 | 1021 | Cannot transfer to the user specified. They are not a member of your account.
 * 403 | 1022 | Cannot delete the user specified. They are not a member of your account. 400 | 1023 | The sheet specified is
 * shared at the Workspace level. 400 | 1024 | The HTTP request body is required for this Method. 400 | 1025 | The share
 * already exists. 403 | 1026 | Transferring ownership is not currently supported. 404 | 1027 | Share not found. 400 | 1028 |
 * You cannot edit the share of the owner. 400 | 1029 | The parameter in the URI does not match the object in the request body.
 *  401 | 1030 | You are unable to assume the user specified. 400 | 1031 | The value {0} was not valid for the attribute {1}.
 * 400 | 1032 | The attribute(s) {0} are not allowed for this operation. 404 | 1033 | The template was not found. 400 | 1034 |
 * Invalid Row Id. 400 | 1035 | Deprecated. 400 | 1036 | The columnId {0} is invalid. 400 | 1037 | The columnId {0} is included
 *  more than once in a single row. 400 | 1038 | Invalid Cell value. Must be numeric or a string. 403 | 1039 | Cannot edit a
 * locked column {0}. 400 | 1040 | Cannot edit your own share. 400 | 1041 | The value for {0} must be {1} characters in length
 * or less, but was {2}. 400 | 1042 | The value for cell in column {0}, {1}, did not conform to the strict requirements for
 * type {2}. 404 | 1043 | The row number you requested is blank and cannot be retrieved. 400 | 1044 | Assume-User header is
 * required for your Access Token. 403 | 1045 | The resource specified is read-only. 400 | 1046 | Cells containing system
 * values cannot be inserted or updated through the API, columnId : {0}. 403 | 1047 | You cannot remove yourself from the
 * account through the API. 403 | 1048 | The user specified has declined the invitation to join your organization. You cannot
 * modify declined invitations. 403 | 1049 | You cannot remove admin permissions from yourself through the API. 403 | 1050 |
 * You cannot edit a locked row. 400 | 1051 | Attachments of type FILE cannot be created using JSON. 406 | 1052 | Invalid
 * Accept header. Media type not supported. 400 | 1053 | Unknown Paper size: {0}. 400 | 1054 | The new sheet requires either a
 * fromId or columns. 400 | 1055 | One and only one column must be primary. 400 | 1056 | Column titles must be unique. 400 |
 * 1057 | Primary columns must be of type TEXT_NUMBER. 400 | 1058 | Column type of {1} does not support symbol of type {0}. 400
 *  | 1059 | Column options are not allowed when a symbol is specified. 400 | 1060 | Column options are not allowed for column
 * type {0}. 400 | 1061 | Max count exceeded for field {0}. 400 | 1062 | Invalid row location. 400 | 1063 | Invalid parentId:
 * {0}. 400 | 1064 | Invalid siblingId: {0}. 400 | 1065 | The column specified cannot be deleted. 400 | 1066 | You can only
 * share to {0} users at a time. 401 | 1067 | Invalid client_id 400 | 1068 | Unsupported grant type. 400 | 1069 | Invalid
 * Request. The authorization_code has expired. 400 | 1070 | Invalid Request. Required parameter is missing: {0}. 400 | 1071 |
 * Invalid Grant. The authorization code or refresh token provided was invalid. 400 | 1072 | Invalid hash value. The hash
 * provided did not match the expected value. 400 | 1073 | The redirect_uri did not match the expected value. 400 | 1074 | You
 * are trying to upload a file of {0}, but the API currently only supports {1}. 400 | 1075 | The Content-Size provided did not
 * match the file uploaded. This may be due to network issues or because the wrong Content-Size was specified. 403 | 1076 | The
 *  user has created sheets and must be added as a licensed user. 400 | 1077 | Duplicate system column type: {0}. 400 | 1078 |
 * System column type {0} not supported for {1} {2}. 400 | 1079 | Column type {0} is not supported for system column type {1}.
 * 400 | 1080 | End Dates on dependency-enabled sheets cannot be created/updated. Please update either the Duration or Start
 * Date column. 403 | 1081 | You cannot delete or update another user's discussions, comments, or comment attachments. 400 |
 * 1082 | You cannot add options to the given column {0} because it is not a PICKLIST. 400 | 1083 | Auto number formatting
 * cannot be added to a column {0}. 400 | 1084 | The auto number format is invalid. 400 | 1085 | To change this column's type
 * you must first disable Dependencies for this sheet. 400 | 1086 | Google was not able to verify your access. 400 | 1087 | The
 *  column specified is used in a conditional formatting rule, so the column cannot be deleted and its type cannot be changed.
 * 400 | 1088 | Invalid length for concatenated auto number format. Concatenated format is {0}, with a length of {1}. Must be
 * less than or equal to 40. 400 | 1089 | The type specified is only used with System Columns. 400 | 1090 | Column.type is
 * required when changing symbol, systemColumnType or options. 400 | 1091 | Invalid Content-Type: {0}. 403 | 1092 | You cannot
 * delete this row. Either it or one or more of its children are locked. 400 | 1093 | Apple verification not available. 400 |
 * 1094 | Can't set password without licensed account. 400 | 1095 | The Excel file is invalid/corrupt. This may be due to an
 * invalid file extension, an outdated Excel format, or an invalid Content-Length. 403 | 1096 | This Apple payment receipt has
 * already been applied to a user's payment profile. 403 | 1097 | A user must be a licensed sheet creator to be a resource
 * viewer. 400 | 1098 | To delete this column you must first disable Dependencies for this sheet. 400 | 1099 | To delete this
 * column you must first disable Resource Management for this sheet. 400 | 1100 | Uploading new versions of a discussion
 * comment attachment is not supported. 400 | 1101 | Uploading new versions of non-FILE type attachments is not supported. 403
 * | 1102 | A user must be a licensed sheet creator to be a group administrator. 400 | 1103 | A group with the same name
 * already exists. 403 | 1104 | You must be a group administrator to create a group. 400 | 1105 | The operation failed because
 * one or more group members were not members of your account: {0}. 404 | 1106 | Group not found. 400 | 1107 | User specified
 * in transferGroupsTo must be a group admin. 400 | 1108 | transferGroupsTo must be provided because user being deleted owns
 * one or more groups. 400 | 1109 | Only one of cell.hyperlink or cell.linkInFromCell may be non-null. 400 | 1110 | cell.value
 * must be null if cell.linkInFromCell is non-null. 400 | 1111 | Only one of cell.hyperlink.sheetId and cell.hyperlink.reportId
 *  may be non-null. 400 | 1112 | cell.hyperlink.url must be null for sheet or report hyperlinks. 400 | 1113 | cell.value must
 * be a string when the cell is a hyperlink. 404 | 1114 | Invalid sheetId or reportId: {0}. 400 | 1115 | Row must contain
 * either cell link updates or row/cell value updates; mixing of both update types in one API call is not supported. 400 | 1116
 *  | You cannot link a cell to its own sheet. 400 | 1117 | One of the following cell.hyperlink fields must be non-null: url,
 * sheetId, or reportId. 400 | 1118 | You cannot set the value of a Gantt allocation column (id {0}) in a row that has child
 * rows. 400 | 1120 | Too many sheets to copy.<br/>**NOTE**: includes a \"detail\" object containing \"maxSheetCount\" which
 * represents the server-side limit on the number of sheets allowed in a single folder/workspace copy operation. 400 | 1121 |
 * transferTo must be provided because user being deleted owns one or more groups. 405 | 1122 | Requested URL does not support
 * this method: {0}. 400 | 1123 | Specifying multiple row locations is not yet supported. Each row must use the same row
 * location attribute and value (toBottom, toTop, parentId, siblingId, above). 415 | 1124 | Invalid Content-Type header. Media
 * type not supported. 400 | 1125 | Each part in a multipart payload must have a name. 400 | 1126 | Multipart payload contained
 *  duplicate part names: {0}. 400 | 1127 | Required multipart part was missing: '{0}' 400 | 1128 | Multipart upload size limit
 *  exceeded. 400 | 1129 | The resource you tried to create already exists. 400 | 1130 | One of cell.value or objectValue may
 * be set, but not both. 400 | 1131 | cell.{0} for column {1} was of the wrong object type. Allowed types: {2}. 400 | 1132 |
 * The token provided has previously been revoked. 400 | 1133 | Column titles are not unique among input columns. 400 | 1134 |
 * Duplicate system column type among input columns. 400 | 1135 | Input column index {0} is different from the first input
 * column index {1}. 400 | 1136 | Cannot copy or move row(s) within the same sheet. 400 | 1137 | Input collection contains
 * multiple instances of the same element. 403 | 1138 | The user is not eligible for a trial organization. 403 | 1139 | The
 * user is an admin in another organization. Add 'allowInviteAccountAdmin=true' to the query string to invite their entire
 * organization. 403 | 1140 | The user must be added as a licensed user. 403 | 1141 | Inviting users from an enterprise
 * organization is not supported. 400 | 1142 | Column type {0} is reserved for project sheets and may not be manually set on a
 * column. 400 | 1143 | To set {0}, you must first enable dependencies on the sheet. 400 | 1144 | The user owns one or more
 * groups and must be added as a Group Admin. 400 | 1145 | Multipart upload request was invalid. Please check your request
 * headers and payload. 400 | 1146 | Unsupported operation: {0}. 400 | 1147 | Multipart request contained an invalid part name:
 *  '{0}' 400 | 1148 | Numeric cell values must be between {0} and {1}. 400 | 1149 | Not configured for Gantt. 400 | 1150 |
 * Invalide operation for shared. 404 | 1151 | Scope object not found. 400 | 1152 | URL must have HTTPS. 403 | 1153 | Webhook
 * app revoked. 403 | 1154 | Webhook disabled by Smartsheet. 400 | 1155 | You cannot set the '{0}' attribute for a cell in a
 * 'Project Settings' column of a dependency-enabled sheet. 400 | 1156 | Invalid email. 400 | 1157 | This address is already
 * associated with another Smartsheet account, so it cannot be added as an alternate address for this account. 400 | 1158 |
 * This address has not been confirmed yet, so it can't be set as the primary email address. 400 | 1159 | The specified email
 * address ({0}) is an alternate email address for a user with a primary email address of {1}. 400 | 1160 | Invalid bulk
 * request. See detail for more information. 400 | 1161 | Cannot set altText for a cell that does not contain an image: row
 * {0}, column {1}. 400 | 1162 | A formula must always start with an equal sign (=). 400 | 1163 | If cell.image is non-null
 * then value, objectValue, hyperlink, and linkInFromCell must all be null. 400 | 1164 | Cannot add image to cell because this
 * feature has been disabled by the org administrator. 400 | 1165 | Cannot add image to cell with alt text larger than 100
 * characters. 400 | 1166 | You cannot share Sights as an Editor. 400 | 1167 | The resource you are attempting to access has
 * expired. 400 | 1168 | objectValue's type is not valid for virtualColumnId {0}. 400 | 1169 | All virtual columns in a group
 * must be of the same type. 403 | 1173 | You must be in a Team/Enterprise account to specify a {0} value of '{1}'. 403 | 1174
 * | The value for {0} is invalid because this publish option is configured to be restricted to users in this account. 403 |
 * 1175 | One or more publish options which you attempted to enable are disabled for this account. 400 | 1176 | Array attribute
 * (s) may not contain null elements: {0}. 400 | 1177 | Arrays may not contain null elements. 400 | 1178 | The following
 * combination of attributes is not allowed for this operation: {0}. 400 | 1179 | The schedule specified is invalid because
 * endAt is earlier than the next send date. 403 | 1180 | We are unable to process this request because the email has been
 * associated with a different Smartsheet account. 403 | 1181 | Only admins can edit shared filters. 400 | 1182 | The specified
 *  sheet filter {0} does not exist for sheet {1}. 400 | 1183 | Sheet filters must define at least one detail entry. 400 | 1184
 *  | Sheet {0} already has a filter named '{1}' of type {2}. 400 | 1185 | Cannot create a child of a parent row that has no
 * data in it. 403 | 1186 | User's primary email address must be a validated domain. 403 | 1187 | User's alternate address
 * selected to be made primary must be a validated domain. 403 | 1188 | The account status of the user specified is not active.
 *  400 | 1189 | Only Enterprise or Team accounts with security controls are able to change primary emails. 400 | 1190 | Only
 * Enterprise or Team accounts with special permission granted are able to change primary emails. 400 | 1191 | Summary field
 * attribute is the wrong type. 403 | 1192 | Can't edit locked summary field. 400 | 1193 | Duplicate summary field title. 400 |
 *  1194 | Invalid summary field type for options. 400 | 1195 | Duplicate summary field index. 400 | 1196 | Summary field type
 * required for change. 400 | 1197 | Summary field options not allowed for symbols. 400 | 1198 | Unsupported symbol for summary
 *  field type. 400 | 1199 | Maximum number summary fields exceeded. 400 | 1200 | Duplicate summary field Id. 400 | 1201 |
 * Summary field image only. 400 | 1202 | Summary field hyperlink value must be string. 400 | 1203 | Can't link summary field
 * to same sheet. 404 | 1204 | Invalid sheet report dashboard Id. 400 | 1205 | Can't set summary field alt text. 400 | 1206 |
 * Unsupported summary field format type. 400 | 1207 | Attribute value is empty. 400 | 1208 | Duplicate summary field title in
 * request. 400 | 1209 | Can't set attribute on column type. 400 | 1210 | Column validation is not supported for column type
 * '{0}'. 400 | 1211 | Not authorized. 403 | 1212 | You must be a sheet admin to override validation. 400 | 1213 | Deprecated.
 * 400 | 1214 | Invalid notification level '{0}'. 400 | 1215 | Notification not supported by level. 400 | 1216 | Notification
 * rule not found. 400 | 1217 | Exceeds allowed max date. 400 | 1218 | The attributes recipientColumnIds, recipients and
 * notifyAllSharedUsers are mutually exclusive. Only one may be set, not all. 400 | 1218 | The attributes includeAllColumnIds
 * and includedColumnIds are mutually exclusive. Only one may be set not all. 400 | 1219 | The attributes {0} and {1} are
 * mutually exclusive. Only one may be set. 400 | 1220 | Automation action type cannot be changed. 400 | 1221 | The value {0}
 * is not valid for the attribute action.recipientColumnIds. Only Contact List columns may be used. 400 | 1222 | Invalid
 * attribute for operation. 400 | 1223 | Sort sheet with locked row. 400 | 1224 | Invalid parameter value. 400 | 1225 | Problem
 *  processing row header. 400 | 1226 | Personal workspace not found. 400 | 1227 | The query parameters '{0}' and '{1}' are
 * mutually exclusive. Only one may be set. 400 | 1228 | You must specify one of the following query parameters: '{0}' or '{1}'
 * . 400 | 1229 | The value '{0}' was not valid for the parameter '{1}'. The value must be between '{2}' and '{3}'. 400 | 1230
 * | Duplicate bot type. 400 | 1231 | Invalid bot type. 403 | 1232 | Bot not enabled. 400 | 1233 | Required form field missing.
 *  400 | 1234 | Form data empty. 400 | 1235 | Value not supported on column. 400 | 1236 | OAuth missing client auth. 400 |
 * 1237 | OAuth redundant client auth. 400 | 1238 | OAuth invalid secret. 400 | 1239 | Client column version mismatch. 400 |
 * 1240 | Multi-contact list limit. 400 | 1241 | Invalid mulcit-contact name. 400 | 1242 | Too many display column Ids. 400 |
 * 1243 | Invalid view by Id. 400 | 1244 | Uncardable view by Id. 400 | 1245 | Invalid display column Id. 400 | 1246 |
 * Displaying primary column in card view. 400 | 1247 | Card view level below minimum. 400 | 1248 | Invalid subtask column Id.
 * 400 | 1249 | Uncheckable subtask column Id. 400 | 1250 | Card view not configured. 400 | 1251 | Duplicate display column Id.
 *  400 | 1252 | Inconsistent lane values. 400 | 1253 | Move card adjacent to self. 400 | 1254 | Invalid view by Id for card
 * operation. 400 | 1255 | Attachment type '{0}' is not supported. 400 | 1256 | Individual accounts disabled. 400 | 1257 | Form
 *  hyperlink is not URL. 400 | 1258 | Cannot move folder under descendant. 400 | 1259 | Uncardable view by Id for card
 * operation. 400 | 1260 | Cannot delete last visible column. 400 | 1261 | Uncardable column for editing lanes. 400 | 1262 |
 * Invalid card lane name. 400 | 1263 | Duplicate card lane name. 400 | 1264 | Column fields not allowed for editing lanes. 400
 *  | 1265 | Card view was never configured for lanes. 400 | 1266 | This rule is not accessible through the API. Only
 * single-action notifications, approval requests, or update requests qualify. 400 | 1267 | Web content widget disabled. 400 |
 * 1268 | Dashboard web content widget custom domain URL disabled. 403 | 1269 | Removing group admin who owns groups. 403 |
 * 1270 | Forbidden impersonate object owner. 403 | 1271 | Forbidden impersonate user. 400 | 1272 | Invalid impersonate header
 * value. 400 | 1273 | Both impersonate and assume user provided. 400 | 1274 | Impersonate object owner not supported. 403 |
 * 1275 | Impersonate feature not enabled. 400 | 1276 | Column type not supported. 400 | 1277 | Multi-picklist invalid size.
 * 400 | 1279 | Proofing setting error. 400 | 1280 | Proofing duplicate record. 400 | 1281 | Proofing invalid file extension.
 * 400 | 1282 | Invalid JSON. 400 | 1283 | Unrecognized JSON property. 400 | 1284 | Dashboard level below minimum. 400 | 1286 |
 *  Proofing service row move invalid. 403 | 1287 | Publish disabled by sheet sys admin. 403 | 1288 | Sheet disabled by admin.
 * 400 | 1289 | Proofing cannot disable with proofs. 400 | 1290 | The following users cannot be added to this group: {0}. 400 |
 *  1291 | Refreshing mobile access tokens is not yet enabled. 400 | 1292 | The grant_type is inconsistent with the API request
 *  type for authentication. 404 | 1293 | Subscope contains invalid ids. 400 | 1294 | A proof cannot be retrieved. 400 | 1295 |
 *  Proof requests cannot be created on an empty proof. 400 | 1296 | Uploading new versions of a proof attachment is not
 * supported. 403 | 1297 | Upgrade to a business plan or higher to enable proofing. 400 | 1298 | Proof version can only be
 * created on an original version proof id. In addition, the proof must not be marked as complete and the current version must
 * not be empty. 400 | 1299 | Proof version cannot be deleted. 400 | 1300 | Error creating proof request or sending the
 * notification. 403 | 1301 | No permissions for column formulas. 400 | 1302 | Cannot edit column formula cells. 400 | 1303 |
 * Unsupported column type for column formulas. 400 | 1304 | Maximum number of column formulas exceeded. 400 | 1305 | Cannot
 * set column formula on project column. 400 | 1306 | An attachment can only be uploaded to a current version proof. In
 * addition, the proof must not be marked as complete. 400 | 1307 | A PDF cannot be included in a proof with multiple files.
 * Only JPG, JPEG, PNG, GIF, and BMP files are supported in a proof with multiple files. 400 | 1308 | Multi-image proofing is
 * not enabled on this sheet. Please contact the sheet owner for assistance. 400 | 1309 | An attachment can only be deleted
 * from a current version proof. In addition, the proof must not be marked as complete. 400 | 1310 | Column formula syntax not
 * supported. 404 | 1311 | Invalid Item ID provided. 400 | 1312 | Invalid Item type provided. 400 | 1313 | Since
 * 'includeMessageOnly' is set to true, 'message' must not be blank. 400 | 1314 | Plan type cannot use column formulas. 400 |
 * 1315 | Proof status can only be updated on a current version proof. In addition, the proof cannot be empty. 400 | 1316 | A
 * discussion, comment, or comment attachment can only be created or edited on a current version proof. In addition, the proof
 * must not be marked as complete. 403 | 1317 | This action can't be performed right now, as this report is using capabilities
 * that haven't been released to the public yet. You'll be able to take this action later once the capabilities are released.
 * Sorry for the inconvenience! 400 | 1318 | Cannot delete baseline columns. 400 | 1319 | Cannot update values in baseline
 * columns. 400 | 1320 | Cannot change a baseline column type. 400 | 1321 | Cannot put a column formula in a baseline column.
 * 400 | 1322 | Cannot remove column formula from baseline variance column. 400 | 1323 | Invalid column type for baseline
 * column. 400 | 1324 | Unsupported baseline type for column. 400 | 1326 | Baselines API feature not enabled for sheet. 400 |
 * 1327 | Proof request cannot be deleted. 400 | 1328 | Baselines requires existing start and end date columns. 400 | 1329 |
 * Invalid characters in first or last name. 400 | 1330 | You have reached the limit of {0} alternate email addresses. To add
 * {1}, first remove an existing alternate email address that you no longer need. 403 | 1331 | Datatables feature is not
 * enabled. 400 | 1332 | Images not allowed in baseline columns. 403 | 1334 | Shared publish option is not enabled. 403 | 1335
 * | Cannot deactivate the user specified. They are not a member of your account. 403 | 1336 | You cannot deactivate yourself
 * from the account through the API. 403 | 1337 | You must upgrade your organization to invite this user. Pro plans are only
 * allowed to invite other pro plan users. 400 | 1338 | Missing file name in request. 400 | 1339 | Missing file size in request
 * . 400 | 1340 | Attachment ticket is invalid, is not found or access has been disabled for this account. Create a valid
 * attachment ticket before finalizing. 400 | 1341 | File uploaded to s3 is invalid or is missing. 400 | 1342 | Attachment
 * ticket is no longer pending. Create a new attachment ticket and try again. 400 | 1343 | User movement policy planId and
 * planName doesn't match. 400 | 1344 | Duplicate user movement policy for the same planId. 400 | 1345 | Unsupported user
 * movement policy attribute. 400 | 1346 | planId should be a managed plan. 400 | 1347 | User movement policy list count is
 * more than expected managed plan count. 400 | 1348 | Empty user movement policy list. 400 | 1349 | Input data is out of
 * expected length boundary. 400 | 1350 | Missing required property. 400 | 1351 | Data value is not supported. 400 | 1352 |
 * Unexpected leading/trailing space in input data. 403 | 1354 | Cannot reactivate the user specified. They are not a member of
 *  your account. 403 | 1355 | You cannot reactivate yourself from the account through the API. 403 | 1356 | The operation you
 * are attempting to perform is not supported in Smartsheet Gov. 400 | 1357 | Container type is invalid. 403 | 1359 | User
 * account with a common ISP domain email cannot be deactivated. You can only remove them from the Org. 400 | 1360 | Please
 * confirm all existing alternate email addresses before attempting to add another email address. 403 | 1361 | This action is
 * restricted by an SSO Policy. Please reach out to the workspace admin for any questions. 403 | 1362 | This action is
 * restricted by an MFA Policy. Please reach out to the workspace admin for any questions. 403 | 1363 | This action is
 * restricted by this organization's Data Egress Policy. Please reach out to this organization's system admin for any questions
 * . 400 | 2110 | The requested move is too large to process at this time. Please select a smaller number of items to move. 429
 *  | 4003 | Rate limit exceeded. 410 | 4005 | API version retired. 400 | 5xxx | Errors in the 5xxx range represent conditions
 * that a developer cannot reasonably prevent or handle, most typically related to account status. These error messages are
 * localized and can be displayed to the end-user to inform them of the condition that caused the error to occur.  ## 500-Level
 *  Error Codes  500-level error codes indicate there is some kind of permanent error.  HTTP status code | Smartsheet errorCode
 *  | Smartsheet message -----|-----|-----| 500 | 1119 | Failed to complete copy.<br/>**NOTE**: may include a \"detail\" object
 *  containing \"topContainerType\" and \"topContainerId\" which represent the top-level folder or workspace that were
 * partially copied. 500 | 1170 | The sheet referenced by this widget is unavailable or deleted. 500 | 1171 | The report
 * referenced by this widget is unavailable or deleted. 500 | 1172 | The referenced cell is unavailable or deleted. 500 | 1278
 * | Proofing service error. 500 | 1285 | Dashboard source profile field missing. 500 | 1325 | Unable to create baseline column
 * . 501 | 1333 | The form service mobile API is not implemented for this environment. 501 | 1353 | Work Insights Widget is
 * currently not supported in Dashboard API. 501 | 1358 | This operation has been deprecated. Smartsheet recommends to use the
 * Deactivate User API endpoint. Contact Support for more information. 500 | 4000 | An unexpected error has occurred. Please
 * contact api@smartsheet.com for assistance. 503 | 4001 | Smartsheet.com is currently offline for system maintenance. Please
 * check back again shortly. 500 | 4002 | Server timeout exceeded. Request has failed. 500 | 4004 | An unexpected error has
 * occurred. Please retry your request. If you encounter this error repeatedly, please contact api@smartsheet.com for
 * assistance.<br/><br/>**Or**<br/><br/>Request failed because sheetId {0} is currently being updated by another request that
 * uses the same access token. Please retry your request once the previous request has completed. 500 | 5151 | The action could
 *  not be completed because the following people are outside of the approved domain sharing list:[email address] 500 | 5502 |
 * You must have sheet admin permission to save a notification with recipients other than yourself.  # OAuth Walkthrough  Apps
 * connect to Smartsheet using [OAuth 2.0](https://oauth.net/2/) to authenticate and authorize users. If you are building an
 * app, this documentation will walk you through the steps you need to authenticate your users. The Smartsheet [SDKs]
 * (/#section/API-Basics/SDKs-and-Sample-Code) contain APIs for OAuth 2.0.  *NOTE:* You will need a Tenant ID for users of apps
 *  like AWS AppFabric. You can find your Tenant ID in Admin Center under *Security & Controls*. There is a *Smartsheet Tenant
 * ID* pane.        ## First Steps  Before you can start using OAuth 2.0 with your app, Smartsheet needs the following
 * information:   1. You must register (https://developers.smartsheet.com/register) with Smartsheet to get a developer account*
 * . The developer account gives you access to \"Developer Tools,\" where you manage your app.  2. In \"Developer Tools,\"
 * complete any required fields in your developer profile.  3. In \"Developer Tools,\" register your app so Smartsheet can
 * assign it a client ID and a client secret.  4. Review the list of access scopes. Choose which ones your app needs to access
 * a user's Smartsheet data. The user must consent to that access. After you've worked through these steps, you'll be ready to
 * implement the [OAuth Flow](/#section/OAuth-Walkthrough/OAuth-Flow).  *NOTE:* Your use of the Smartsheet APIs and SDKs are
 * governed by the [Developer Agreement](https://www.smartsheet.com/legal/developer-program-agreement).  *A developer account
 * is a service account you should use when developing an integration. It counts as an additional account against your plan. A
 * best practice is to keep this account separate from your user account.  ## Open Developer Tools  1. Log in to Smartsheet
 * with your developer account. 2. Click the \"Account\" button in the lower-left corner of your Smartsheet screen, and then
 * click \"Developer Tools\". 3. Do one of the following:   * If you need to register an app, click \"Create New App\".   * If
 * you need to manage an app, click \"view/edit\" for the app.  ## Register Your App Using Developer Tools  1. Log in to
 * Smartsheet with your developer account. 2. Click the \"Account\" button in the upper-right corner of your Smartsheet screen,
 *  and then click \"Developer Tools\". 3. In the \"Create New App\" form, provide the following information:   * Name: the
 * name the user sees to identify your app   * Description: a brief description intended for the user   * URL: the URL to
 * launch your app, or the landing page if not a web app   * Contact/support: support information for the user   * Redirect
 * URL: also known as a callback URL. The URL within your application that will receive the OAuth 2.0 credentials After you
 * click \"Save\", Smartsheet assigns a client Id and secret to your app. Make a note of these Ids for the next steps; however,
 *  you can always look them up again in \"Developer Tools\".  ## OAuth Flow  Your app must implement a 3-legged OAuth flow to
 * retrieve an access token it can use to access Smartsheet data on behalf of an end user. The following diagram has an
 * overview of the OAuth flow:  <img src=\"/images/OAuth_flow2.png\" alt=\"Simplified graphic showing what data is passed back
 * and forth during OAuth flow\">  NOTE: App registration and OAuth flow require HTTPS.  ## Access Scopes  To access a user's
 * Smartsheet data, your application must explicitly ask the user for permission. You do this by using access scopes, which
 * enable your app to communicate to the user what type of operations it is performing. Access scopes do not override existing
 * [access-level restrictions](/#section/Security/Resource-Access-Levels). For example, having the access scope of
 * **WRITE_SHEETS** does not allow your app to update a sheet on which the user has **VIEWER** access level.  The access scopes
 *  are as follows:  Access Scope | Description -----|-----| ADMIN_SHEETS | Modify sheet structure, including column
 * definition, publish state, etc. ADMIN_SIGHTS | Modify Sights/dashboards structure. ADMIN_USERS | Add and remove users from
 * your Smartsheet organization account; create groups and manage membership. ADMIN_WEBHOOKS | Create, delete, and update
 * webhooks; get all webhooks; reset shared secret. ADMIN_WORKSPACES | Create and manage workspaces and folders, including
 * sharing. CREATE_SHEETS | Create new sheets. CREATE_SIGHTS | Create new Sights/dashboards. DELETE_SHEETS | Delete sheets.
 * DELETE_SIGHTS | Delete Sights/dashboards. READ_CONTACTS | Retrieve contacts. READ_EVENTS | Retrieve events. READ_SHEETS |
 * Read all sheet data, including attachments, discussions, and cell data. READ_SIGHTS | Read all Sights/dashboards data.
 * READ_USERS | Retrieve users and groups for your Smartsheet organization account. SHARE_SHEETS | Share sheets, including
 * sending sheets as attachments. SHARE_SIGHTS | Share Sights/dashboards. WRITE_SHEETS | Insert and modify sheet data,
 * including attachments, discussions, and cell data.  NOTE: <b>Additional Info:</b><ul><li>When you request an authorization
 * code, you specify all of the access scopes you need for your app. Smartsheet encodes those permissions into the auth code
 * and subsequent access token.</li><li>Your app must request at least one access scope, but should only request the scopes
 * necessary.</li><li>Once your app attains a valid access token, it can execute a [Get Current User]
 * (/tag/users#operation/get-current-user) operation, regardless of which access scopes were requested.</li></ul>  ## Request
 * an Authorization Code  `GET https://app.smartsheet.com/b/authorize`  `POST https://app.smartsheet.com/b/authorize`
 * Initiates the process to get authorization from the user. Smartsheet will redirect this URL to display your app's consent
 * page with an explanation of the data the app will need access to. This consent page is autogenerated by Smartsheet based on
 * a combination of the information you registered for your app and the parameters you send with the request.  Value |
 * Description | -----|-----| **client_id** | Required. The client Id you obtained when you registered your app.
 * **response_type** | Required. Indicates whether the endpoint returns an authorization code. Must be set to \"code\".
 * **scope** | Required. Space-delimited list of [access scopes](/#section/OAuth-Walkthrough/Access-Scopes) to which you are
 * asking the user to grant access. **NOTE:** No access scopes are necessary if you simply need to validate that the user has a
 *  Smartsheet account. **state** | Optional. An arbitrary string of your choosing that is returned to your app; a successful
 * roundtrip of this string helps ensure that your app initiated the request.  You can view code examples by clicking the
 * corresponding tab in the rightmost pane. The cURL example shows a `GET`.  A correctly formatted Auth URL request looks like
 * this: `https://app.smartsheet.com/b/authorize?response_type=code&client_id=dheu3dmkd32fhxme&scope=READ_SHEETS%20WRITE_SHEETS
 * &state=MY_STATE`  NOTE: If the user has not yet logged into Smartsheet, the redirect will first take them to a login page,
 * and then display the consent page.  At this point, the user can authorize your app to access their Smartsheet account, as in
 *  the following example:  <img src=\"/images/developer_tools3.png\" alt=\"Dialog box to allow or deny scopes\">  After the
 * user clicks \"Allow\" or \"Deny\", you'll receive a response from Smartsheet outlined in the next sections.  ## If the User
 * Clicks Allow  If the user clicks \"Allow\", Smartsheet redirects the user to the callback URL with the following parameters:
 *   Value | Description | -----|-----| **code** | Authorization code required to obtain access token, such as
 * 'sample6p9qisx6a'. **expires_in** | Number of milliseconds code is valid once issued; this is currently 599135 milliseconds,
 *  or approx. 10 minutes--you must obtain an access token within that time. **state** | The same value for **state** that you
 * sent when you requested the authorization code.  At this point, you should verify the **state** value matches what you sent
 * to the user when you requested the authorization code. This helps you determine that the response came from the user and not
 *  a malicious script. If the values do not match, you should reject the response.  For other error conditions, see the list
 * of [OAuth Error Types](/#section/OAuth-Walkthrough/OAuth-Error-Types).  ## If the User Clicks Deny  If the user clicks
 * \"Deny\", Smartsheet redirects the user to the callback URL with the following parameters:  Value | Description |
 * -----|-----| **error** | \"access_denied\". **state** | The same value for **state** that you sent when you requested the
 * authorization code.  ## Get or Refresh an Access Token  Once you’ve successfully obtained an authorization code, the next
 * step is to exchange the code for an access token. (Remember, the authorization code expires after 599135 milliseconds.)
 * Access tokens expire after 604799 seconds, which is approx 7 days. Use the refresh token to obtain a new access token and a
 * new refresh token. Once you obtain the new tokens, you must use them in place of the old ones, which are no longer valid.
 * To get or refresh an access token, see [Refresh Access Token](/tag/token#operation/tokens-getOrRefresh).  ## OAuth Error
 * Types  Value | Description | -----|-----| **invalid_client** | The client information is invalid. Ensure your client id is
 * correct. **invalid_grant** | The authorization code or refresh token is invalid or expired or the hash value does not match
 * the app secret and/or code. **invalid_request** | The request parameters are invalid or missing. **invalid_scope** | One or
 * more of the requested access scopes is invalid. Please check the list of [access scopes]
 * (/#section/OAuth-Walkthrough/Access-Scopes). **unsupported_grant_type** | **grant_type** must equal **authorization_code**
 * or **refresh_token**. **unsupported_response_type** | **response_type** must be set to **code**.  # SDKs and Samples
 * Smartsheet software development kits (SDKs) and sample applications help you develop with C#, Java, Node.js, and Python.
 * The SDKs are streamlined interfaces for using Smartsheet in several languages. The sample applications demonstrate using the
 *  SDKs to access Smartsheet.  Language | SDK | Sample application -----|-----|-----| C# | [smartsheet-csharp-sdk]
 * (https://github.com/smartsheet/smartsheet-csharp-sdk) | [csharp-read-write-sheet](https://github
 * .com/smartsheet-samples/csharp-read-write-sheet) Java | [smartsheet-java-sdk](https://github
 * .com/smartsheet/smartsheet-java-sdk) | [java-read-write-sheet](https://github.com/smartsheet-samples/java-read-write-sheet)
 * Node.js | [smartsheet-javascript-sdk](https://github.com/smartsheet/smartsheet-javascript-sdk) | [node-read-write-sheet]
 * (https://github.com/smartsheet-samples/node-read-write-sheet) Python | [smartsheet-python-sdk](https://github
 * .com/smartsheet/smartsheet-python-sdk) | [python-read-write-sheet](https://github
 * .com/smartsheet-samples/python-read-write-sheet) Ruby* | [smartsheet-ruby-sdk](https://github
 * .com/smartsheet-platform/smartsheet-ruby-sdk) | [ruby-read-write-sheet](https://github
 * .com/smartsheet-samples/ruby-read-write-sheet)  *The Ruby SDK is no longer maintained.  SKD benefits: * Retry with backoff
 * to automatically recover from network errors or rate limiting * Request & response logging * Native object models for
 * request and responses (Java and C# only)  Each SDK readme file demonstrates SDK installation and using the SDK.   You can
 * download the sample apps and run them with SDKs. The samples can be a great starting points for your own applications.  #
 * Security  The following provides some best practices to consider when working with the Smartsheet API and any access tokens
 * (API keys), or other sensitive information.  ## Application Authentication Approaches  When choosing an authentication
 * method, it is important to consider your integration scenario. Is the integration machine-to-machine without user
 * interaction, or do you want user consent and interaction?  - **If your scenario involes user consent and interaction,** it's
 *  typically best to use OAuth with Smartsheet. Note that the Smartsheet implementation of OAuth 2.0 is a 3-legged process,
 * which requires human intervention. See [OAuth Walkthrough](/#section/OAuth-Walkthrough) for details.  - **If your scnenario
 * is strictly machine-to-machine,** execute raw token requests over HTTPS. It's a straightforward, secure way to authenticate.
 *  See [Authentication and Access Tokens](/#section/API-Basics/Authentication-and-Access-Tokens) for details.  Each scenario
 * requires an HTTP authorization header containing an access token.  > NOTE: In applications and for routine requests, it is
 * best to use a shared account, such as ticket-processor@example.com, rather than your individual work account.  > WARNING: If
 *  an unauthorized user gets a copy of this token, they will be able to access all Smartsheet data that you have access to,
 * both to read and modify on your behalf. You should keep your tokens secure and do not share them with anyone.   ## Access
 * Token Best Practices  Never commit access tokens (API keys) to accessible version control systems like GitHub or BitBucket.
 * Instead, use one of the following recommended storage options.  ### Recommended Storage Options  * Use an app configuration
 * tool suitable for deploying secrets to your app, or * Use a config file outside of source control, or * Use environment
 * variables set outside of source control.  If you have mistakenly deployed API keys to a publicly accessible location such as
 *  GitHub, then you should immediately revoke those API keys, revise your application to use a preferred method of key
 * deployment, and then generate new keys.  ### Storing in a database  If you need to store API keys in a database, consider
 * the following protections:  * Restrict access so API keys are only readable by the owner of the object * Restrict read
 * privileges to the database table * Make sure your database or the disk the database is on is set to encrypt data at rest  >
 * NOTE: When using any Smartsheet SDK, you can use the environment variable of `SMARTSHEET_ACCESS_TOKEN`. If the access token
 * is null on input to the client builder, the SDK will automatically pick up the value of that environment variable.  ##
 * Revoking and Regenerating Tokens  If you've committed code to a repository before implementing these security best
 * practices, here are some steps to resecure your API keys.  **For requests with raw tokens:**  1. [Revoke the token]
 * (/tag/token#operation/tokens-delete) 2. Create a [new token](/#section/API-Basics/Authentication-and-Access-Tokens)  **For
 * OAuth with Smartsheet:** Work through the [OAuth Walkthrough](/#section/OAuth-Walkthrough) to regenerate client secrets,
 * auth codes, and tokens.  ## Resource Access Levels  Sheet, template, and workspace objects have an `accessLevel` attribute
 * that describes the current user's access level to that object. This corresponds directly to the sharing and access controls
 * of Smartsheet that are available through the Smartsheet UI.  The `accessLevel` attribute has one of the following values:
 * Value (`string`) | Description | -----|-----| `ADMIN` | The user can edit and share the resource, and can alter the
 * structure of the resource as well. `COMMENTER` | The same as `VIEWER`, but with the ability to leave comments and add
 * attachments. `EDITOR` | The user can edit the resource, but cannot alter the structure of, delete, or share the resource.
 * `EDITOR_SHARE` | The same as `EDITOR`, but with the ability to share the resource to other users. `OWNER` | The user has
 * complete control over the resource. `VIEWER` | The user has read-only access to the resource.  > NOTE: Smartsheet also uses
 * [access scopes](/#section/OAuth-Walkthrough/Access-Scopes). *Access levels* describe the actual permissions a specific user
 * has for a specific sheet or other resource. *Access scopes* describe the general categories of access requested by a
 * third-party app.  # Smartsheet Gov  Smartsheet Gov has \"FedRAMP Authorized\" status as part of Federal Risk and
 * Authorization Management Program (FedRAMP). As an API developer working on a Smartsheet Gov account, you should be aware of
 * the following differences from the standard API:  * The base URL for each API call is smartsheetgov.com instead of
 * smartsheet.com. This documentation uses smartsheet.com in *all* examples. * Smartsheetgov.com uses a separate token from
 * Smartsheet.com. * Smartsheetgov.com has a restriction on attachment types. Only the following attachment types are allowed:
 * BOX_COM, FILE, GOOGLE_DRIVE, LINK, or ONEDRIVE.  If you use a Smartsheet SDK, you need to modify the standard config file to
 *  point to smartsheetgov.com. There are instructions specific to each SDK on how to modify the config file at the following
 * locations:  * [C# SDK](https://github.com/smartsheet-platform/smartsheet-csharp-sdk/blob/master/ADVANCED
 * .md#working-with-smartsheetgovcom-accounts) * [Java SDK](https://github
 * .com/smartsheet-platform/smartsheet-java-sdk/blob/master/ADVANCED.md#working-with-smartsheetgovcom-accounts) * [Node.js SDK]
 * (https://github.com/smartsheet-platform/smartsheet-javascript-sdk#working-with-smartsheetgovcom-accounts) * [Python SDK]
 * (https://github.com/smartsheet-platform/smartsheet-python-sdk/blob/master/ADVANCED
 * .md#working-with-smartsheetgovcom-accounts) * [Ruby SDK](https://github
 * .com/smartsheet-platform/smartsheet-ruby-sdk#basic-configuration)  # Smartsheet Regions Europe  Smartsheet Regions Europe is
 *  a separate data island. As an API developer working on a Smartsheet Regions Europe account, you should be aware of the
 * following differences from the standard API:  * The base URL for each API call is smartsheet.eu instead of smartsheet.com.
 * This documentation uses smartsheet.com in *all* examples. * Smartsheet Regions Europe aka Smartsheet EU uses a separate
 * token from Smartsheet.com.  If you use a Smartsheet SDK, you need to modify the standard config file to point to smartsheet
 * .eu. There are instructions specific to each SDK on how to modify the config file at the following locations:  * [C# SDK]
 * (https://github.com/smartsheet-platform/smartsheet-csharp-sdk/blob/master/ADVANCED
 * .md#working-with-smartsheet-regions-europe-accounts) * [Java SDK](https://github
 * .com/smartsheet-platform/smartsheet-java-sdk/blob/master/ADVANCED.md#working-with-smartsheet-regions-europe-accounts) *
 * [Node.js SDK](https://github.com/smartsheet-platform/smartsheet-javascript-sdk#working-with-smartsheet-regions-europe
 * -accounts) * [Python SDK](https://github.com/smartsheet-platform/smartsheet-python-sdk/blob/master/ADVANCED
 * .md#working-with-smartsheet-regions-europe-accounts) * [Ruby SDK](https://github
 * .com/smartsheet-platform/smartsheet-ruby-sdk#basic-configuration)  # Work at Scale  ## Bulk Operations  The Smartsheet API
 * supports a number of bulk operations that can operate on multiple objects.  Unlike single-object operations, bulk operations
 *  allow you to create, update, or delete multiple objects in a single request. For example, if you want to update 10 rows
 * within a sheet, do so using a single [Update Rows](/tag/rows#operation/update-rows) request, rather than executing 10
 * separate requests - one for each row.  **Optional Bulk Operations**  Several endpoints support optional bulk `POST`
 * operations which exist alongside the standard single-object `POST`.  For these endpoints, you may pass in either a single
 * object or an array of objects. Depending on what was passed in, the [Result object]
 * (/tag/commonObjects/#section/Result-Object) returned contains either a single object or an array.  An example optional bulk
 * operation is [POST /favorites](/tag/favorites#operation/add-favorite): you can pass in a single [Favorite object]
 * (/tag/favoritesObjects#section/Favorite-Object) to create a single favorite, or an array of Favorite objects to create
 * multiple favorites in a single request.  Endpoints which support bulk operations are noted as such in the API reference
 * documentation.  NOTE: Most `POST` operations fail when attempting to create a single object which already exists (for
 * example, favorites, shares, group members).  However, for the corresponding bulk operations, these endpoints do *not* return
 *  an error if one or more items in the array already exist.  Existing items are simply ignored, and the [Result object]
 * (/tag/commonObjects#section/Result-Object) returned omits them.  **Partial Success**  In general, the default behavior for
 * bulk operations is to fail outright if any of the objects in the request are invalid for some reason.  If successful,
 * Smartsheet creates/updates/deletes all objects in the request; if not, no objects are changed.  However, there are some
 * operations that support partial success, which means the operation still succeeds even if one or more of the objects in the
 * request fails for some reason (for example, an object is invalid). Here is another example: if you want to update more than
 * one row, you send more than one row object in your request. If a row object is invalid, that row update will fail, but the
 * other row updates will succeed.  Partial success is *not* the default mode for an operation and you must explicitly enable
 * it by using a query string parameter.  This is noted in the documentation for operations that support partial success.  When
 *  partial success is enabled, and one or more of the objects in the request fail to be added/updated/deleted, a standard
 * [Result object](/tag/commonObjects#section/Result-Object) is returned, but with a **message** of <b>'PARTIAL_SUCCESS'</b>
 * (instead of <b>'SUCCESS'</b>), and a **resultCode** of **3**. Additionally, the object contains a **failedItems** attribute
 * -- an array of [BulkItemFailure objects](/tag/commonObjects#section/BulkItemFailure-Object) that contains an item for each
 * object in the request that failed to be added/updated/deleted.  ## Paging  The Smartsheet API contains a number of index
 * endpoints (typically denoted in the documentation with titles beginning with \"Get All\" or \"List\") which return arrays of
 *  objects. Examples include `GET` [/users](/tag/users#operation/list-users), [/sheets](/tag/sheets#operation/list-sheets),
 * [/sheets/{sheetId}/columns](/tag/columns#operation/columns-listOnSheet), and many others.  These endpoints all support
 * pagination, meaning you can retrieve paged subsets of results, enabling you to process potentially large result sets in
 * smaller chunks.  **Paging Query String Parameters**  Index endpoints all support pagination via the following optional query
 *  string parameters:  Value | Type | Description | ------|------|-------------| **includeAll**  | Boolean | If true, include
 * all results, that is, do not paginate.  Mutually exclusive with **page** and **pageSize** (they are ignored if
 * **includeAll=true** is specified). **page**  | number  | Which page to return.  Defaults to **1** if not specified.  If you
 * specify a value greater than the total number of pages, the last page of results is returned. **pageSize**  | number  | The
 * maximum number of items to return per page.  Unless otherwise stated for a specific endpoint, defaults to **100**.  NOTE:
 * Most index endpoints default to a page size of 100 results.  If you want all results at once, you must specify the
 * <b>includeAll=true</b> query string parameter.  **Paged Responses**  Index endpoints all return paged responses via an
 * IndexResult object, which provides paging metadata that can be used to navigate the full set of pages in the result set:
 * Value | Type | Description | ------|------|-------------| **data**  | array  | An array of objects representing the current
 * page of data in the result set. **pageNumber** | number | The current page in the full result set that the **data** array
 * represents. NOTE: when a page number greater than **totalPages** is requested, the last page is instead returned.
 * **pageSize** | number | The number of items in a page.  Omitted if there is no limit to page size (and hence, all results
 * are included).  Unless otherwise specified, this defaults to **100** for most endpoints. **totalCount** | number | The total
 *  number of items in the full result set. **totalPages** | number | The total number of pages in the full result set.  ##
 * Rate Limiting  **Handle \"Rate limit exceeded\" Error**  To prevent abuse and undue stress on the Smartsheet servers,
 * Smartsheet reserves the right to enforce some limits depending on the load on our systems. This reduction is sometimes
 * called rate limiting or throttling. Certain operations, such as attaching a file and getting cell history, are resource
 * intensive.  The Smartsheet API implements \"rate limiting\" to protect the system. When API calls exceed an acceptable load,
 *  an HTTP **429** status will be returned along with the following response body:  ```json {   \"errorCode\": 4003,
 * \"message\": \"Rate limit exceeded.\" } ``` <br/>  Smartsheet recommends that you design your integration to gracefully
 * handle this rate limit error. One way of doing that would be to have your integration *sleep* for a minimum of 60 seconds
 * when this error is encountered, and then subsequently retry the request.  Alternatively, you might choose to implement
 * [exponential backoff](https://en.wikipedia.org/wiki/Exponential_backoff) (an error handling strategy whereby you
 * periodically retry a failed request with progressively longer wait times between retries, until either the request succeeds
 * or the certain number of retry attempts is reached). Note that the SDKs implement this behavior.  **Avoid Executing \"Rapid
 * Fire\" Updates**  If the only thing your integration does is execute an [Update Rows](/tag/rows#operation/update-rows)
 * request once every second for the same sheet, that would only amount to a total of 60 requests per minute -- well within
 * rate limiting guidelines. However, updating the same object in such rapid succession could result in save errors that
 * negatively impact both your integration as well as user experience within the Smartsheet app. To avoid this scenario, design
 *  your integration such that API requests are never executed with rapid-fire succession against the same Smartsheet object.
 * For maximum efficiency, consider batching up changes and submitting them in a single request using a [bulk operation]
 * (/#section/Work-at-Scale/Bulk-Operations) (for example, [Update Rows](/tag/rows#operation/update-rows) or [Add Columns]
 * (/tag/columns#operation/columns-addToSheet).  **Execute Requests Serially**  Executing multiple API requests in parallel to
 * update a specific Smartsheet object results in reduced performance and often results in errors due to save collisions. To
 * avoid this scenario, design your integration such that API requests to update a specific Smartsheet object are always
 * executed serially (that is, execute one request at time, not beginning the next request until the previous request has
 * completed).  NOTE: Attempts to perform multiple concurrent updates to a sheet may result in [error code 4004]
 * (/#section/Error-Codes/400-Level-Error-Codes).  **Use the Smartsheet SDKs**  The [SDKs]
 * (/#section/API-Basics/SDKs-and-Sample-Code) provide default backoff and retry to accommodate rate limiting responses from
 * the API. Note that the default maximum retry duration is typically 30 seconds. You may wish to increase this if your
 * application is making many API calls in quick succession. For specific instructions per language, see the Readme for the
 * respective SDK.  # Additional Information  Here are some additional resources:  * [Smartsheet Developers site]
 * (https://developers.smartsheet.com/) provides quick access to samples, SDKs, and more * <a href=\"https://community
 * .smartsheet.com/categories/api-developers\" target=\"_blank\">Smartsheet API and developer community discussions</a> * <a
 * href=\"https://stackoverflow.com/questions/tagged/smartsheet-api\" target=\"_blank\">StackOverflow</a> `smartsheet-api`
 * tagged questions and answers * <a href=\"https://10kft.github.io/10kft-api/\" target=\"_blank\">Resource Management API
 * documentation</a> * <a href=\"https://developers.brandfolder.com/\" target=\"_blank\">Brandfolder API and SDK
 * documentation</a> * <a href=\"https://help.smartsheet.com/contact\" target=\"_blank\">Contact us</a>  *NOTE:* Your use of
 * the Smartsheet APIs and SDKs are governed by the [Developer Agreement](https://www.smartsheet
 * .com/legal/developer-program-agreement).  **Got feedback?** Share it with us in a <a href=\"https://community.smartsheet
 * .com/categories/api-developers\" target=\"_blank\">Smartsheet Community discussion</a>.  # Changelog  Here is the log of
 * changes to the API.  <!-- Entry Template - IMPORTANT: Use an H3 heading as demonstrated below. ### YYYY-MM-DD
 * (Added/Updated/Deprecated/Removed) the (something. If the change requires consumers to change how they use an API, describe
 * what they must do and explain why the API was changed.) -->  ### 2025-01-22  Webhooks are automatically disabled on sheets
 * that exceed any of the scale limits  Scale limits:  * 20,000 rows * 400 columns * 500,000 cells  Related resources: *
 * [Webhook Status](https://smartsheet.redoc.ly/tag/webhooksDescription#section/Webhook-Status) has a new related status *
 * [Webhook Error Codes](https://smartsheet.redoc.ly/tag/webhooksDescription#section/Webhook-Errors) has new related error
 * codes  To keep webhooks enabled on a sheet, stay below the aforementioned limits.  As a sheet Owner, you can re-enable
 * webhooks on a sheet by following these steps: 1. Reduce the sheet size to within the aforementioned limits. 1. Execute the
 * [Update Webhook](https://smartsheet.redoc.ly/tag/webhooks#operation/updateWebhook) method with the `enabled` body attribute
 * set to `true`.  Related resources: * [Webhook Status](https://smartsheet.redoc
 * .ly/tag/webhooksDescription#section/Webhook-Status) has a new related status. * [Webhook Error Codes](https://smartsheet
 * .redoc.ly/tag/webhooksDescription#section/Webhook-Errors) has new related error codes.  ### 2024-10-09  Updated the
 * `WebContentWidgetContent.type` to the `WidgetWebContent` enum. The former enum `WEBCONTENT` was incorrect.  # RESOURCES  The
 *  following sections specify Smartsheet resources, including resource objects and methods (endpoints).  # Authentication
 * <!-- ReDoc-Inject: <security-definitions> -->
 *
 * The version of the OpenAPI document: 2.0.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.ronreynolds.smartsheet.it;

import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.ContactsApi;
import com.ronreynolds.smartsheet.model.Contact;
import com.ronreynolds.smartsheet.model.GetContactInclude;
import com.ronreynolds.smartsheet.model.ListContacts200Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * API tests for ContactsApi
 */
@Disabled("ContactsApiTest not yet implemented")
public class ContactsApiTest {

    private final ContactsApi api = new ContactsApi();


    /**
     * Get Contact
     * <p>
     * Gets the specified contact.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getContactTest() throws ApiException {
        Long contactId = null;
        List<GetContactInclude> include = null;
        Contact response =
                api.getContact(contactId, include);
        assertThat(response).isNotNull();

        // TODO: test validations
    }

    /**
     * List Contacts
     * <p>
     * Gets a list of the user&#39;s Smartsheet contacts.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listContactsTest() throws ApiException {
        Boolean includeAll = null;
        OffsetDateTime modifiedSince = null;
        Boolean numericDates = null;
        Integer page = null;
        Integer pageSize = null;
        ListContacts200Response response =
                api.listContacts(includeAll, modifiedSince, numericDates, page, pageSize);

        // TODO: test validations
    }

}
