package com.ronreynolds.smartsheet.api.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.OffsetDateTime;

/**
 * collection of utility methods for dealing with specific aspects of Jackson (mostly mods needed to make JSON work with
 * Smartsheet API)
 */
public class JacksonUtil {
    static final StdSerializer<OffsetDateTime> dateSerializer = new StdSerializer<>(OffsetDateTime.class) {
        @Override
        public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value == null) {
                gen.writeNull();
            } else {
                gen.writeString(DateTimes.format(value));
            }
        }
    };
    static final StdDeserializer<OffsetDateTime> dateDeserializer = new StdDeserializer<>(OffsetDateTime.class) {
        @Override
        public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            JsonNode node = parser.getCodec().readTree(parser);
            String dateText = node.asText();
            return DateTimes.parseToOffset(dateText);
        }
    };

    public static JsonMapper.Builder modifyObjectMapper(ObjectMapper mapper) {
        JsonMapper.Builder builder = JsonMapper.builder(mapper.getFactory());

        builder.disable(MapperFeature.ALLOW_COERCION_OF_SCALARS)        // type coercion breaks parsing of more complex objects
                .serializationInclusion(JsonInclude.Include.NON_NULL)   // don't send fields with null values
                .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)  // excellent for debugging JSON responses
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)    // handle responses that contain both single and array
        ;

        // add our custom date handlers
        builder.addModules(new SimpleModule()
                .addSerializer(dateSerializer)
                .addDeserializer(OffsetDateTime.class, dateDeserializer));

        return builder;
    }
}
