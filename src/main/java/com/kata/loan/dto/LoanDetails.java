package com.kata.loan.dto;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_EVEN;

public class LoanDetails {

    private int loanLengthMonths = 36;
    private BigDecimal MONTHS_IN_YEAR = new BigDecimal(12);
    private BigDecimal yearlyRate;
    private BigDecimal loanAmount;

    public LoanDetails(String yearlyRate, String loanAmount) {
         this(new BigDecimal(yearlyRate), new BigDecimal(loanAmount));
    }

    public LoanDetails(BigDecimal yearlyRate, BigDecimal loanAmount) {
        this.yearlyRate = yearlyRate;
        this.loanAmount = loanAmount;
    }

    public BigDecimal getYearlyRate() {
        return yearlyRate.multiply(new BigDecimal(100)).setScale(1, HALF_EVEN);
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }


    /**
     * This formula does not match example in spec but returns same as Excel's PMT function
     */
    public LoanRepayments calculateRepayments() {
        BigDecimal monthlyRate = this.yearlyRate.divide(MONTHS_IN_YEAR, 10, HALF_EVEN);
        BigDecimal e1 = (new BigDecimal(1).add(monthlyRate)).pow(loanLengthMonths);
        BigDecimal e2 = monthlyRate.multiply(e1);
        BigDecimal e3 = e1.subtract(new BigDecimal(1));

        BigDecimal amortizationCalc = loanAmount.multiply(e2.divide(e3, HALF_EVEN)).setScale(2, HALF_EVEN);
        BigDecimal totalRepayment = amortizationCalc.multiply(new BigDecimal(loanLengthMonths));
        return new LoanRepayments(totalRepayment, amortizationCalc);
    }
}
