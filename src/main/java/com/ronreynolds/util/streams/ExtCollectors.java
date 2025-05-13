package com.ronreynolds.util.streams;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * extension to the standard stream.Collectors
 */
public class ExtCollectors {
    private ExtCollectors() {
    }

    /**
     * simple collector for when you have a stream of Map.Entry's to collect to a map
     */
    public static <T extends Map.Entry<K, U>, K, U> Collector<T, ?, Map<K, U>> entriesToMap() {
        return Collectors.toMap(Map.Entry<K, U>::getKey, Map.Entry<K, U>::getValue);
    }
}
