package com.sngular.skilltree.infraestructura.impl.neo4j.model;

public enum EnumCharge {
    DIRECTOR("director"),
    HEAD("head");

    private final String value;

    EnumCharge(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EnumCharge from(final String value) {
        final EnumCharge result;

        if (DIRECTOR.getValue().equalsIgnoreCase(value)) {
            result = DIRECTOR;
        } else {
            result = HEAD;
        }
        return result;
    }
}
