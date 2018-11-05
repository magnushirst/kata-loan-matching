package com.kata.loan.matcher;

import com.kata.loan.dto.Lender;
import com.kata.loan.dto.LoanDetails;
import com.kata.loan.exception.CannotFulFillLoanRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class SplitAcrossLendersMatcherTest {
    private SplitAcrossLendersMatcher splitAcrossLendersMatcher;

    @Before
    public void setUp() {
        splitAcrossLendersMatcher = new SplitAcrossLendersMatcher(3);
    }

    @Test
    public void whenCalledWithMultipleLendersWithSameRate_ThenMatchWithSameRate() throws CannotFulFillLoanRequest {
        Lender lender1 = new Lender("Sarah", "0.1", "1000");
        Lender lender2 = new Lender("Bob", "0.1", "1000");
        Lender lender3 = new Lender("Lisa", "0.1", "1000");
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender1, lender2, lender3));

        LoanDetails loanDetails = splitAcrossLendersMatcher.match(100, lenders);
        Assert.assertEquals(new BigDecimal("10.0"), loanDetails.getYearlyRate());
        Assert.assertEquals(new BigDecimal("102"), loanDetails.getLoanAmount()); // TODO: Make this more accurate so we work out the change from the division
    }

    @Test
    public void whenCalledWithMultipleLendersWithDifferentRates_ThenLoanWillBeAverageOfTop5() throws CannotFulFillLoanRequest {
        Lender lender1 = new Lender("Sarah", "0.1", "100");
        Lender lender2 = new Lender("Bob", "0.2", "100");
        Lender lender3 = new Lender("Lisa", "0.3", "100");
        Lender lender4 = new Lender("Sam", "0.6", "1000");
        Lender lender5 = new Lender("Alex", "0.7", "1000");
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender1, lender2, lender3, lender4, lender5));

        LoanDetails loanDetails = splitAcrossLendersMatcher.match(100, lenders);
        Assert.assertEquals(new BigDecimal("20.0"), loanDetails.getYearlyRate());
        Assert.assertEquals(new BigDecimal("102"), loanDetails.getLoanAmount());
    }

    @Test(expected = CannotFulFillLoanRequest.class)
    public void whenCalledWithInsuffientAmountOfLenders_ThrowException() throws CannotFulFillLoanRequest {
        Lender lender1 = new Lender("Sarah", "0.1", "100");
        Lender lender2 = new Lender("Bob", "0.2", "100");

        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender1, lender2));

        splitAcrossLendersMatcher.match(100, lenders);
    }

    @Test(expected = CannotFulFillLoanRequest.class)
    public void whenCalledWithMultipleLendersWithInsufficientFunds_ThenThrowCannotFulfillException() throws CannotFulFillLoanRequest {
        Lender lender1 = new Lender("Sarah", "0.1", "5");
        Lender lender2 = new Lender("Bob", "0.2", "5");
        Lender lender3 = new Lender("Lisa", "0.3", "5");
        Lender lender4 = new Lender("Sam", "0.6", "5");
        Lender lender5 = new Lender("Alex", "0.7", "5");

        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender1, lender2, lender3, lender4, lender5));

        splitAcrossLendersMatcher.match(100, lenders);
    }

    @Ignore
    public void whenCalledWithMultipleLendersAndSomeInsufficientFunds_ThenOnlyMatchWithLendersThatCanFulfill() throws CannotFulFillLoanRequest {
    }
}