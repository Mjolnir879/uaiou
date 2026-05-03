package com.uaiou.core.domain.entity;

public class OrderType {
    private String code;

    private OrderType(String code) {
        this.code = code;
    }

    public static OrderType create(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Order type code must not be blank");
        }
        return new OrderType(code);
    }

    public String getCode() {
        return code;
    }
}
