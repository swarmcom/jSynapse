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

public class User {
    @Id
    String id;

    String userId;

    @JsonProperty("password")
    String hashedPassword;

    @JsonView(AvatarUrlSummary.class)
    @JsonProperty("avatar_url")
    String avatarUrl;

    @JsonView(DisplayNameSummary.class)
    @JsonProperty("displayname")
    String displayName;

    public User() {
    }

    public User(String userId, String hashedPassword) {
        this.userId = userId;
        this.hashedPassword = hashedPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String password) {
        this.hashedPassword = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public interface DisplayNameSummary {}

    public interface AvatarUrlSummary {}
}
