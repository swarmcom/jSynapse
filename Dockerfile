FROM centos:centos7

MAINTAINER dizzy "george.niculae79@gmail.com"

ADD . /jSynapse/src/
WORKDIR /jSynapse/src/

# get to the current
RUN yum -y update

# install tools for building jSynapse
RUN yum install -y java-1.7.0-openjdk maven

# let's build jSynapse
RUN mvn clean install -Dmaven.test.skip=true

EXPOSE 5555:5555

# jSynapse server entrypoint
ENTRYPOINT ["/usr/bin/java", "-jar", "target/jSynapse-1.0-SNAPSHOT.jar"]
