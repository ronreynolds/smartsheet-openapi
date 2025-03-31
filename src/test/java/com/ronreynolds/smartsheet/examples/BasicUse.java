package com.ronreynolds.smartsheet.examples;

import com.ronreynolds.smartsheet.ApiClient;
import com.ronreynolds.smartsheet.ApiException;
import com.ronreynolds.smartsheet.api.ServerInfoApi;
import com.ronreynolds.smartsheet.api.UsersApi;
import com.ronreynolds.smartsheet.api.util.ApiClients;
import com.ronreynolds.smartsheet.api.util.Sheets;
import com.ronreynolds.smartsheet.model.CellObjectValue;
import com.ronreynolds.smartsheet.model.Sheet;
import com.ronreynolds.util.logging.JULIntoSLF4J;
import lombok.extern.slf4j.Slf4j;

import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
public class BasicUse {
    public static void main(String[] args) {
        // route codegen code logging into slf4j
        JULIntoSLF4J.install();

        ApiClient client = ApiClients.getDefaultClient();
        try {
//            getServerInfo(client);
//            getUserInfo(client);
            getSheet(client);
        } catch (ApiException e) {
            log.error("failure", e);
        }
    }

    private static void getServerInfo(ApiClient client) throws ApiException {
        log.info("server info - {}", new ServerInfoApi(client).serverinfoGet());
    }

    private static void getUserInfo(ApiClient client) throws ApiException {
        UsersApi usersApi = new UsersApi(client);
        var currentUser = usersApi.getCurrentUser(null);
        log.info("current user - {}", currentUser);
        var sameUser = usersApi.getUser(currentUser.getId());
        log.info("same user? - {}", sameUser);
        var userList = usersApi.listUsers(null, null, null, null, null, null, null);
        log.info("user list - {}", userList);
    }

    private static void getSheet(ApiClient client) throws ApiException {
        Logger.getLogger(CellObjectValue.class.getName()).log(Level.INFO, "info log");
        Logger.getLogger(CellObjectValue.class.getName()).log(Level.FINE, "fine log");
        Logger.getLogger(CellObjectValue.class.getName()).log(Level.FINER, "finer log");
        Logger.getLogger(CellObjectValue.class.getName()).log(Level.FINEST, "finest log");
        Sheet sheet = Sheets.getWholeSheet(client, 7290900052922244L);
        log.info("sheet - {}", sheet);
    }
}