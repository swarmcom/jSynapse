package org.matrix.jsynapse.domain;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Room {

    @NotNull
    String name;

    @NotNull
    String aliasName;

    public Room() {
    }

    public Room(String name, String aliasName) {
        this.name = name;
        this.aliasName = aliasName;
    }

    public String getName() {
        return name;
    }

    public String getAliasName() {
        return aliasName;
    }
}
