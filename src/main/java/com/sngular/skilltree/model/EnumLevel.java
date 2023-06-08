package com.sngular.skilltree.model;

public enum EnumLevel {
    LOW("low"),
    MEDIUM("medium"),
    CONFIDENT("confident"),
    HIGH("high");

    private final String value;

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
        } else if (MEDIUM.value.equalsIgnoreCase(value)) {
            result = MEDIUM;
        } else if (CONFIDENT.value.equalsIgnoreCase(value)) {
            result = CONFIDENT;
        } else {
            result = HIGH;
        }
        return result;
    }
}
