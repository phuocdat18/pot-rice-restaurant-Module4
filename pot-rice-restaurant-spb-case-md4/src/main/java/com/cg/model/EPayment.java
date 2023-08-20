package com.cg.model;

public enum EPayment {

    ORDER("ORDER"), //0
    LOADING("LOADING"),
    SHIPPING("SHIPPING"),
    DONE("DONE"),   //1
    CANCEL("CANCEL"),
    BOMB("BOMB");

    private final String value;

    EPayment(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}