#!/bin/bash

URL="http://repo.bitbake.bolid.ru/arm-s3000/"

FILE_URL=$(curl -s "$URL" | grep -o '<a [^>]*href=[^>]*>' | tail -n 1 | sed -n 's/.*href="\([^"]*\).*/\1/p')

#echo "$FILE_URL"
echo "${FILE_URL::-19}:${FILE_URL:22:-7}" >> /var/lib/jenkins/workspace/1/target/allure-results/environment.properties