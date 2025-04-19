package com.ronreynolds.smartsheet.api.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JacksonUtil {
    // same as https://github.com/smartsheet/smartsheet-java-sdk/blob/mainline/src/main/java/com/smartsheet/api/internal/json/JacksonJsonSerializer.java#L81
    static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneId.of("UTC"));
    static final StdSerializer<OffsetDateTime> dateSerializer = new StdSerializer<>(OffsetDateTime.class) {
        @Override
        public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value == null) {
                gen.writeNull();
            } else {
                gen.writeString(DATE_FORMATTER.format(value));
            }
        }
    };
    static final StdDeserializer<OffsetDateTime> dateDeserializer = new StdDeserializer<>(OffsetDateTime.class) {
        @Override
        public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            JsonNode node = parser.getCodec().readTree(parser);
            String dateText = node.asText();
            return ZonedDateTime.parse(dateText, JacksonUtil.DATE_FORMATTER).toOffsetDateTime();
        }
    };

    public static JsonMapper.Builder modifyObjectMapper(ObjectMapper mapper) {
        JsonMapper.Builder builder = JsonMapper.builder(mapper.getFactory());
        // disable type coercion of values; breaks parsing of more complex objects
        builder.disable(MapperFeature.ALLOW_COERCION_OF_SCALARS);
        // add our custom date handlers
        builder.addModules(new SimpleModule()
                .addSerializer(dateSerializer)
                .addDeserializer(OffsetDateTime.class, dateDeserializer));
        return builder;
    }
}
