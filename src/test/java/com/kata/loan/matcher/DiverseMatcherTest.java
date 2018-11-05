package com.kata.loan.matcher;

import com.kata.loan.dto.Lender;
import com.kata.loan.dto.LoanDetails;
import com.kata.loan.exception.CannotFulFillLoanRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class DiverseMatcherTest {
    private DiverseMatcher diverseMatcher;

    @Before
    public void setUp() {
        diverseMatcher = new DiverseMatcher();
    }

    @Test
    public void whenCalledWithMultipleLendersWithSameRate_ThenMatchWholeLoanWithRate() throws CannotFulFillLoanRequest {
        Lender lender1 = new Lender("Sarah", "0.1", "100");
        Lender lender2 = new Lender("Bob", "0.1", "100");
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender1, lender2));

        LoanDetails loanDetails = diverseMatcher.match(100, lenders);
        Assert.assertEquals(new BigDecimal("10.0"), loanDetails.getYearlyRate());
        Assert.assertEquals(new BigDecimal("100.00"), loanDetails.getLoanAmount());
    }

    @Test
    public void whenCalledWithMultipleLendersWithDifferentRates_ThenMatchWithAverageRate() throws CannotFulFillLoanRequest {
        Lender lender1 = new Lender("Sarah", "0.01", "100");
        Lender lender2 = new Lender("Bob", "0.02", "100");
        Lender lender3 = new Lender("Sally", "0.03", "100");
        Lender lender4 = new Lender("Jane", "0.04", "100");
        Lender lender5 = new Lender("Dan", "0.05", "100");
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender1, lender2, lender3, lender4, lender5));

        LoanDetails loanDetails = diverseMatcher.match(100, lenders);
        Assert.assertEquals(new BigDecimal("3.0"), loanDetails.getYearlyRate());
        Assert.assertEquals(new BigDecimal("100.00"), loanDetails.getLoanAmount());
    }

    @Test
    public void whenCalledWithLendersThatHaveInsufficentFunds_ThenIgnoreThem() throws CannotFulFillLoanRequest {
        Lender lender1 = new Lender("Sarah", "0.01", "10");
        Lender lender2 = new Lender("Bob", "0.02", "100");
        Lender lender3 = new Lender("Sally", "0.03", "100");
        Lender lender4 = new Lender("Jane", "0.04", "100");

        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender1, lender2, lender3, lender4));

        LoanDetails loanDetails = diverseMatcher.match(100, lenders);
        Assert.assertEquals(new BigDecimal("3.0"), loanDetails.getYearlyRate());
        Assert.assertEquals(new BigDecimal("100.00"), loanDetails.getLoanAmount());
    }




}