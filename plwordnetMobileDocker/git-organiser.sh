#!/bin/bash

[[ ":$PATH:" != *"/data/opt/gradle/gradle-4.6/bin"* ]] && export PATH=$PATH:/data/opt/gradle/gradle-4.6/bin
[[ ":$PATH:" != *"/data/opt/sdk-tools/tools:/data/opt/sdk-tools/tools/bin"* ]] && export PATH=/data/opt/sdk-tools/tools:/data/opt/sdk-tools/tools/bin:$PATH
[[ ":$ANDROID_HOME:" != *"/data/opt/sdk-tools"* ]] && export ANDROID_HOME=/data/opt/sdk-tools

cd /data/plwordnetMobile-repo/plwordnetMobile/

RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git fetch

UPSTREAM=${1:-'@{u}'}
LOCAL=$(git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git rev-parse @)
REMOTE=$(git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git rev-parse "$UPSTREAM")
BASE=$(git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git merge-base @ "$UPSTREAM")

if [ "$LOCAL" = "$REMOTE" ]; then
	echo '${GREEN}plwordnetMobile git repo is UP-TO-DATE${NC}'
elif [ "$REMOTE" = "$BASE" ]; then
	echo '${RED}Unauthorised changes reverting plwordnetMobile git repo to remote master branch${NC}'
	git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git checkout origin/master
elif [ "$LOCAL" = "$BASE" ]; then
	echo '${BLUE}Updating plwordnetMobile git repo${NC}'
	git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git pull origin master
	kill -9 $(ps -ef | grep "java -jar /data/plwordnetMobile-spring-service/plwordnetmobile-service.jar" | grep -v "grep" | awk '{print $2}')
	gradle -p /data/plwordnetMobile-repo/plwordnetMobile/plwordnetmobileRoot build
	gradle -p /data/plwordnetMobile-repo/plwordnetMobile/dockerDeployer deploySpringServiceToDocker
	gradle -p /data/plwordnetMobile-repo/plwordnetMobile/dockerDeployer deployAndroidAppToDocker
	VERSION=$(gradle -p /data/plwordnetMobile-repo/plwordnetMobile/plwordnetmobileRoot properties -q | grep "version:"  | awk '{print $2}')
	mv "/data/plwordnetMobile-spring-service/plwordnetmobile-service-${VERSION}.jar" "/data/plwordnetMobile-spring-service/plwordnetmobile-service.jar"
	nohup java -jar /data/plwordnetMobile-spring-service/plwordnetmobile-service.jar &
else
	echo '${RED}plwordnetMobile git repo diverged - something went wrong!${NC}'
fi

exit 0