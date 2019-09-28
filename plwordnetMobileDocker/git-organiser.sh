#!/bin/bash

[[ ":$PATH:" != *"/data/opt/gradle/gradle-4.6/bin"* ]] && export PATH=$PATH:/data/opt/gradle/gradle-4.6/bin
[[ ":$PATH:" != *"/data/opt/sdk-tools/tools:/data/opt/sdk-tools/tools/bin"* ]] && export PATH=/data/opt/sdk-tools/tools:/data/opt/sdk-tools/tools/bin:$PATH
[[ ":$ANDROID_HOME:" != *"/data/opt/sdk-tools"* ]] && export ANDROID_HOME=/data/opt/sdk-tools

cd /data/plwordnetMobile-repo/plwordnetMobile/

git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git fetch 

UPSTREAM=${1:-'@{u}'}
LOCAL=$(git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git rev-parse @)
REMOTE=$(git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git rev-parse "$UPSTREAM")
BASE=$(git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git merge-base @ "$UPSTREAM")

NOW=$(date '%d-%m-%Y %H:%M:%S')

touch /data/git-organiser-log.log

if [ "$REMOTE" = "$BASE" ]; then
	echo '$NOW - Unauthorised changes reverting plwordnetMobile git repo to remote master branch' >> /data/git-organiser-log.log
	git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git checkout origin/stable/current
elif [ "$LOCAL" = "$BASE" ]; then
	echo '$NOW - Updating plwordnetMobile git repo' >> /data/git-organiser-log.log
	git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git pull origin stable/current
	kill -9 $(ps -ef | grep "java -jar /data/plwordnetMobile-spring-service/plwordnetmobile-service.jar" | grep -v "grep" | awk '{print $2}')
	gradle -p /data/plwordnetMobile-repo/plwordnetMobile/plwordnetmobileRoot build
	gradle -p /data/plwordnetMobile-repo/plwordnetMobile/dockerDeployer deploySpringServiceToDocker
	gradle -p /data/plwordnetMobile-repo/plwordnetMobile/dockerDeployer deployAndroidAppToDocker
	VERSION=$(gradle -p /data/plwordnetMobile-repo/plwordnetMobile/plwordnetmobileRoot properties -q | grep "version:"  | awk '{print $2}')
	mv "/data/plwordnetMobile-spring-service/plwordnetmobile-service-${VERSION}.jar" "/data/plwordnetMobile-spring-service/plwordnetmobile-service.jar"
	nohup java -jar /data/plwordnetMobile-spring-service/plwordnetmobile-service.jar &
fi

exit 0
