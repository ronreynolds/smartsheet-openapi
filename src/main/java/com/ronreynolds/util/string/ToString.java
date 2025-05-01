package com.ronreynolds.util.string;

import com.ronreynolds.smartsheet.ApiResponse;

import java.io.ByteArrayOutputStream;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Flow;
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

    // java.net.http doesn't seem to provide a public type that can subscribe to the REQUEST side of things :-/
    private static class ByteBufferSubscriber implements Flow.Subscriber<ByteBuffer> {
        private Throwable error;
        private final List<ByteBuffer> buffers = Collections.synchronizedList(new ArrayList<>());

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            subscription.request(Long.MAX_VALUE);   // give us everything (now would be great)
        }

        @Override
        public void onNext(ByteBuffer item) {
            buffers.add(item);
        }

        @Override
        public void onError(Throwable throwable) {
            error = throwable;
        }

        @Override
        public void onComplete() {
        }

        public byte[] getByteArray() throws Throwable {
            if (error != null) {
                throw error;
            }
            var byteStream = new ByteArrayOutputStream();
            buffers.forEach(buffer -> byteStream.writeBytes(buffer.array()));
            return byteStream.toByteArray();
        }

        public String asString() throws Throwable {
            return new String(getByteArray(), StandardCharsets.UTF_8);
        }
    }
}