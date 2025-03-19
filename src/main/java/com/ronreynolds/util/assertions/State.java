package com.ronreynolds.util.assertions;

/**
 * collection of assertion utility methods that throw IllegalStateException if the assertion fails
 */
public class State {
    private State() {
    }

    /**
     * @param mustBeTrue condition that must be true
     * @param msgFormat  String.format pattern used to generate exception message
     * @param msgArgs    String.format args used to generate exception message
     * @throws IllegalStateException throw if `mustBeTrue` is false
     */
    public static void isTrue(boolean mustBeTrue, String msgFormat, Object... msgArgs) throws IllegalStateException {
        if (!mustBeTrue) {
            throw new IllegalStateException(String.format(msgFormat, msgArgs));
        }
    }

    /**
     * assert that a value is not null
     *
     * @param val the value to test for null
     * @param <T> the type of the value to test
     * @return the value passed in if null; otherwise there is no return
     * @throws IllegalStateException if the value is null
     */
    public static <T> T notNull(T val) throws IllegalStateException {
        return notNull(val, "null value");
    }

    /**
     * assert that a value is not null; use provided exception message args to create exception if value is null
     *
     * @param val       the value to test for null
     * @param msgFormat the String.format pattern to use for the exception message
     * @param msgArgs   the String.format args to use for the exception message
     * @param <T>       the type of the value to test
     * @return the value if it is not null; otherwise there is no return
     * @throws IllegalStateException if the value is null; exception message created via String.format with provided pattern and args
     */
    public static <T> T notNull(T val, String msgFormat, Object... msgArgs) throws IllegalStateException {
        if (val == null) {
            throw new IllegalStateException(String.format(msgFormat, msgArgs));
        }
        return val;
    }
}
