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

import java.util.LinkedList;
import java.util.List;

public class RoomAlias {
    private static final String PREFIX = "#";
    private static final String SEPARATOR = ":";

    @Id
    String id;

    @Indexed
    @JsonProperty("room_id")
    String roomId;

    String alias;

    String server;

    public RoomAlias(String roomId, String alias, String server) {
        this.roomId = roomId;
        this.alias = alias;
        this.server = server;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getAlias() {
        return alias;
    }

    public String getServer() {
        return server;
    }

    public static class RoomAliases {
        List<String> aliases = new LinkedList<>();

        public RoomAliases(List<RoomAlias> roomAliases) {
            for (RoomAlias roomAlias : roomAliases) {
                aliases.add(StringUtils.join(PREFIX, roomAlias.getAlias(), SEPARATOR, roomAlias.getServer()));
            }
        }
    }

    public static class AliasServers {
        @JsonProperty("room_id")
        String roomId;

        @JsonProperty
        List<String> servers = new LinkedList<>();

        public AliasServers(String roomId, List<RoomAlias> roomAliases) {
            this.roomId = roomId;
            for (RoomAlias roomAlias : roomAliases) {
                servers.add(roomAlias.getServer());
            }
        }

        public String getRoomId() {
            return roomId;
        }

        public List<String> getServers() {
            return servers;
        }
    }
}
