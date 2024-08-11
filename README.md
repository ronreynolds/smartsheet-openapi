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