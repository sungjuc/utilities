#!/bin/bash
echo ${0}
TREE_ID=${1}
DATE_TIME=${2}

ssh -t scho@lor1-app6139.prod '/usr/local/linkedin/bin/mssh -r %%prod-lor1.cloud-session \"/bin/grep 1234 /export/content/lid/apps/cloud-session/i001/logs/cloudsession_public_access.log.2017-08-28-19.gz\" && exit '
