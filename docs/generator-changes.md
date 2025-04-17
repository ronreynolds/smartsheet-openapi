## OpenAPI-CodeGen changes
the OpenAPI-codegen project's code-generator behavior can be altered via `mustache` files (one of the many flavors of template
files to which metadata is applied to generate output files; Java in this case).

`mustache` files are stored in `src/main/resources/templates` in this project and contain the following files for the following
reasons:
* `AbstractOpenApiSchema.mustache`
  * base type for all generated `any-of` or `one-of` types
  * added a `actualInstance(Object):T` for fluent setting of the actual instance of the derived type
* `api.mustache`
  * used to generate all API-domain-specific classes that end with "Api"
  * add `ApiClient` to instance so it can be extracted via `getApiClient():ApiClient`
    * makes consistent with other library's generator code
* `ApiClient.mustache`
  * used to generate the `ApiClient.java` which wraps the request-, response- and JSON-handling logic and some utils
  * forcing the date format to match the SDK and API's format of `"yyyy-MM-dd'T'HH:mm:ss'Z'"`
  * some minor code cleanup (to be submitted to the OpenAPI-codegen project)
* `oneof_model.mustache`
  * implementation template for all `one-of` types in the schema
  * performance improvements around JSON deserializing (processing JSON server-response into fields of this type)
    * using a `Set<Class<?>>` rather than an if-else chain for primitive types
    * some minor code-cleanup of `deserialize(JsonParser, DeserializationContext):This`
      * using final fields were possible
      * reducing repeat method calls
      * using `actualInstance()` method added to `AbstractOpenApiSchema`
      * cleanup of some logging code
        * including logging all types that matched a particular input (this was key to debugging a JSON parsing issue)