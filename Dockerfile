FROM openjdk:8-jdk-alpine
ENV JAR_FILE=plwordnetMobileService/build/libs/plwordnetmobile-service.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]