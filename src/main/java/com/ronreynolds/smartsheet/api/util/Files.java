package com.ronreynolds.smartsheet.api.util;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Files {
    /**
     * generate the Content-Disposition header to provide with this file
     */
    public static String getAttachmentContentDisposition(File source) {
        return "attachment; filename=\"" + URLEncoder.encode(source.getName(), StandardCharsets.UTF_8) + "\"";
    }
}
