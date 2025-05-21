package com.ronreynolds.util.string;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/**
 * a minimal version of commons-lang3's StringUtils (to avoid hauling in the whole library for this one really useful class)
 * this version was actually stolen from Micrometer's version (which is probably based on common-lang's version)
 */
public final class StringUtils {
    private StringUtils() {
    }

    public static boolean isBlank(@Nullable String string) {
        if (isEmpty(string)) {
            return true;
        }
        for (int i = 0; i < string.length(); ++i) {
            if (!Character.isWhitespace(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(@Nullable String string) {
        return !isBlank(string);
    }

    public static boolean isEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNotEmpty(@Nullable String string) {
        return !isEmpty(string);
    }

    public static String truncate(String string, int maxLength) {
        return string.length() > maxLength ? string.substring(0, maxLength) : string;
    }

    public static String truncate(String string, int maxLength, String truncationIndicator) {
        if (truncationIndicator.length() >= maxLength) {
            throw new IllegalArgumentException("maxLength must be greater than length of truncationIndicator");
        }
        if (string.length() > maxLength) {
            int remainingLength = maxLength - truncationIndicator.length();
            return string.substring(0, remainingLength) + truncationIndicator;
        }
        return string;
    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; ++j) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    private static final char[] RANDOM_ASCII_CHARS =
            "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~"
                    .toCharArray();

    public static CharSequence randomASCIIStringOfLength(int length) {
        final StringBuilder buf = new StringBuilder(length);
        ThreadLocalRandom.current()
                .ints(length, 0, RANDOM_ASCII_CHARS.length)
                .forEach(i -> buf.append(RANDOM_ASCII_CHARS[i]));
        return buf;
    }

    public static String toUpperCase(String val) {
        return val != null ? val.toUpperCase() : null;
    }

    public static String toLowerCase(String val) {
        return val != null ? val.toLowerCase() : null;
    }

    public static String prune(String prefix, String valueToPrune) {
        if (isBlank(valueToPrune) || !valueToPrune.startsWith(prefix)) return valueToPrune;
        return valueToPrune.substring(prefix.length());
    }

    public static String appendIfMissing(String value, String suffix) {
        return value == null || value.endsWith(suffix) ? value : value + suffix;
    }
}