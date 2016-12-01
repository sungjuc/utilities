#!/bin/bash
if [ $# -le 0 ]; 
then
  echo "Usage: ${0} DIRECTORY" >&2
  exit 1
fi

KEY_WORD="LIQUID"

DIRECTORY=${1}
DATE=${2}
PATH_LOG="/export/content/lid/apps/cloud-session/i001/logs/cloudsession_public_access.log"

#TMPFILE="${0}.tmp"
#./file_touch.sh ${TMPFILE}

SUMMARY_FILE="migration_log.${DATE}.summary"
LIQUID_LOG_FILE="migration_log.${DATE}.liquid_traffic"
CLOUD_LOG_FILE="migration_log.${DATE}.cloud_traffic"
./file_touch.sh ${SUMMARY_FILE}
./file_touch.sh ${LIQUID_LOG_FILE}
./file_touch.sh ${CLOUD_LOG_FILE}

echo "Processing logs in ${DIRECTORY}"

ODATE=""
OTIME=""
OTAG=""
CALL_LATENCY=""
CLOUD_LATENCY=""
LIQUD_LATENCY=""
COUNT=0

for entry in "${DIRECTORY}"/*
do
  #grep "${KEY_WORD}" ${entry} >> ${TMPFILE}
  LOG_DATE=`echo ${entry} | cut -d "." -f4`
  echo "XXX: ${LOG_DATE} vs. ${DATE}"
  if [[ ${DATE} != "" ]] && [[ ${DATE} == ${LOG_DATE} ]]
  then
    grep "${KEY_WORD}" ${entry} | while read -r line
    do
      echo "XXX: ${line}"
      DATE=`echo ${line} | cut -d " " -f1 | tr '/' '-'`
      TIME=`echo ${line} | cut -d " " -f2 | cut -d ":" -f1`
      TAG=`echo ${line} | cut -d " " -f7 | tr '[' ' ' | tr ']' ' '`
      if [[ ${line} == *"time"* ]]
        CALL_LATENCY=`echo ${line} | cut -d " " -f11`
        echo "XXXXX: ${CALL_LATENCY}"
        OTAG=""
        COUNT=`expr ${COUNT} + 1`
      then
        #if [[ ${DATE} != ${ODATE} ]] || [[ ${TIME} != ${OTIME} ]]
        #then
          #echo "./log_download.sh ${DIRECTORY} pub ${DATE}-${TIME}"
          #./log_download.sh ${DIRECTORY} pub ${DATE}-${TIME}
        D_LOG=`ssh ${DIRECTORY} zgrep "${TAG}" ${PATH_LOG}.${DATE}-${TIME}.gz &`

        O_HOST=`echo ${D_LOG} | cut -d " " -f3 | cut -d "(" -f2 | cut -d "," -f1`
        O_TAG=`echo ${D_LOG} | cut -d "=" -f1 | cut -d ":" -f4`
        O_LOG=`ssh ${O_HOST}.prod zgrep "${O_TAG}" ${PATH_LOG}.${DATE}-${TIME}.gz &`

        LIQUID_LATENCY=`echo ${D_LOG} | grep -oh "in .*ms" | cut -d " " -f2 | sed 's/ms//g'`
        CLOUD_LATENCY=`echo ${O_LOG} | grep -oh "in .*ms" | cut -d " " -f2 | sed 's/ms//g'`

        echo "XXX ----------"
        echo ${D_LOG} >> ${LIQUID_LOG_FILE}
        echo "XXX ----------"
        echo ${O_LOG} >> ${CLOUD_LOG_FILE}
        echo -e "${CLOUD_LATENCY} \t ${LIQUID_LATENCY} \t ${CALL_LATENCY}" >> ${SUMMARY_FILE}
        #ODATE=${DATE}
        #OTIME=${TIME}
        OTAG=${TAG}
      fi
    done
  fi
done 

#rm ${TMPFILE}
