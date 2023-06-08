package com.sngular.skilltree.infraestructura.impl.neo4j.model;

public enum EnumMinLevel {
    HIGH("high"),
    MEDIUM("medium"),
    LOW("low");

    private final String value;

    EnumMinLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public EnumMinLevel from(final String value) {
        final EnumMinLevel result;

        if (HIGH.value.equalsIgnoreCase(value)) {
            result = HIGH;
        } else if (MEDIUM.value.equalsIgnoreCase(value)) {
            result = MEDIUM;
        } else {
            result = LOW;
        }
        return result;
    }
}