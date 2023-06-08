package com.sngular.skilltree.model;

public enum EnumGuards {

    PASSIVE("passive"),
    ACTIVE("active"),
    NO_GUARD("no_guard");

    private final String value;

    EnumGuards(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public EnumGuards from(final String value) {
        final EnumGuards result;

        if (PASSIVE.getValue().equalsIgnoreCase(value)) {
            result = PASSIVE;
        } else if (ACTIVE.getValue().equalsIgnoreCase(value)) {
            result = ACTIVE;
        } else {
            result = NO_GUARD;
        }
        return result;
    }
}
