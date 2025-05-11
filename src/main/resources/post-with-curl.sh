#!/bin/bash -x
URL=${1}
BODY=${2}
curl -v\
 -H "Authorization: Bearer ${SMARTSHEET_ACCESS_TOKEN}"\
 -H "Content-Type: application/json"\
 -X POST\
 -d "${BODY}"\
 "${URL}"
