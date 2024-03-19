package com.stockmonitoring.model;

public class Stock {

    Integer value;

    public Stock() {
    }

    public Stock(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
