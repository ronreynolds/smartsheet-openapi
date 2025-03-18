package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.ApiClient;
import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.AttachmentsApi;
import com.ronreynolds.smartsheet.model.Attachment;
import com.ronreynolds.smartsheet.model.Sheet;
import com.ronreynolds.util.assertions.State;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class Attachments {
    private Attachments() {
    }

    /**
     * builds a map of all attachments on the specified sheet-id
     */
    @NonNull
    public static <K> Map<K, Attachment> getAttachmentMap(@NonNull ApiClient client, long sheetId,
                                                          @NonNull Function<Attachment, K> keyMapper)
            throws ApiException {
        return buildMap(new AttachmentsApi(client).attachmentsListOnSheet(sheetId, null, null, true).getData(), keyMapper);
    }

    /**
     * builds a map of all attachments on the specified sheet (uses sheet-attachments if available; fetches them if not)
     */
    @NonNull
    public static <K> Map<K, Attachment> getAttachmentMap(@NonNull ApiClient client, @NonNull Sheet sheet,
                                                          @NonNull Function<Attachment, K> keyMapper)
            throws ApiException {
        List<Attachment> attachments = sheet.getAttachments();
        if (attachments == null) {
            attachments = new AttachmentsApi(client).attachmentsListOnSheet(sheet.getId(), null, null, true).getData();
        }
        return buildMap(attachments, keyMapper);
    }

    /**
     * converts a list of Attachment objects into a map keyed by whatever the keyMapper returns as the key
     */
    @NonNull
    public static <K> Map<K, Attachment> buildMap(Collection<Attachment> attachments, @NonNull Function<Attachment, K> keyMapper) {
        if (attachments == null) {
            return Map.of();
        }
        return attachments.stream()
                .collect(Collectors.toMap(
                        keyMapper,      // the key is provided by the keyMapper lambda (normally the name of the attachment)
                        (a) -> a,       // the value is the attachment
                        (a1, a2) -> {   // if there are duplicates we want to know
                            log.warn("duplicate attachments found - {} & {}", a1.getName(), a2.getName());
                            return a1;  // use the first (arbitrary choice)
                        }));

    }

    public static Attachment addSheetAttachment(@NonNull ApiClient client, long sheetId, String contentType, @NonNull File source)
            throws ApiException {
        return new AttachmentsApi(client).attachmentsAttachToSheet(sheetId, contentType, source).getResult();
    }

    public static Attachment addSheetRowAttachment(@NonNull ApiClient client, long sheetId, long rowId, String contentType,
                                                   @NonNull File source)
            throws ApiException {
        return new AttachmentsApi(client).rowAttachmentsAttachFile(sheetId, rowId, contentType, source).getResult();
    }

    /**
     * downloads an attachment to a local file
     *
     * @param dir        the directory into which to place the file (name based on name of attachment)
     * @param attachment the attachment to download
     * @param fileCb     callback to receive File created (even if download fails)
     * @return the number of bytes downloaded
     * @throws IOException if anything goes wrong
     */
    public static long downloadToDir(File dir, Attachment attachment, @NonNull Consumer<File> fileCb) throws IOException {
        File file = new File(dir, State.notNull(attachment.getName(), "null attachment name"));
        fileCb.accept(file);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            return Files.copy(Paths.get(State.notNull(attachment.getUrl(), "null attachment url")), fos);
        }
    }

    public static <K> String toString(Map<K, Attachment> attachmentMap) {
        StringBuilder buf = new StringBuilder(attachmentMap.size() * 100);
        for (Map.Entry<K, Attachment> entry : attachmentMap.entrySet()) {
            buf.append(entry.getKey()).append(':').append(toString(entry.getValue())).append('\n');
        }
        return buf.toString();
    }

    public static String toString(Attachment attachment) {
        return String.valueOf(attachment);
    }
}