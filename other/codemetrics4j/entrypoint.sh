#!/bin/sh

mkdir -p /app/root/target/metrics
/app/codemetrics4j/bin/codemetrics4j /app/root/src/main/java > /app/root/target/metrics/results.xml
xsltproc /app/codemetrics4j/metrics-to-html.xslt /app/root/target/metrics/results.xml > /app/root/target/metrics/results.html

chmod 777 -R /app/root/target
