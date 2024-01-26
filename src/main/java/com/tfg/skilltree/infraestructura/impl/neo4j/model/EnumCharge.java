package com.tfg.skilltree.infraestructura.impl.neo4j.model;

public enum EnumCharge {
    DIRECTOR("director"),
    HEAD("head"),
    UNKNOWN("unknown");

    private final String value;

    EnumCharge(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EnumCharge from(final String value) {
        var result = UNKNOWN;

        if (DIRECTOR.getValue().equalsIgnoreCase(value)) {
            result = DIRECTOR;
        } else if (HEAD.getValue().equalsIgnoreCase(value)) {
            result = HEAD;
        }
        return result;
    }
}
