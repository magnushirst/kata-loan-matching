package com.kata.loan.dto;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_EVEN;

public class LoanAllocation {
    private BigDecimal loanedAmount;
    private BigDecimal rate;

    public LoanAllocation() {
        this.loanedAmount = new BigDecimal(0);
        this.rate = new BigDecimal(0);
    }

    public void addLender(String lenderRate, String lendersContribution) {
        addLender(new BigDecimal(lenderRate), new BigDecimal(lendersContribution));
    }

    public void addLender(BigDecimal lenderRate, BigDecimal lendersContribution) {
        BigDecimal loan1interest = this.rate.multiply(loanedAmount);
        BigDecimal loan2interest = lenderRate.multiply(lendersContribution);

        BigDecimal combinedLoanInterest = loan1interest.add(loan2interest);
        BigDecimal combinedLoanAmount = loanedAmount.add(lendersContribution);

        BigDecimal averageLoanInterest = combinedLoanInterest.divide(combinedLoanAmount, HALF_EVEN);

        this.loanedAmount = combinedLoanAmount;
        this.rate = averageLoanInterest;
    }

    public BigDecimal loanedAmount() {
        return loanedAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getLoanedAmount() {
        return loanedAmount;
    }
}
