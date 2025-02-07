package com.ronreynolds.util.string;

import com.ronreynolds.smartsheet.ApiResponse;

import java.net.http.HttpRequest;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * utility class to convert HttpRequest, ApiResponse, and other objects into a String representation
 */
public class ToString {
    public static CharSequence toString(HttpRequest request) {
        StringBuilder buf = new StringBuilder();
        buf
                .append("    method:").append(request.method())
                .append("\n   version:").append(request.version())
                .append("\n   timeout:").append(request.timeout())
                .append("\n       uri:").append(request.uri())
                .append("\nexpectCont:").append(request.expectContinue())
                .append("\n   headers:").append(request.headers())
                .append("\nbodyLength:").append(request.bodyPublisher().map(HttpRequest.BodyPublisher::contentLength).orElse(0L));
        return buf;
    }

    public static CharSequence toString(ApiResponse<?> response) {
        StringBuilder buf = new StringBuilder();
        buf
                .append("\nheaders:").append(response.getHeaders().entrySet().stream()
                        .map(Map.Entry::toString).collect(Collectors.joining("\n\t")))
                .append("\n   data:").append(response.getData());
        return buf;
    }

    public static CharSequence toString(Object o) {
        // TODO - consider adding a reflective to-string mechanism
        return String.valueOf(o);
    }

    public static <T> String joinWithSpace(Stream<T> stream) {
        return stream.map(T::toString).collect(Collectors.joining(" "));
    }
}