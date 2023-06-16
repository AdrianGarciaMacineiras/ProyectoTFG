package com.sngular.skilltree.infraestructura.impl.neo4j.model;

public enum EnumLevel {

    LOW("low"),
    MIDDLE("middle"),
    ADVANCED("advanced"),
    UNKNOWN("unknown");

    private final String value;

    EnumLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EnumLevel from(final String value) {
        var result = UNKNOWN;
        if (LOW.value.equalsIgnoreCase(value)) {
            result = LOW;
        } else if (MIDDLE.value.equalsIgnoreCase(value)) {
            result = MIDDLE;
        } else if (ADVANCED.value.equalsIgnoreCase(value)) {
            result = ADVANCED;
        }
        return result;
    }
}

