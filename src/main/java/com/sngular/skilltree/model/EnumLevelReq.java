package com.sngular.skilltree.model;

public enum EnumLevelReq {
    MANDATORY("mandatory"),
    NICE_TO_HAVE("nice_to_have");

    private final String value;

    EnumLevelReq(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public EnumLevelReq from(final String value) {
        final EnumLevelReq result;
        if (MANDATORY.value.equalsIgnoreCase(value)) {
            result = MANDATORY;
        } else {
            result = NICE_TO_HAVE;
        }
        return result;
    }
}