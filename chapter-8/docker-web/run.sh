#!/usr/bin/env bash
set -e
NAME=${NAME:-docker-webapp}

JAR=$(find . -name ${NAME}*.jar|head -1)
java -jar "${JAR}"
