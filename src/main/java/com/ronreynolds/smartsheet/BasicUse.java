package com.ronreynolds.smartsheet;

import com.ronreynolds.smartsheet.api.ServerInfoApi;
import com.ronreynolds.smartsheet.api.SheetsApi;
import com.ronreynolds.smartsheet.api.UsersApi;
import com.ronreynolds.smartsheet.api.util.ApiClients;
import com.ronreynolds.smartsheet.api.util.Sheets;
import com.ronreynolds.smartsheet.model.Sheet;
import com.ronreynolds.smartsheet.model.SheetInclude;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

@Slf4j
public class BasicUse {
    public static void main(String[] args) {
        // we need SOMETHING for this because the APIs don't support a null value
        OffsetDateTime modifiedSince = OffsetDateTime.now();
        ApiClient client = ApiClients.getDefaultClient();
        try {
            log.info("server info - {}", new ServerInfoApi(client).serverinfoGet());

            UsersApi usersApi = new UsersApi(client);
            var currentUser = usersApi.getCurrentUser(null);
            log.info("current user - {}", currentUser);
            var sameUser = usersApi.getUser(currentUser.getId());
            log.info("same user? - {}", sameUser);
            var userList = usersApi.listUsers(null, null, null, modifiedSince, null, null, null);
            log.info("user list - {}", userList);

            // WIP
            if (true) {
                SheetsApi sheetsApi = new SheetsApi(client);
                var sheet = sheetsApi.getSheet(7290900052922244L, null, null, List.copyOf(EnumSet.allOf(SheetInclude.class)),
                        null, null, null, null, null, null, null, null, null, null, modifiedSince);
                log.info("sheet - {}", sheet);
            }
        } catch (ApiException e) {
            log.error("failure", e);
        }
    }
}