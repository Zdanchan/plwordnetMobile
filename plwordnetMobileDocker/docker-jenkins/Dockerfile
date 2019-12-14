FROM jenkins/jenkins:lts

ARG HOST_DOCKER_GROUP_ID
ARG job_name_1="plwordnetmobile-service"

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

COPY plwordnetmobile-service_job.xml "$JENKINS_HOME"/jobs/${job_name_1}/config.xml  
COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

USER root

RUN chown -R jenkins:jenkins "$JENKINS_HOME"/
#RUN groupadd docker -g ${HOST_DOCKER_GROUP_ID} && \ usermod -a -G docker jenkins