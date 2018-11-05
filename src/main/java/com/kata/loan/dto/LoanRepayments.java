package com.kata.loan.dto;

import java.math.BigDecimal;

public class LoanRepayments {
    private BigDecimal totalRepayment;
    private BigDecimal monthlyRepayment;

    public LoanRepayments(BigDecimal totalRepayment, BigDecimal monthlyRepayment) {
        this.totalRepayment = totalRepayment;
        this.monthlyRepayment = monthlyRepayment;
    }

    public BigDecimal getTotalRepayment() {
        return totalRepayment;
    }

    public BigDecimal getMonthlyRepayment() {
        return monthlyRepayment;
    }
}
