package com.ronreynolds.smartsheet;

import com.ronreynolds.smartsheet.api.ServerInfoApi;
import com.ronreynolds.smartsheet.api.SheetsApi;
import com.ronreynolds.smartsheet.api.util.ApiClients;
import com.ronreynolds.smartsheet.api.util.Sheets;
import com.ronreynolds.smartsheet.model.Sheet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicUse {
    public static void main(String[] args) {
        ApiClient client = ApiClients.getDefaultClient();
        try {
//            log.info("server info - {}", new ServerInfoApi(client).serverinfoGet());

            Sheet sheet = Sheets.getWholeSheet(new SheetsApi(client), 7290900052922244L);
            log.info("sheet - {}", sheet);
        } catch (ApiException e) {
            log.error("failure", e);
        }
    }
}
