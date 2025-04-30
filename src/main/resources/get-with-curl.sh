#!/bin/bash -x
URL=${1}
curl -v -H "Authorization: Bearer ${SMARTSHEET_ACCESS_TOKEN}" "${URL}"