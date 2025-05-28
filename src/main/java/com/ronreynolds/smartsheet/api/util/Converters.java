package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.model.AddRowsObject;
import com.ronreynolds.smartsheet.model.Cell;
import com.ronreynolds.smartsheet.model.CellObjectForRows;
import com.ronreynolds.smartsheet.model.GetCurrentUser200Response;
import com.ronreynolds.smartsheet.model.Row;
import com.ronreynolds.smartsheet.model.UpdateRowsObject;
import com.ronreynolds.smartsheet.model.UserProfile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * a collection of static methods to convert from one type to another
 */
public class Converters {
    private Converters() {
    }

    public static Row convert(AddRowsObject result) {
        return Row.builder()
                .id(result.getId())
                .sheetId(result.getSheetId())
                .rowNumber(result.getRowNumber())
                .version(result.getVersion())
                .expanded(result.getExpanded())
                .createdAt(result.getCreatedAt())
                .modifiedAt(result.getModifiedAt())
                .cells(convert(result.getCells()))
                .build();
    }

    public static Row convert(UpdateRowsObject result) {
        return Row.builder()
                .id(result.getId())
//                .sheetId(result.getSheetId()) - not available in response according to openapi spec :-?
                .rowNumber(result.getRowNumber())
                .version(result.getVersion())
                .expanded(result.getExpanded())
                .createdAt(result.getCreatedAt())
                .modifiedAt(result.getModifiedAt())
                .cells(convert(result.getCells()))
                .build();
    }


    public static List<Cell> convert(List<CellObjectForRows> cellForRowsList) {
        return cellForRowsList == null ? null
                : cellForRowsList.stream().map(Converters::convert).collect(Collectors.toList());
    }

    public static Cell convert(CellObjectForRows cellLite) {
        return Cell.builder()
                .columnId(cellLite.getColumnId())
                .columnType(cellLite.getColumnType())
                .value(cellLite.getValue())
                .displayValue(cellLite.getDisplayValue())
                .build();
    }

    public static UserProfile convert(GetCurrentUser200Response response) {
        return UserProfile.builder()
                .id(response.getId())
                .account(response.getAccount())
                .admin(response.getAdmin())
                .alternateEmails(response.getAlternateEmails())
                .company(response.getCompany())
                .customWelcomeScreenViewed(response.getCustomWelcomeScreenViewed())
                .department(response.getDepartment())
                .email(response.getEmail())
                .firstName(response.getFirstName())
                .groupAdmin(response.getGroupAdmin())
                .jiraAdmin(response.getJiraAdmin())
                .lastLogin(response.getLastLogin())
                .lastName(response.getLastName())
                .licensedSheetCreator(response.getLicensedSheetCreator())
                .locale(response.getLocale())
                .mobilePhone(response.getMobilePhone())
                .profileImage(response.getProfileImage())
                .resourceViewer(response.getResourceViewer())
                .role(response.getRole())
                .salesforceAdmin(response.getSalesforceAdmin())
                .salesforceUser(response.getSalesforceUser())
                .sheetCount(response.getSheetCount())
//                .status(response.getStatus()) status no longer returned?
                .timeZone(response.getTimeZone())
                .title(response.getTitle())
                .workPhone(response.getWorkPhone())
                .build();
    }
}
