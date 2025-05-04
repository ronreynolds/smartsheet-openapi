package com.ronreynolds.util.io;

/**
 * removes need for a catch when this type is used in a TWR block by overwriting AutoCloseable.close() without throws Exception
 */
@FunctionalInterface
public interface NoThrowAutoCloseable extends AutoCloseable {
    void close();   // no throw
}
