package com.kata.marketplace.entity.enums;

public enum ProductStatusEnum {
    PUBLISH("Publish"), UNPUBLISH("Unpublish");

    public final String label;

    private ProductStatusEnum(String label) {
        this.label = label;
    }
}
