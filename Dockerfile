FROM centos:centos7

MAINTAINER dizzy "george.niculae79@gmail.com"

ADD . /jSynapse/src/
WORKDIR /jSynapse/src/

# get to the current
RUN yum update

# install epel
RUN yum install -y epel-release

# install and start Redis
RUN yum install -y redis

# install tools for building jSynapse
RUN yum install -y git java-1.7.0-openjdk maven

# let's build jSynapse
RUN mvn clean install

EXPOSE 5555:5555

RUN sed -i 's/daemonize no/daemonize yes/' /etc/redis.conf

# start jSynapse server
CMD /usr/bin/redis-server /etc/redis.conf; \
        java -jar target/jSynapse-1.0-SNAPSHOT.jar
