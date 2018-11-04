package com.kata.zopa.dto;

import org.junit.Test;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.Assert.assertEquals;

public class LoanDetailsTest {

    @Test
    public void calculatingTheInterest_ReturnCorrectValues() {
        BigDecimal expectedMonthlyRepayment = new BigDecimal("30.88");
        BigDecimal expectedTotalRepayment =  new BigDecimal("1111.58");


        LoanDetails loanDetails = new LoanDetails(0.07, 1000);

        LoanRepayments loanRepayments = loanDetails.calculateRepayments();

        assertEquals(expectedMonthlyRepayment, loanRepayments.getMonthlyRepayment().setScale(2, HALF_EVEN));
        assertEquals(expectedTotalRepayment, loanRepayments.getTotalRepayment().setScale(2, HALF_EVEN));
    }

}