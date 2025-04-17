# Smartsheet OpenApi-Generated SDK

## Purpose
the purpose of this project is to replace the need for the various language-specific Smartsheet-SDK projects with one
generated from the OpenAPI spec file.  the current OpenAPI spec file at https://smartsheet.redoc.ly/ is unusable as-is
so this project contains a heavily modified version of that OpenAPI spec and a gradle build script that will generate
the Java classes needed to invoke the Smartsheet API.

## Changes
* [Specification Changes](docs/spec-changes.md)
* [OpenAPI-Codegen Changes](docs/generator-changes.md)

## Other Docs
* [Integration Tests](docs/integration-tests.md)