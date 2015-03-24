package org.swarmcom.jsynapse.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Room {
    @GraphId
    Long id;

    @Indexed(unique = true) @JsonProperty("room_id")
    @JsonView({CreateSummary.class, DirectorySummary.class})
    String roomId;

    @NotNull
    String name;

    @JsonProperty
    String visibility = "private";

    @JsonProperty("room_alias_name")
    @JsonView(CreateSummary.class)
    String alias;

    @JsonProperty
    String topic;

    @JsonProperty
    List<String> invite;

    @JsonProperty
    @JsonView(DirectorySummary.class)
    List<String> servers = new ArrayList<>();

    public Room() {
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public interface CreateSummary {}

    public interface DirectorySummary {}
}
