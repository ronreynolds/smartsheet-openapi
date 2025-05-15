## JSON processing changes
due to some complexities of the spec (many value types for a single field) and the behavior of the generated OpenAPI code some
tweaks had to be applied to the Jackson `ObjectMapper` used to process the JSON responses.

### ApiClients.createNewClient()
this is the factory method registered via `Configuration.setApiClientFactory()` to allow us to customize the `ApiClient` used by
all code to access the API (that doesn't choose to create their own custom `ApiClient` directly, of course).  because of the
complexities of multiple value types for `Cell.objectValue` we need to disable coercion of scalar types in Jackson (because 
multiple types match the values received from the server; basically there are too many types that match a particular value).  to
that end the above method contains:

```java
        client.setObjectMapper(JacksonUtil.modifyObjectMapper(client.getObjectMapper()).build());
```

`JacksonUtil.modifyObjectMapper` makes a few critical changes to the `ObjectMapper` passed in:
1. disable `MapperFeature.ALLOW_COERCION_OF_SCALARS` to eliminate duplicate mappings for response fields with multiple value types
1. register custom serializer/deserializer pair for `OffsetDateTime` to handle ISO-8601 format returned by Smartsheet API
1. enable `DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY` to address `share-sight` complexity of `result` that's `one-of(Share, ShareArray)`
   1. easier to just specify that it's an array and tell Jackson to interpret a single as an array of 1
1. enable `StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` to make it MUCH easier to debug JSON-parsing failures 

