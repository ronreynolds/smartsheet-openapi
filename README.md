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
* unfortunately the old swagger editor doesn't support versions other than 3.0.x

### https://editor-next.swagger.io/
* used to convert JSON to YAML (much easier to edit)
  * removed all `x-codeSamples` sections (as they won't apply to the OpenAPI-generated version anyway)
  
### org.openapi.generator
#### openApiValidate
* working thru issues 
  * mostly it seems to not like endpoints with multiple parameter refs to parameters of other endpoints

### https://redocly.com/docs/cli/commands/bundle
* swagger-cli has been abandoned according to https://github.com/APIDevTools/swagger-cli and replaced with Redocly's CLI - https://redocly.com/docs/cli
* installed (`sudo npm i -g @redocly/cli@latest`) and generated another yaml file using `redocly bundle smartsheet-openapi-v2.json --dereferenced -o openapi-3.1.0-smartsheet-v2.1.yaml --ext yaml`
  * the results were somewhat mixed 
    * yes, all `$ref`s were removed but replaced with a different type of ref that's harder to follow
    * also it appears to have broken the `components` section in a whole new way.

## Changes
* openapi code-gen doesn't seem to handle multiple $refs so need to replace those cases with their literal values
  * e.g., the error `paths.'/favorites'(get).parameters. There are duplicate parameter values` is caused by this block:
```yaml
        - $ref: '#/paths/~1contacts/get/parameters/0'
        - $ref: '#/paths/~1contacts/get/parameters/3'
        - $ref: '#/paths/~1contacts/get/parameters/4'
```
which can be replaced with
```yaml
# 0
        - name: includeAll
          in: query
          required: false
          description: If true, include all results, that is, do not paginate. Mutually exclusive with page and pageSize (they are ignored if includeAll=true is specified).
          schema:
            type: boolean
            default: false
# 1
        - name: modifiedSince
          in: query
          required: false
          description: When specified with a date and time value, response only includes the objects that are modified on or after the date and time specified. If you need to keep track of frequent changes, it may be more useful to use Get Sheet Version.
          schema:
            $ref: '#/components/schemas/Attachment/properties/createdAt'
 # 2
        - name: numericDates
          in: query
          schema:
            type: boolean
            default: false
          description: You can optionally choose to receive and send dates/times in numeric format, as milliseconds since the UNIX epoch (midnight on January 1, 1970 in UTC time), using the query string parameter numeric Dates with a value of true. This query parameter works for any API request.
 # 3
        - name: page
          in: query
          required: false
          description: Which page to return. Defaults to 1 if not specified. If you specify a value greater than the total number of pages, you'll receive an empty *data* set.
          schema:
            type: number
            default: 1
# 4
        - name: pageSize
          in: query
          required: false
          description: The maximum number of items to return per page. Unless otherwise stated for a specific endpoint, defaults to 100. If only page is specified, defaults to a page size of 100. For reports, the default is 100 rows. If you need larger sets of data from your report, returns a maximum of 10,000 rows per request.
          schema:
            type: number
            default: 100
```
