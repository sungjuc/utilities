#!/bin/bash

ssh scho@lor1-app6139.prod << EOF
  /usr/local/linkedin/bin/mssh -r %%prod-lor1.cloud-session "zgrep ${1} /export/content/lid/apps/cloud-session/i001/logs/cloudsession_public_access.log.${2}.gz" &&
  /usr/local/linkedin/bin/mssh -r %%prod-lor1.cloud-session "zgrep ${1} /export/content/lid/apps/cloud-session/i002/logs/cloudsession_public_access.log.${2}.gz" &&
  exit
EOF
