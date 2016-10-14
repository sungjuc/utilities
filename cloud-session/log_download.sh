#!/bin/bash

HOST=$1
OPT=$2
LOG_DIR="/export/content/lid/apps/cloud-session/i001/logs/"
LOG_NAME="cloudsession_public_access.log"
LOG_GZ=".*.gz"
echo "Download NetworkCacheLog from ".$HOST

mkdir -p ${HOST}

cd ${HOST}

if [[ ${OPT} == "gz" ]]
then 
  scp scho@${HOST}:/${LOG_DIR}${LOG_NAME}${LOG_GZ} .
  ../file_process.sh
  cd ..
else
  scp scho@${HOST}:/${LOG_DIR}${LOG_NAME} .
fi
