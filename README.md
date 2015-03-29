# jSynapse
jSynapse is the Java implementation of matrix.org homeserver.
Full spec: http://matrix.org/docs/spec/


Steps to get it running:
- install MongoDB and intialize replica set
- git clone git@github.com:swarmcom/jSynapse.git
- mvn clean install
- java -jar target/jSynapse-1.0-SNAPSHOT.jar
(or import Maven project in IDE of choice and run JSynapseServer class)


Install and start from Docker image:
- sudo docker pull dizzy/mongodb
- sudo docker run --name mongo_swarm -p 27017:27017 -d dizzy/mongodb:latest --noprealloc --smallfiles --replSet=jsynapse

- sudo docker pull swarmcom/jsynapse
- sudo docker inspect mongo_swarm (and extract ip)
- sudo docker run -d -name synapse1 -p 5555:5555 --link mongo_swarm:db swarmcom/jsynapse:latest --spring.data.mongodb.uri=mongodb://(mongo_swarm ip):27017/matrix


jSynapse homeserver accepts requests on port 5555,
e.g.
-create room
curl -H "Content-Type: application/json" -d '{"name":"jSynapse first room","aliasName":"my_first_room"}' http://localhost:5555/api/v1/createRoom
{"room_id":"!nUMFdtVYEJsDFpbWGW:swarmcom.org","room_alias_name":null}

-get room by id
curl http://localhost:5555/api/v1/rooms/\!nUMFdtVYEJsDFpbWGW\:swarmcom.org/state/m.room.name
{"name":"jSynapse first room"}

-send message
curl -H "Content-Type: application/json" -d '{"msgtype":"m.text","body":"Testing"}' http://localhost:5555/api/v1/rooms/\!nUMFdtVYEJsDFpbWGW\:swarmcom.org/send/m.room.message

-get room messages
curl http://localhost:5555/api/v1/rooms/\!nUMFdtVYEJsDFpbWGW\:swarmcom.org/messages
{"chunk":[{"content":{"msgtype":"dfsdgdsg"}},{"content":{"msgtype":"34545fdsgfdg"}},{"content":{"msgtype":"hgfjyu6ujuyj"}},{"content":{"msgtype":"hgfjyu6ujuyj","body":"Bob joined the room"}},{"content":{"msgtype":"hgfjyu6ujuyj","body":"Bob left the room"}},
{"content":{"msgtype":"hgfjyu6ujuyj","body":"Alice joined the room"}},{"content":{"msgtype":"hgfjyu6ujuyj","body":"Alice left the room"}}]}

- for retrieving created room by alias:

curl http://localhost:5555/room/my_first_room


