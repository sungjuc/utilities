#!/bin/bash
if [ $# -le 0 ]; 
then
  echo "Usage: ${0} DIRECTORY" >&2
  exit 1
fi

DIRECTORY=${1}
TMPFILE="${0}.tmp"
RESULT="${0}.result"

if [ -e ${TMPFILE} ]
then
  rm ${TMPFILE}
fi

if [ -e ${RESULT} ]
then
  rm ${RESULT}
fi

touch ${TMPFILE}
touch ${RESULT}

echo "Processing logs in ${DIRECTORY}"
echo -e "count\tmaxDegree" > ${RESULT}

for entry in "${DIRECTORY}"/*.gz
do
  echo ${entry}
  zgrep graphDistances ${entry} | grep -v 'RestException' | grep -o 'DISTANCE_[0-9]' | cut -d '_' -f 2 >> ${TMPFILE}

done

rm ${TMPFILE}
