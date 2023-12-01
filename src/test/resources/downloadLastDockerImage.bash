#!/bin/bash

URL="http://repo.bitbake.bolid.ru/arm-s3000/"
DIRECTORY="/home/user/"
#DIRECTORY2="/home/vmware/IdeaProjects/s3000test/src/test/resources/"

FILE_URL=$(curl -s "$URL" | grep -o '<a [^>]*href=[^>]*>' | tail -n 1 | sed -n 's/.*href="\([^"]*\).*/\1/p')

curl -o "$DIRECTORY$(basename $FILE_URL)" "$URL$FILE_URL"
echo "$FILE_URL"