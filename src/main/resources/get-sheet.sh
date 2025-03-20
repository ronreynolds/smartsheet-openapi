#!/bin/bash -x
SHEET_ID=${1}
ACCEPTS="application/json, application/pdf, application/vnd.ms-excel, text/csv"
ALL_INCLUDES="attachments%2CcolumnType%2CcrossSheetReferences%2Cdiscussions%2Cfilters%2CfilterDefinitions%2Cformat%2CganttConfig%2CobjectValue%2CownerInfo%2CrowPermalink%2Csource%2CwriterInfo"

curl -v -H "Authorization: Bearer ${SMARTSHEET_ACCESS_TOKEN}" \
 -H "Accept: ${ACCEPTS}" \
 "https://api.smartsheet.com/2.0/sheets/${SHEET_ID}?include=${ALL_INCLUDES}"