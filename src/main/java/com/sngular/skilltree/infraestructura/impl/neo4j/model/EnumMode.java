package com.sngular.skilltree.infraestructura.impl.neo4j.model;

public enum EnumMode {
    REMOTE("remote"),
    PRESENTIAL("presential"),
    MIX("mix");

    private final String value;

    EnumMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public EnumMode from(final String value) {
        final EnumMode result;
        if (REMOTE.value.equalsIgnoreCase(value)) {
            result = REMOTE;
        } else if (PRESENTIAL.value.equalsIgnoreCase(value)) {
            result = PRESENTIAL;
        } else {
            result = MIX;
        }
        return result;
    }
}