package com.sngular.skilltree.model;

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


}