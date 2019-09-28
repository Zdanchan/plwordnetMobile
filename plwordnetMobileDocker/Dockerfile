FROM mysql:8.0.13 

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

ENV MYSQL_ROOT_PASSWORD plw0rdn3t2018
EXPOSE 8080

COPY wordnet.sql /usr/local/bin/
COPY plwordnet-init.sh /usr/local/bin/
RUN ln -s /usr/local/bin/plwordnet-init.sh /
ENTRYPOINT ["bash","plwordnet-init.sh"]
CMD ["$MYSQL_ROOT_PASSWORD a54b3fkjxdtasdf3"]

