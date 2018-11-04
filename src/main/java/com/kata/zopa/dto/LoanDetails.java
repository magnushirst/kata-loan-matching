package com.kata.zopa.dto;

public class LoanDetails {

    int loanTermLength = 12;
    double rate;
    double loanAmount;

    public LoanDetails(double rate, double loanAmount) {
        this.rate = rate;
        this.loanAmount = loanAmount;
    }

    public double getRate() {
        return rate;
    }

    public double getLoanAmount() {
        return loanAmount;
    }
}
