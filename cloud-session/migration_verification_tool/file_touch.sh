#!/bin/bash
FILE_NAME=${1}
echo "Touching ${FILE_NAME}"
if [ -e ${FILE_NAME} ]
then
  echo "${FILE_NAME} exists. So, remove it."
  rm ${FILE_NAME}
fi

touch ${FILE_NAME}

