package com.sngular.skilltree.infraestructura.impl.neo4j.model;

public enum EnumGuards {

    PASSIVE("passive"),
    ACTIVE("active"),
    NONE("none"),
    UNKNOWN("unknown");

    private final String value;

    EnumGuards(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EnumGuards from(final String value) {
        var result = UNKNOWN;

        if (PASSIVE.getValue().equalsIgnoreCase(value)) {
            result = PASSIVE;
        } else if (ACTIVE.getValue().equalsIgnoreCase(value)) {
            result = ACTIVE;
        } else if (NONE.getValue().equalsIgnoreCase(value)) {
            result = NONE;
        }
        return result;
    }
}
