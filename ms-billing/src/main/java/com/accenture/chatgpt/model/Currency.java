package com.accenture.chatgpt.model;

public enum Currency {

    PESO("peso"),
    DOLLAR("dollar"),
    EURO("euro");

    private final String value;

    Currency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
