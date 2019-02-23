FROM mysql:8.0.13

VOLUME /data 

RUN mkdir -p /usr/share/man/man1
RUN apt-get update
RUN apt-get install -y wget
RUN apt-get install -y curl
RUN apt-get install -y nano
RUN apt-get install -y zip
RUN apt-get install -y cron
RUN apt-get install -y git
RUN apt-get install -y default-jdk
RUN apt-get install -y procps