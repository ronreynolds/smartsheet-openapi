package com.ronreynolds.smartsheet.api.util;

import com.ronreynolds.smartsheet.model.Cell;
import com.ronreynolds.smartsheet.model.CellBrief;
import com.ronreynolds.smartsheet.model.GetCurrentUser200Response;
import com.ronreynolds.smartsheet.model.Row;
import com.ronreynolds.smartsheet.model.RowsAddToSheet200ResponseAllOfResultInner;
import com.ronreynolds.smartsheet.model.UpdateRows200ResponseAllOfResultInner;
import com.ronreynolds.smartsheet.model.UserProfile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * a collection of static methods to convert from one type to another
 */
public class Converters {
    private Converters() {
    }

    public static Row convert(RowsAddToSheet200ResponseAllOfResultInner result) {
        return new Row()
                .id(result.getId())
                .sheetId(result.getSheetId())
                .rowNumber(result.getRowNumber())
                .version(result.getVersion())
                .expanded(result.getExpanded())
                .createdAt(result.getCreatedAt())
                .modifiedAt(result.getModifiedAt())
                .cells(convert(result.getCells()));
    }

    public static Row convert(UpdateRows200ResponseAllOfResultInner result) {
        return new Row()
                .id(result.getId())
//                .sheetId(result.getSheetId()) - not available in response according to openapi spec :-?
                .rowNumber(result.getRowNumber())
                .version(result.getVersion())
                .expanded(result.getExpanded())
                .createdAt(result.getCreatedAt())
                .modifiedAt(result.getModifiedAt())
                .cells(convert(result.getCells()));
    }


    public static List<Cell> convert(List<CellBrief> cellLiteList) {
        if (cellLiteList == null) {
            return null;
        }
        return cellLiteList.stream().map(Converters::convert).collect(Collectors.toList());
    }

    public static Cell convert(CellBrief cellLite) {
        return new Cell()
                .columnId(cellLite.getColumnId())
                .columnType(cellLite.getColumnType())
                .value(cellLite.getValue())
                .displayValue(cellLite.getDisplayValue());
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
                .status(response.getStatus())
                .timeZone(response.getTimeZone())
                .title(response.getTitle())
                .workPhone(response.getWorkPhone())
                .build();
    }
}
