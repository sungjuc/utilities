#!/bin/bash
echo ${0}

HOST="lor1-app6139.prod"
DATE=`date +%Y-%m-%d -d "1 day ago"`

./log_download.sh ${HOST} ${DATE}
