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
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.swarmcom.jsynapse.validator.EnumValue;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Presence {
    @Id
    String id;

    @NotNull
    @EnumValue(enumClass = PresenceEnum.class)
    @JsonView(PresenceSummary.class)
    String presence;

    @NotNull
    @JsonProperty("status_msg")
    @JsonView(PresenceSummary.class)
    String statusMessage;

    @Indexed
    String userId;

    @LastModifiedDate
    private Date lastModifiedDate;

    public Presence() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public enum PresenceEnum {
        offline,
        unavailable,
        online,
        free_for_chat;
    }

    public interface PresenceSummary {}
}
