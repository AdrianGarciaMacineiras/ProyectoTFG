package com.sngular.skilltree.infraestructura.impl.neo4j.model;

public enum EnumMinLevel {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low"),
    UNKNOWN("unknown");

    private final String value;

    EnumMinLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EnumMinLevel from(final String value) {
        var result = UNKNOWN;

        if (HIGH.value.equalsIgnoreCase(value)) {
            result = HIGH;
        } else if (MEDIUM.value.equalsIgnoreCase(value)) {
            result = MEDIUM;
        } else if (LOW.value.equalsIgnoreCase(value)) {
            result = LOW;
        }
        return result;
    }
}