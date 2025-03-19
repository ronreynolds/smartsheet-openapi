package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.ApiClient;
import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.WorkspacesApi;
import com.ronreynolds.smartsheet.model.Workspace;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class Workspaces {
    private Workspaces() {
    }

    /**
     * @return the first Workspace with the provided name; Optional.empty if no workspace has the provided name
     */
    public static Optional<Workspace> findWorkspaceByName(@NonNull ApiClient api, @NonNull String name) throws ApiException {
        try {
            return new WorkspacesApi(api)
                    .listWorkspaces(null, true, null, null)
                    .getData()
                    .stream()
                    .filter(ws -> name.equals(ws.getName()))
                    .findFirst();
        } catch (NullPointerException gotNulls) {
            log.warn("{}", gotNulls, gotNulls);
            return Optional.empty();
        }
    }
}