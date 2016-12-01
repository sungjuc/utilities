#!/bin/bash
if [ $# -le 0 ]; 
then
  echo "Usage: ${0} DIRECTORY" >&2
  exit 1
fi

DIRECTORY=${1}
#TMPFILE="${0}.tmp"
#RESULT="${0}.result"

#if [ -e ${TMPFILE} ]
#then
#  rm ${TMPFILE}
#fi

#if [ -e ${RESULT} ]
#then
#  rm ${RESULT}
#fi

#touch ${TMPFILE}
#touch ${RESULT}

echo "Processing logs in ${DIRECTORY}"
#echo -e "count\tcardinality" > ${RESULT}

for entry in "${DIRECTORY}"/*.gz
do
  echo ${entry}
  #zgrep graphDistances ${entry} | grep -v 'RestException' | awk -F 'to:urn' '{print NF - 1}' >> ${TMPFILE}
  zgrep setOperations ${entry} | grep -v 'RestException' | grep 'firstEdgeType:MEMBER_EMPLOYED_BY_COMPANY,secondEdgeType:MEMBER_EMPLOYED_BY_COMPANY' | wc -l
#  sort -n ${TMPFILE} | uniq -c >> ${RESULT}
done

#rm ${TMPFILE}

