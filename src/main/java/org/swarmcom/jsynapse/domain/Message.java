/*
 * (C) Copyright 2015 eZuce Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
*/
package org.swarmcom.jsynapse.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.util.*;

public class Message {
    @Id
    String id;

    @NotNull
    @JsonProperty
    String msgtype;

    @Indexed
    String roomId;

    String body;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static class Messages {
        List<MessageSummary> chunk = new LinkedList<>();

        public Messages(List<Message> messages) {
            for (Message message : messages) {
                chunk.add(new MessageSummary(message));
            }
        }

        public List<MessageSummary> getChunk() {
            return chunk;
        }
    }

    public static class MessageSummary {
        public static final String MSG_TYPE = "msgtype";
        public static final String BODY = "body";

        @JsonProperty
        Map<String, String> content = new LinkedHashMap<>();

        public MessageSummary(Message message) {
            content.put(MSG_TYPE, message.getMsgtype());
            if (StringUtils.isNotBlank(message.getBody())) {
                content.put(BODY, message.getBody());
            }
        }

        public Map<String, String> getContent() {
            return content;
        }
    }
}
