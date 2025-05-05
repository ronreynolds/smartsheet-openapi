package com.ronreynolds.util.string;

import com.ronreynolds.smartsheet.ApiResponse;
import com.ronreynolds.util.flow.ByteBufferSubscriber;

import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * utility class to convert HttpRequest, ApiResponse, and other objects into a String representation
 */
public class ToString {
    private ToString() {
    }

    public static CharSequence toString(HttpRequest request) {
        Optional<BodyPublisher> bodyPublisher = request.bodyPublisher();
        long bodyLength = 0L;
        String bodyContent = "<none>";
        BodyPublisher body = bodyPublisher.orElse(null);
        if (body != null) {
            bodyLength = body.contentLength();
            ByteBufferSubscriber subscriber = new ByteBufferSubscriber();
            body.subscribe(subscriber);
            try {
                bodyContent = subscriber.asString();
            } catch (Throwable fail) {
                bodyContent = fail.toString();
            }
        }
        return new StringBuilder()
                .append("  method:").append(request.method())
                .append("\n version:").append(request.version())
                .append("\n timeout:").append(request.timeout())
                .append("\n     uri:").append(request.uri())
                .append("\ncontinue:").append(request.expectContinue())
                .append("\n headers:").append(request.headers())
                .append("\n bodyLen:").append(bodyLength)
                .append("\n    body:").append(bodyContent);
    }

    public static CharSequence toString(ApiResponse<?> response) {
        return new StringBuilder()
                .append("\nheaders:").append(joinStringsWith(response.getHeaders().entrySet().stream(), "\n\t"))
                .append("\n   data:").append(response.getData());
    }

    public static CharSequence toString(Object o) {
        // TODO - consider adding a reflective to-string mechanism
        return String.valueOf(o);
    }

    public static <T> String joinStringsWith(Stream<T> stream, String delimiter) {
        return stream.map(T::toString).collect(Collectors.joining(delimiter));
    }

    public static <T> String joinWithSpace(Stream<T> stream) {
        return joinStringsWith(stream, " ");
    }

    /** return enough info to get a deeper view of the object itself (useful for types with "dumb-down" toString()s) */
    public static String deepToString(Object o) {
        return o == null ? "null" : o.getClass() + "@" + System.identityHashCode(o) + " = " + o;
    }
}