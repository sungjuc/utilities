#!/bin/bash

HOST=$1
OPT=$2
TIME=${3}

LOG_DIR="/export/content/lid/apps/cloud-session/i001/logs/"
LOG_NAME="cloudsession.log"
PUBLIC_ACCESS_LOG_NAME="cloudsession_public_access.log"
ALL=".*"
LOG_GZ=".gz"
if [[ ${OPT} == "pub" ]]
then 
  echo "Download ${PUBLIC_ACCESS_LOG_NAME} from "$HOST
else
  echo "Download ${LOG_NAME} from "$HOST
fi

mkdir -p ${HOST}

cd ${HOST}

if [[ ${OPT} == "pub" ]]
then
  scp scho@${HOST}:/${LOG_DIR}${PUBLIC_ACCESS_LOG_NAME}.${TIME}${LOG_GZ} .
elif [[ ${OPT} == "gz" ]]
then
  SELECT=${ALL}
  if [[ ${TIME} != "" ]]
  then
    SELECT=".${TIME}"
  fi  
  scp scho@${HOST}:/${LOG_DIR}${LOG_NAME}${SELECT}${LOG_GZ} .
  ../file_process.sh
else
  scp scho@${HOST}:/${LOG_DIR}${LOG_NAME} .
fi

cd ..
