package org.swarmcom.jsynapse.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;

public class Message {
    @Id
    String id;

    @NotNull
    @JsonProperty
    String msgtype;

    @DBRef
    Room room;

    public void setRoom(Room room) {
        this.room = room;
    }
}
