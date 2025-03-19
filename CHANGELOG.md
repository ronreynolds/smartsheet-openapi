# Smartsheet-OpenAPI 
* based on https://keepachangelog.com/en/1.0.0/ and https://semver.org/
* sections: Breaking Added Changed Deprecated Fixed Removed Security ToDo (in that order)

## 0.1.2 - unreleased
### Added
* .
### Changed
* .
### Fixed
* .
### Removed
* .

## 0.1.1 - 2025-03-19
### Added
* publish task so can start using this lib in other projects
* `com.ronreynolds.smartsheet.api.util.`
  * `Attachments`, `Cells`, `Columns`, `Folders`, `Rows`, `Workspaces` (all based on my smartsheet-sdk-ex project)
    * collections of util methods to help with specific model types received/returned with the Smartsheet API
  * `Converters` for converting between types (typically responses into cleaner models)
  * `Constants` is a collection of fixed values useful for working with the API
* logback lib for logging (works great with spring-boot et al)
* private ctors to all the utility classes so they can't be created nor extended
* `State` assertion-util class for ensuring certain state conditions are met before proceeding
* location fields to `#/components/schemas/Row` which are missing from original Smartsheet OpenAPI spec
### Changed
* upgraded to latest (7.12.0) openapi-codegen plugin
* user-agent string used by `ApiClient`
* changed `Sheets` to take `ApiClient` rather than `SheetsApi` and various other improvements
### Fixed
* processing of get-sheet response (issue was the one-of that included SheetVersion which couldn't be determined in the response)
### Removed
* some library deps 
  * some possibly left-over from previous config-gen code
  * some possibly assumed and/or copy-pasta from other projects
* `JavaTimeFormatter` - not used (currently) by lib:native and this isn't how it's done anyway (would need to use `.mustache`)
* `SheetVersion` from `oneOf` in get-sheet response
* `package-info.java`; not sure why i added it in the first place
* `AbstractRow` from `#/components/schemas` as it's not used
* `SheetVersion` from get-sheet-by-id responses as it will never (AFAIK) be returned and it breaks Jackson parsing

## 0.1.0 - 2025-02-08
felt like a land-mark version; a LOT of things work; MANY things don't but technically this is a MVP for at least 3 
(of 109+) endpoints. :) 
### Added
* `ApiClient.mustache` to force generated `ApiClient` to use same timestamp format as Smar-SDK
* note, if using a library OTHER than "native" you'll want to check the associated `ApiClient.mustache` files 
  [here](https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator/src/main/resources/Java/libraries/) 
  as each library (and date-library) can trigger different templates being loaded and the whole family of templates for
  a particular language/library combo are quite tightly coupled (because they're not consistent with other libraries)
  * this is also why the `JavaTimeFormatter.mustache` is not always loaded; only the following http-client templates 
    currently (7.11.0) support it
    * apache-httpclient, jersey2, jersey3, restclient, resteasy, resttemplate, vertx, webclient
* `style:simple` to all comma-separated list parameters (supposed to be default but it's not)
  * also changed all comma-separated list parameters to `type:array`
* moved all `enum` types in `#/components/schemas/` so that they'll be top-level sharable types rather than unshared inner-types
* added `#/components/schemas/Datetime` to combine `type:string,format:date-time` 
* `ApiClients` utility class to make it easier to have all needed headers and other supporting features
  * made `createNewClient` public so a new `ApiClient` could be created with current settings WITHOUT replacing global default
* `BasicUse` example "app" to start exercising various `Api` types
  * `ServerinfoApi` works perfectly AFAICT
  * `UserApi` works for reads mostly (doesn't seem to support "modifiedSince" parameter but perhaps that's API-side)
  * `SheetApi` still needs lots of work (mostly on the processing of the response which has 4 different MIME types)
* 3 referenced-but-not-generated model classes until the openapi-generator issue can be worked out
  * a theory is that part of the generator decided the models were needed but didn't generate them but their references 
    (`import`) were left in
  * need to dig into [openapi-generator code](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator/src/main/resources/Java) 
    to determine cause 
* a few utility classes from some of my other projects (`Settings`, `StringUtils`, `ToString`) 
### Changed
* significant changes to the [original OpenAPI spec](src/main/java/resources/smartsheet-openapi-v2-2025-01-22.json) 
  until it successful parsed and could generate compilable Java code
### Removed
* `#/components/schemas/DateUnion` because it won't work and `Timestamp` because it's unused
* removed `Authorization` header from every single `#/paths` entry; unneeded because each has a `security` attribute