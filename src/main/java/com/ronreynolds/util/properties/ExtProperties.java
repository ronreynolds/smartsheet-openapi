package com.ronreynolds.util.properties;

import com.ronreynolds.smartsheet.api.util.DateTimes;
import com.ronreynolds.util.streams.ExtCollectors;
import com.ronreynolds.util.string.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * extension to the Properties class to support groups of keys and ways to create objects and collections from them
 */
@Slf4j
public class ExtProperties {
    /**
     * loads properties from the provided file path
     * @param filePath path to local file to read into properties
     * @return {@code ExtProperties} containing values read from {@code filePath}
     * @throws RuntimeException if there are any issues finding or loading the file
     */
    public static ExtProperties load(String filePath) {
        try {
            return load(new FileReader(filePath), null).orElseThrow(RuntimeException::new);
        } catch (IOException failure) {
            handleLoadFailure(failure, null);
            throw new RuntimeException("impossible to reach this code", failure);
        }
    }

    public static Optional<ExtProperties> load(Reader source, Consumer<IOException> onFailure) {
        try {
            Properties props = new Properties();
            props.load(source);
            return Optional.of(new ExtProperties(props));
        } catch (IOException failure) {
            handleLoadFailure(failure, onFailure);
        }
        return Optional.empty();
    }

    public static Optional<ExtProperties> load(InputStream source, Consumer<IOException> onFailure) {
        try {
            Properties props = new Properties();
            props.load(source);
            return Optional.of(new ExtProperties(props));
        } catch (IOException failure) {
            handleLoadFailure(failure, onFailure);
        }
        return Optional.empty();
    }

    public static Optional<ExtProperties> loadXML(InputStream source, Consumer<IOException> onFailure) {
        try {
            Properties props = new Properties();
            props.loadFromXML(source);
            return Optional.of(new ExtProperties(props));
        } catch (IOException failure) {
            handleLoadFailure(failure, onFailure);
        }
        return Optional.empty();
    }

    private static void handleLoadFailure(IOException failure, Consumer<IOException> onFailure) {
        if (onFailure != null) {
            onFailure.accept(failure);
        } else {
            throw new RuntimeException(failure);
        }
    }

    private final Map<String, String> properties;

    public ExtProperties(Properties properties) {
        // since Properties is mutable we want to protect ourselves from state change from outside this object
        this.properties = new HashMap<>(properties.size());
        properties.forEach((k, v) -> this.properties.put((String) k, (String) v));
    }

    /**
     * @param name name of a property to look up; must NOT be null or blank
     * @return the value of the specified property (or null if the property key is not found)
     */
    public String get(String name) {
        return properties.get(name);
    }

    /**
     * @param name      property name
     * @param converter converts property value (String) into {@code T} (will be called even if property value is null)
     * @param <T>       the type requested
     * @return the {@code T} returned by converter for the value of the specified property (which could be null)
     * @throws NullPointerException if the {@code converter} is null
     */
    public <T> T get(String name, Function<String, T> converter) {
        return Objects.requireNonNull(converter).apply(get(name));
    }

    /**
     * @param name name of a property to look up; must NOT be null or blank
     * @return an {@code OffsetDateTime} created from the value of the property with key {@code name} or null if property value
     * is null or blank
     * @throws java.time.format.DateTimeParseException if the value can't be parsed (yyyy-MM-dd'T'HH:mm:ss'Z'; e.g.,
     *                                                 2025-05-17T07:50:00Z)
     */
    public OffsetDateTime getDate(String name) {
        return DateTimes.parseToOffset(get(name));
    }

    /**
     * @param name name of a property to look up; must NOT be null or blank
     * @return the value converted to a {@code long} or null if the value is null or blank
     * @throws NumberFormatException if the value isn't a properly formatted long
     */
    public long getLong(String name) {
        String value = get(name);
        return StringUtils.isNotBlank(value) ? Long.parseLong(value) : null;
    }

    /**
     * @param namePrefix property name starts-with
     * @return {@code Map<String,String>} of all key-value pairs with keys starting with {@code namePrefix}
     */
    public Map<String, String> getPairsStartingWith(String namePrefix) {
        // matches all names that start with the prefix
        return properties.keySet().stream()
                .filter(key -> key.startsWith(namePrefix))
                .map(key -> Map.entry(key, get(key)))
                .collect(ExtCollectors.entriesToMap());
    }

    public <T> T getObject(String keyPrefix, Function<Map<String, String>, T> converter) {
        Objects.requireNonNull(converter);
        // converter expects keys without prefix
        Map<String, String> pairs = pruneKeys(StringUtils.appendIfMissing(keyPrefix, "."), getPairsStartingWith(keyPrefix));
        return converter.apply(pairs);
    }

    public static <V> Map<String, V> pruneKeys(String prefixToPrune, Map<String, V> map) {
        return map.entrySet().stream()
                .map(entry -> Map.entry(StringUtils.prune(prefixToPrune, entry.getKey()), entry.getValue()))
                .collect(ExtCollectors.entriesToMap());
    }

    /**
     * @param keyPrefix         property name starts-with
     * @param collectionFactory returns instance of collection to populate
     * @param valueConverter    converts {@code String} property values into type {@code T}
     * @param <T>               the type of object to return
     * @param <CollectionT>     the type of collection of {@code T} to return
     * @return a collection of {@code T} objects created from the values of properties with keys starting with {@code keyPrefix}
     * @throws NullPointerException if {@code collectionFactory} or {@code valueConverter} is null
     */
    public <T, CollectionT extends Collection<T>> CollectionT getValuesCollection(
            String keyPrefix, Supplier<CollectionT> collectionFactory, Function<String, T> valueConverter) {
        Objects.requireNonNull(valueConverter);
        Objects.requireNonNull(collectionFactory);
        return getPairsStartingWith(keyPrefix).values().stream()
                .map(valueConverter)
                .collect(Collectors.toCollection(collectionFactory));
    }


    /**
     * generate a collection of T given their property key prefix and a factory method to convert the Map of key-value pairs to T.
     * properties for the same object are grouped via one or more digits following the key prefix; e.g., "Foo.1.fieldOfFoo = ..."
     *
     * @param keyPrefix         key prefix to find all properties to use; property keys must be of the form "PREFIX.DIGITS.FIELD"
     * @param collectionFactory returns instance of collection to populate
     * @param itemFactory       converts {@code Map<String,String>} of key-value pairs into type {@code T}
     * @param <T>               the type of the object created from the property groups
     * @return a {@code List<T>} containing all T created from all properties with {@code keyPrefix} grouped by DIGIT(S)
     * @throws NullPointerException if the {@code collectionFactory} or {@code itemFactory} are null
     */
    public <T, CollectionT extends Collection<T>> CollectionT getObjectsCollection(
            String keyPrefix, Supplier<CollectionT> collectionFactory, Function<Map<String, String>, T> itemFactory) {
        Objects.requireNonNull(collectionFactory);
        Objects.requireNonNull(itemFactory);

        Map<String, String> properties = getPairsStartingWith(keyPrefix + ".");
        Pattern keyPattern = Pattern.compile(Pattern.quote(keyPrefix).concat("\\.(\\d+)\\.(.+)"));

        // gather up all the key-value pairs by digit suffix
        Map<Integer, Map<String, String>> mapsByDigit = new HashMap<>();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            Matcher mat = keyPattern.matcher(entry.getKey());
            if (mat.matches()) {
                Integer digits = Integer.parseInt(mat.group(1));
                String field = mat.group(2);
                Map<String, String> keyValuePairs = mapsByDigit.computeIfAbsent(digits, ignore -> new HashMap<>());
                keyValuePairs.put(field, entry.getValue());
            } else {
                log.warn("key '{}' didn't match expect key-pattern '{}'", entry.getKey(), keyPattern.pattern());
            }
        }
        return mapsByDigit.values().stream()
                .map(itemFactory)
                .collect(Collectors.toCollection(collectionFactory));
    }

}
