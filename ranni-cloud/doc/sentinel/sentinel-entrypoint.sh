#!/bin/bash

JAVA_OPT="${JAVA_OPT}"

JAVA_OPT="${JAVA_OPT} -Dserver.port=${SERVER_PORT} "
JAVA_OPT="${JAVA_OPT} -Dcsp.sentinel.log.dir=${SENTINEL_LOGS} "
JAVA_OPT="${JAVA_OPT} -Dproject.name=${PROJECT_NAME} "
JAVA_OPT="${JAVA_OPT} -Dsentinel.dashboard.auth.username=${USERNAME} "
JAVA_OPT="${JAVA_OPT} -Dsentinel.dashboard.auth.password=${PASSWORD} "
JAVA_OPT="${JAVA_OPT} -Dcsp.sentinel.dashboard.server=${SERVER_HOST}:${SERVER_PORT} "

echo "JAVA_OPT============"
echo "JAVA_OPT============"

echo $JAVA_OPT

java ${JAVA_OPT} -jar /opt/sentinel-dashboard-1.8.1.jar

echo "JAVA_OPT============"

