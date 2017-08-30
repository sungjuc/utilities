#!/bin/bash
echo ${0}

HOST={1}
HOST="lor1-app6139.prod"

for file in `ls ${HOST}`
do
   #grep -o "\w*AAA==\w*" "${HOST}/${file}"
   while read -r line
   do
   echo ${line}
   TREE_ID=`echo ${line}| grep -oh "\w*==]\w*"`
   #TREE_ID=${TREE_ID:1:24}
   #TREE_ID=`grep -oh "\w[*AAA==]\w"`
   echo "XXXXXX"${TREE_ID}
   done <"${HOST}/${file}"
done 
