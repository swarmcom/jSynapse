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
-create room
curl -H "Content-Type: application/json" -d '{"name":"jSynapse first room","aliasName":"my_first_room"}' http://localhost:5555/api/v1/createRoom
{"room_id":"!nUMFdtVYEJsDFpbWGW:swarmcom.org","room_alias_name":null}[mirceac@mircea jSynapse]$

-get room by id
curl http://localhost:5555/api/v1/rooms/\!nUMFdtVYEJsDFpbWGW\:swarmcom.org/state/m.room.name
{"name":"jSynapse first room"}[mirceac@mircea jSynapse]$

-send message
curl -H "Content-Type: application/json" -d '{"msgtype":"m.text","body":"Testing"}' http://localhost:5555/api/v1/rooms/\!nUMFdtVYEJsDFpbWGW\:swarmcom.org/send/m.room.message

-get room messages
curl http://localhost:5555/api/v1/rooms/\!nUMFdtVYEJsDFpbWGW\:swarmcom.org/messages
[{"msgtype":"m.text","roomId":"!nUMFdtVYEJsDFpbWGW:swarmcom.org","body":"Testing"}]

- for retrieving created room by alias:

curl http://localhost:5555/room/my_first_room


