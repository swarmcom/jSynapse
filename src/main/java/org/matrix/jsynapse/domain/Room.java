package org.matrix.jsynapse.domain;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Room {
    @NotNull
    String id;

    @NotNull
    String name;

    @NotNull
    String aliasName;

    public Room() {
    }

    public Room(String id, String name, String aliasName) {
        this.id = id;
        this.name = name;
        this.aliasName = aliasName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAliasName() {
        return aliasName;
    }
}
