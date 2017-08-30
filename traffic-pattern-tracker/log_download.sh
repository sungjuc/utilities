#!/bin/bash

HOST=$1
DATE=$2
LOG_DIR="/export/content/lid/apps/cloud-session/i001/logs/"
LOG_NAME="cloudsession_public_access.log"
LOG_GZ=".${DATE}*.gz"
echo "Download NetworkCacheLog from ".$HOST

mkdir -p ${HOST}

cd ${HOST}
rm *.*

scp scho@${HOST}:/${LOG_DIR}${LOG_NAME}${LOG_GZ} .
gunzip *.gz

cd ..
