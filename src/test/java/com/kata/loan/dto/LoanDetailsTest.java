package com.kata.loan.dto;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class LoanDetailsTest {

    @Test
    public void calculatingTheInterest_ReturnCorrectValues() {
        BigDecimal expectedMonthlyRepayment = new BigDecimal("30.88");
        BigDecimal expectedTotalRepayment = new BigDecimal("1111.68");


        LoanDetails loanDetails = new LoanDetails("0.07", "1000");

        LoanRepayments loanRepayments = loanDetails.calculateRepayments();

        assertEquals(expectedMonthlyRepayment, loanRepayments.getMonthlyRepayment());
        assertEquals(expectedTotalRepayment, loanRepayments.getTotalRepayment());
    }

}