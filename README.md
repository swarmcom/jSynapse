# jSynapse
jSynapse is the Java implementation of matrix.org homeserver

Steps to get it running:
- git clone git@github.com:swarmcom/jSynapse.git
- mvn clean install
- java -jar target/jSynapse-1.0-SNAPSHOT.jar
(or import Maven project in IDE of choice and run JSynapseServer class)

jSynapse homeserver accepts requests on port 5555, e.g. for getting room by alias name use:
curl http://localhost:5555/room/test_aliasName

http://localhost:5555/room/test_aliasName

Shooting for building / packaging as:
- RPM
- deb
- docker image
- Windows .exe
