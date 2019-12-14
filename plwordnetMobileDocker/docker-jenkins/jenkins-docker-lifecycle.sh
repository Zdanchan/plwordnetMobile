#!/bin/bash

touch ./work.log

echo "$(date +"%d-%m-%Y %T") Started Jenkins docker!" >> ./work.log

while [ true ]
do
	/bin/sh -c /var/jenkins_home/trigger-job.sh >> ./work.log
	sleep 10m
done
