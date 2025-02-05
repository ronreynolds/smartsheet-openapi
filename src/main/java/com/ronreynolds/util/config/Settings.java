package com.ronreynolds.util.config;

import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * simple settings that return values from System-props or process Environment (in that order)
 */
public class Settings {
    private Settings() {
    }

    /**
     * @param key - the key by which we want to look up a property or environment variable
     * @return the system-prop or environment-var with the specified key; otherwise null
     */
    @Nullable
    public static String get(@NonNull String key) {
        String value = System.getProperty(key);
        return value != null ? value : System.getenv(key);
    }

    /**
     * a more general-purpose get that converts the String to a type T using the provided converter Function
     *
     * @param key       property key
     * @param converter converts property value String to T
     * @param <T>       the type to return (and the type accepted by the converter)
     * @return the converted property or null
     */
    @Nullable
    public static <T> T get(@NonNull String key, @NonNull Function<String, ? extends T> converter) {
        return converter.apply(get(key));
    }

    /**
     * convenience method for getting boolean properties
     * @param key property key
     * @param ifNull value to return if the key isn't found or its value is null
     * @return the property value as a boolean or {@code ifNull} if property key not found
     */
    public static boolean getBool(@NonNull String key, boolean ifNull) {
        Boolean value = get(key, Boolean::parseBoolean);
        return value != null ? value : ifNull;
    }
}
