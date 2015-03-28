package org.swarmcom.jsynapse.domain;

import org.springframework.data.annotation.Id;

public class User {
    @Id
    String id;

    String userId;

    String password;

    public User(String userId, String hashedPassword) {
        this.userId = userId;
        this.password = hashedPassword;
    }
}
