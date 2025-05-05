package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.ApiClient;
import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.FoldersApi;
import com.ronreynolds.smartsheet.model.Folder;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * utility methods for working with Folders in the Smartsheet API
 */
@SuppressWarnings("unused")
public class Folders {
    private Folders() {
    }

    /**
     * return the first folder found with the provided name
     */
    public static Optional<Folder> findFirstFolderByName(@NonNull ApiClient api, @NonNull String name) throws ApiException {
        return new FoldersApi(api)
                .listFolders(null, true, null, null)
                .getData()
                .stream()
                .filter(ws -> name.equals(ws.getName()))
                .findFirst();
    }

    /**
     * return all the folders with the specified name
     */
    public static List<Folder> findFoldersByName(@NonNull ApiClient api, @NonNull String name) throws ApiException {
        return new FoldersApi(api)
                .listFolders(null, true, null, null)
                .getData()
                .stream()
                .filter(ws -> name.equals(ws.getName()))
                .collect(Collectors.toList());
    }

    public static Folder populateIfNeeded(@NonNull ApiClient api, @NonNull Folder folder) throws ApiException {
        // it seems that if the folder is populated then the folders list is non-null
        if (folder.getFolders() == null) {
            Folder folderData = new FoldersApi(api).getFolder(folder.getId(), Constants.allFolderIncludes);
            if (folderData != null) {
                // even tho the ref within an Optional is immutable our Folder type is not
                folder.setFolders(folderData.getFolders());
                folder.setReports(folderData.getReports());
                folder.setSheets(folderData.getSheets());
//                folder.setSights(folderData.getSights());
                folder.setTemplates(folderData.getTemplates());
            }
        }
        return folder;
    }
}
