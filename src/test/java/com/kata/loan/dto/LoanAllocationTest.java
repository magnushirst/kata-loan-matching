package com.kata.loan.dto;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class LoanAllocationTest {

    @Test
    public void whenAddingMultipleLenders_ThenAverageIsCalulated() {
        LoanAllocation loanAllocation = new LoanAllocation();

        loanAllocation.addLender("0.1", "50");
        loanAllocation.addLender("0.5", "50");

        Assert.assertEquals(new BigDecimal("0.3"), loanAllocation.getRate());
        Assert.assertEquals(new BigDecimal("100"), loanAllocation.getLoanedAmount());
    }
}