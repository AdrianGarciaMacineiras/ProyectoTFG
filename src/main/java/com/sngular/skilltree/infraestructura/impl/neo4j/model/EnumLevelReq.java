package com.sngular.skilltree.infraestructura.impl.neo4j.model;

public enum EnumLevelReq {

    MANDATORY("mandatory"),
    NICE_TO_HAVE("nice_to_have"),
    UNKNOWN("unknown");

    private final String value;

    EnumLevelReq(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EnumLevelReq from(final String value) {
        var result = UNKNOWN;
        if (MANDATORY.value.equalsIgnoreCase(value)) {
            result = MANDATORY;
        } else if (NICE_TO_HAVE.value.equalsIgnoreCase(value)) {
            result = NICE_TO_HAVE;
        }
        return result;
    }
}