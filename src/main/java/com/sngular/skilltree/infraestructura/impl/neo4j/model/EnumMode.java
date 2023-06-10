package com.sngular.skilltree.infraestructura.impl.neo4j.model;

public enum EnumMode {
    REMOTE("remote"),
    PRESENTIAL("presential"),
    MIX("mix"),
    UNKNOWN("unknown");

    private final String value;

    EnumMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EnumMode from(final String value) {
        var result = UNKNOWN;
        if (REMOTE.value.equalsIgnoreCase(value)) {
            result = REMOTE;
        } else if (PRESENTIAL.value.equalsIgnoreCase(value)) {
            result = PRESENTIAL;
        } else if (MIX.value.equalsIgnoreCase(value)) {
            result = MIX;
        }
        return result;
    }
}