#!/bin/bash
DATE=`date --date="yesterday" +%Y-%m-%d`
echo ${DATE}

# Download dark canary cloud-session log first.
DARK_HOST="lva1-app1804.prod"
./log_download.sh ${DARK_HOST} gz ${DATE}

# Verify dark canary traffic
./liquid_call_id_picker.sh ${DARK_HOST} ${DATE}

# Cleanup logs
rm -rf ${DARK_HOST}
