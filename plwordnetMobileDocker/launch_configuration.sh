#!/bin/bash

wget "https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip" -O "/data/sdk-tools-linux-3859397.zip" 
mkdir -p /data/opt/sdk-tools 
unzip -d "/data/opt/sdk-tools" "/data/sdk-tools-linux-3859397" 
rm /data/sdk-tools-linux-3859397.zip 
mkdir ~/.android
touch ~/.android/repositories.cfg 
export PATH=/data/opt/sdk-tools/tools:/data/opt/sdk-tools/tools/bin:$PATH
export ANDROID_HOME=/data/opt/sdk-tools
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

mysqld -u root -p --max_allowed_packet=32M
mysql -u root -p --max_allowed_packet=32M
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS wordnet;
USE wordnet;
CREATE USER 'wordnet_android'@'%' IDENTIFIED BY '<wordnet_android_password>';
USE wordnet;
GRANT SELECT ON wordnet.* TO 'wordnet_android'@'%';
CREATE TABLE wordnet.db_change_records (change_id INT AUTO_INCREMENT,
date DATE,
table_name VARCHAR(255),
change_type VARCHAR(10),
record_id INT,
record_id2 INT,
PRIMARY KEY(change_id));"
mysql -u root -p wordnet < /data/<insert_database_file>.sql
rm /data/<insert_database_file>.sql

chmod 0744 /data/git-organiser.sh

mkdir /data/plwordnetMobile-repo
cd /data/plwordnetMobile-repo/ && git clone https://github.com/Zdanchan/plwordnetMobile.git
echo "spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mysql://localhost:3306/wordnet
spring.datasource.username=wordnet_android
spring.datasource.password=<wordnet_android_password>
logging.path=/data/plwordnetMobile-spring-service/logs" > /data/plwordnetMobile-repo/plwordnetMobile/plwordnetMobileService/src/main/resources/application.properties

echo "*/5 * * * * root /bin/bash /data/git-organiser.sh" >> /etc/crontab
service cron start

gradle -p /data/plwordnetMobile-repo/plwordnetMobile/plwordnetmobileRoot build
gradle -p /data/plwordnetMobile-repo/plwordnetMobile/dockerDeployer deploySpringServiceToDocker
gradle -p /data/plwordnetMobile-repo/plwordnetMobile/dockerDeployer deployAndroidAppToDocker
VERSION=$(gradle -p /data/plwordnetMobile-repo/plwordnetMobile/plwordnetmobileRoot properties -q | grep "version:"  | awk '{print $2}')
mv "/data/plwordnetMobile-spring-service/plwordnetmobile-service-${VERSION}.jar" "/data/plwordnetMobile-spring-service/plwordnetmobile-service.jar"

nohup java -jar /data/plwordnetMobile-spring-service/plwordnetmobile-service.jar &
