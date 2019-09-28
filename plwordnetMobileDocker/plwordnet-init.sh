#!/bin/bash

wget "https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip" -O "/sdk-tools-linux-3859397.zip" 
mkdir -p /data/opt/sdk-tools 
unzip -d "/data/opt/sdk-tools" "/sdk-tools-linux-3859397" 
rm /sdk-tools-linux-3859397.zip 
mkdir ~/.android
touch ~/.android/repositories.cfg 
export PATH=/data/opt/sdk-tools/tools:/data/opt/sdk-tools/tools/bin:$PATH
export ANDROID_HOME=/data/opt/sdk-tools
yes | sdkmanager --licenses
sdkmanager "platforms;android-28" "build-tools;28.0.3" 
sdkmanager "extras;google;m2repository"


wget "https://services.gradle.org/distributions/gradle-4.6-bin.zip" -O "/data/gradle-4.6-bin.zip" 
mkdir -p /data/opt/gradle 
unzip -d "/data/opt/gradle" "/data/gradle-4.6-bin" 
rm /data/gradle-4.6-bin.zip 
export PATH=$PATH:/data/opt/gradle/gradle-4.6/bin

mkdir -p /data/plwordnetMobile-spring-service/downloads/all_in
mkdir -p /data/plwordnetMobile-spring-service/downloads/android-apk
mkdir -p /data/plwordnetMobile-spring-service/downloads/ios-apk
mkdir -p /data/plwordnetMobile-spring-service/logs

mysqld -u root -p$1 --max_allowed_packet=32M
mysql -u root -p$1 --max_allowed_packet=32M
mysql -u root -p$1 -e "CREATE DATABASE IF NOT EXISTS wordnet;
USE wordnet;
CREATE USER 'wordnet_android'@'%' IDENTIFIED BY '$1';
USE wordnet;
GRANT SELECT ON wordnet.* TO 'wordnet_android'@'%';
CREATE TABLE wordnet.db_change_records (change_id INT AUTO_INCREMENT,
date DATE,
table_name VARCHAR(255),
change_type VARCHAR(10),
record_id INT,
record_id2 INT,
PRIMARY KEY(change_id));" $2

if [ -f "/wordnet.sql" ]; then
	mysql -u root -p wordnet < /wordnet.sql
	rm /wordnet.sql
else
	echo "WARN: Could not find file wordnet.sql, update database manually or ignore this message if you already did"
fi


echo "
#!/bin/bash

[[ \":$PATH:\" != *\"/data/opt/gradle/gradle-4.6/bin\"* ]] && export PATH=$PATH:/data/opt/gradle/gradle-4.6/bin
[[ \":$PATH:\" != *\"/data/opt/sdk-tools/tools:/data/opt/sdk-tools/tools/bin\"* ]] && export PATH=$PATH:/data/opt/sdk-tools:/data/opt/sdk-tools/tools/bin
[[ \":$ANDROID_HOME:\" != *\"/data/opt/sdk-tools\"* ]] && export ANDROID_HOME=/data/opt/sdk-tools

cd /data/plwordnetMobile-repo/plwordnetMobile/

git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git fetch

UPSTREAM=${1:-'@{u}'}
LOCAL=$(git --git-dir /tmp/plwordnetMobile-repo/.git rev-parse @)
REMOTE=$(git --git-dir /tmp/plwordnetMobile-repo/.git rev-parse \"@UPSTREAM\")
BASE=$(git --git-dir /tmp/plwordnetMobile-repo/.git merge-base @ \"@UPSTREAM\")

NOW=$(date +'%d-%m-%Y %T')

if[ $REMOTE = $BASE ]; then
	echo '$NOW : Unathorised changes, reverting plwordnetMobile git repo to remote master branch' >> /data/organiser_log.log
	git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git checkout origin/stable/current
elif[ $LOCAL = $BASE ]; then
	echo '$NOW : Updating plwordnetMobile git repo'
	git --git-dir /data/plwordnetMobile-repo/plwordnetMobile/.git pull origin stable/current
	kill -9 $(ps -ef | grep \"java -jar /data/plwordnetMobile-spring-service/plwordnetmobile-service.jar\" | grep -v \"version:\" | awk '{print $2}')
	gradle -p /data/plwordnetMobile-repo/plwordnetMobile/plwordnetmobileRoot build
	gradle -p /data/plwordnetMobile-repo/plwordnetMobile/dockerDeployer deploySpringServiceToDocker
	gradle -p /data/plwordnetMobile-repo/plwordnetMobile/dockerDeployer deployAndroidAppToDocker
	VERSION=$(gradle -p /data/plwordnetMobile-repo/plwordnetMobile/plwordnetmobileRoot properties -q | grep \"version:\" | awk '{print $2}')
	mv \"/data/plwordnetMobile-spring-service/plwordnetmobile-service-${VERSION}.jar\" \"/data/plwordnetMobile-spring-service/plwordnetmobile-service.jar\"	
	nohup java -jar /data/plwordnetMobile-spring-service/plwordnetmobile-service.jar &
fi

exit 0" > /data/git-organiser.sh

chmod 0744 /data/git-organiser.sh

mkdir /data/plwordnetMobile-repo
cd /data/plwordnetMobile-repo/ && git clone --single-branch --branch stable/current https://github.com/Zdanchan/plwordnetMobile.git
echo "spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mysql://localhost:3306/wordnet
spring.datasource.username=wordnet_android
spring.datasource.password=<wordnet_android_password>
logging.path=/data/plwordnetMobile-spring-service/logs" > /data/plwordnetMobile-repo/plwordnetMobile/plwordnetMobileService/src/main/resources/application.properties

CRONTAB=$(cat /etc/crontab)
[[ ":$CRONTAB:" != *"/data/git-organiser.sh"* ]] && echo "*/5 * * * * root /bin/bash /data/git-organiser.sh" >> /etc/crontab
service cron start

gradle -p /data/plwordnetMobile-repo/plwordnetMobile/plwordnetmobileRoot build
gradle -p /data/plwordnetMobile-repo/plwordnetMobile/dockerDeployer deploySpringServiceToDocker
gradle -p /data/plwordnetMobile-repo/plwordnetMobile/dockerDeployer deployAndroidAppToDocker
VERSION=$(gradle -p /data/plwordnetMobile-repo/plwordnetMobile/plwordnetmobileRoot properties -q | grep "version:"  | awk '{print $2}')
mv "/data/plwordnetMobile-spring-service/plwordnetmobile-service-${VERSION}.jar" "/data/plwordnetMobile-spring-service/plwordnetmobile-service.jar"

nohup java -jar /data/plwordnetMobile-spring-service/plwordnetmobile-service.jar &

exec /usr/local/bin/docker-entrypoint.sh
