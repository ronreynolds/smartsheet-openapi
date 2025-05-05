package com.ronreynolds.util.flow;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * java.net HttpClient requests collect ByteBuffers for the request body; this allows us to view them
 */
public class ByteBufferSubscriber extends AbstractSubscriber<ByteBuffer> {
    // list for collecting ByteBuffers
    private final List<ByteBuffer> buffers = new ArrayList<>();

    @Override
    public void onNext(ByteBuffer item) {
        buffers.add(item);
    }

    /**
     * @return all the bytes received by this subscriber
     * @throws Throwable if any error was provided instead of a ByteBuffer
     */
    public byte[] getByteArray() throws Throwable {
        if (getError() != null) {
            throw getError();
        }
        var byteStream = new ByteArrayOutputStream();
        buffers.stream()
                .map(ByteBuffer::array)
                .forEachOrdered(byteStream::writeBytes);
        return byteStream.toByteArray();
    }

    /**
     * convenience method for getting the bytes back as a UTF-8 string; obviously this won't work for general binary data
     * @return a UTF-8 string of the bytes collected
     * @throws Throwable if anything went wrong during the collection
     */
    public String asString() throws Throwable {
        return new String(getByteArray(), StandardCharsets.UTF_8);
    }
}
