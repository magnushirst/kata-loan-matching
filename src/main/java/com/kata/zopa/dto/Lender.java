package com.kata.zopa.dto;

public class Lender {
    String name;
    double rate;
    double available;

    public Lender(String name, double rate, double available) {
        this.name = name;
        this.rate = rate;
        this.available = available;
    }

    public double getRate() {
        return rate;
    }

    public double getAvailable() {
        return available;
    }
}
