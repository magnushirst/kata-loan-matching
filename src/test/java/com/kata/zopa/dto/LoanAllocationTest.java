package com.kata.zopa.dto;

import org.junit.Assert;
import org.junit.Test;

public class LoanAllocationTest {

    @Test
    public void whenAddingMultipleLenders_ThenAverageIsCalulated() {
        LoanAllocation loanAllocation = new LoanAllocation();

        loanAllocation.addLender(0.1, 50);
        loanAllocation.addLender(0.5, 50);

        Assert.assertEquals(0.3, loanAllocation.getRate(), 0);
        Assert.assertEquals(100, loanAllocation.getLoanedAmount(), 0);
    }
}