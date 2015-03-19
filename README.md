# jSynapse
jSynapse is the Java implementation of matrix.org homeserver.
Full spec: http://matrix.org/docs/spec/


Steps to get it running:
- install Redis, no port configuration needed
- git clone git@github.com:swarmcom/jSynapse.git
- mvn clean install
- java -jar target/jSynapse-1.0-SNAPSHOT.jar
(or import Maven project in IDE of choice and run JSynapseServer class)


Install and start from Docker image:
- sudo docker pull dizzy/jsynapse:latest
- sudo docker run -d -p 5555:5555 dizzy/jsynapse:latest


jSynapse homeserver accepts requests on port 5555,
e.g.
- for creating a room
curl -H "Content-Type: application/json" -d '{"name":"jSynapse first room","aliasName":"my_first_room"}' http://localhost:5555/room

- for retrieving created room by alias:
curl http://localhost:5555/room/my_first_room
