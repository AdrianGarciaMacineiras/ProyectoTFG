package com.tfg.skilltree.model;

public enum EnumLevel {

    LOW("low"),
    MIDDLE("middle"),
    ADVANCED("advanced"),
    UNKNOWN("unknown");

    private String value;

    EnumLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public EnumLevel from(final String value) {
        EnumLevel result;
        if (LOW.value.equalsIgnoreCase(value)) {
            result = LOW;
        } else if (MIDDLE.value.equalsIgnoreCase(value)) {
            result = MIDDLE;
        } else {
            result = ADVANCED;
        }
        return result;
    }
}
