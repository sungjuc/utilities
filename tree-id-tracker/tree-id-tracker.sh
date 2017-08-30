#!/bin/bash
echo "${0}"

CLOUD_SESSION_HOST="lor1-app6139.prod.linkedin.com"

LINE=`ssh ${CLOUD_SESSION_HOST} "tail /export/content/lid/apps/cloud-session/i001/logs/cloudsession_public_access.log; exit"`
TAG=`echo ${LINE} | cut -d " " -f 6`
TAG=${TAG:1:24}
echo ${TAG}
echo ${LINE}
