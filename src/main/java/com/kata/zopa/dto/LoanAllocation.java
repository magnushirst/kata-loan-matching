package com.kata.zopa.dto;

public class LoanAllocation {
    public double loanedAmount;
    public double rate;

    public LoanAllocation() {
        this.loanedAmount = 0;
        this.rate = 0;
    }

    public void addLender(double lenderRate, double lendersContribution) {
        double loan1interest = this.rate * loanedAmount;
        double loan2interest = lenderRate * lendersContribution;

        double combinedLoanInterest = loan1interest + loan2interest;
        double combinedLoanAmount = loanedAmount + lendersContribution;

        double averageLoanInterest = combinedLoanInterest/combinedLoanAmount;

        this.loanedAmount = combinedLoanAmount;
        this.rate = averageLoanInterest;
    }

    public double loanedAmount() {
        return loanedAmount;
    }

    public double getRate() {
        return rate;
    }
    public double getLoanedAmount() {
        return loanedAmount;
    }
}
