package com.ronreynolds.smartsheet;

import com.ronreynolds.smartsheet.api.ServerInfoApi;
import com.ronreynolds.smartsheet.api.util.ApiClients;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicUse {
    public static void main(String[] args) {
        ServerInfoApi api = new ServerInfoApi(ApiClients.getDefaultClient());
        try {
            log.info("server info - {}", api.serverinfoGet());
        } catch (ApiException e) {
            log.error("failure to get server info", e);
        }
    }
}
