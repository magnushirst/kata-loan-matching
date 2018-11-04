package com.kata.zopa.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.RoundingMode.HALF_EVEN;

public class LoanDetails {

    int loanLengthMonths = 36;
    double yearlyRate;
    double loanAmount;

    public LoanDetails(double yearlyRate, double loanAmount) {
        this.yearlyRate = yearlyRate;
        this.loanAmount = loanAmount;
    }

    public double getYearlyRate() {
        return yearlyRate;
    }

    public double getLoanAmount() {
        return loanAmount;
    }


    /**
     * This formula does not match example in spec but returns same as Excel's PMT function
     */
    public LoanRepayments calculateRepayments() {
        BigDecimal monthlyRate = new BigDecimal(this.yearlyRate / 12);
        BigDecimal e1 = new BigDecimal(1).add(monthlyRate).pow(loanLengthMonths);
        BigDecimal e2 = monthlyRate.multiply(e1);
        BigDecimal e3 = e1.subtract(new BigDecimal(1));

        BigDecimal amortizationCalc = new BigDecimal(loanAmount).multiply(e2.divide(e3, HALF_EVEN)).setScale(2, HALF_EVEN);
        BigDecimal totalRepayment = amortizationCalc.multiply(new BigDecimal(loanLengthMonths));
        return new LoanRepayments(totalRepayment, amortizationCalc);
    }
}
