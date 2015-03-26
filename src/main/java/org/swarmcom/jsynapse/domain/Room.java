package org.swarmcom.jsynapse.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Room {
    @Id
    String id;

    @JsonProperty("room_id")
    @JsonView({CreateSummary.class, DirectorySummary.class})
    @Indexed
    String roomId;

    @NotNull
    @JsonView(NameSummary.class)
    String name;

    @JsonProperty
    String visibility = "private";

    @JsonProperty("room_alias_name")
    @JsonView(CreateSummary.class)
    String alias;

    @JsonProperty
    @JsonView(TopicSummary.class)
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

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public interface CreateSummary {}

    public interface DirectorySummary {}

    public interface TopicSummary {}

    public interface NameSummary {}
}
