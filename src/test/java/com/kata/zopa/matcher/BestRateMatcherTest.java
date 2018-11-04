package com.kata.zopa.matcher;

import com.kata.zopa.dto.Lender;
import com.kata.zopa.dto.LoanDetails;
import com.kata.zopa.exception.CannotFullFillLoanRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class BestRateMatcherTest {
    private BestRateMatcher bestRateMatcher;

    @Before
    public void setUp() {
        bestRateMatcher = new BestRateMatcher();
    }

    @Test
    public void whenCalledWithOneLender_ThenMatchWith() throws CannotFullFillLoanRequest {
        Lender lender = new Lender("Sarah", 0.1, 100);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender));

        LoanDetails loanDetails = bestRateMatcher.match(100, lenders);

        Assert.assertEquals(.1, loanDetails.getRate(), 0);
        Assert.assertEquals(100, loanDetails.getLoanAmount(), 0);
    }

    @Test
    public void whenCalledWithMultipleLenders_ThenMatchWithLowestRate() throws CannotFullFillLoanRequest {
        Lender expensivelender = new Lender("Emma", 1.0, 100);
        Lender cheapLender = new Lender("Sarah", 0.5, 100);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(expensivelender, cheapLender));

        LoanDetails loanDetails = bestRateMatcher.match(100, lenders);

        Assert.assertEquals(0.5, loanDetails.getRate(), 0);
        Assert.assertEquals(100, loanDetails.getLoanAmount(), 0);
    }

    @Test
    public void whenCalledWithMultipleSmallLenders_ThenMatchWithLowestRates() throws CannotFullFillLoanRequest {
        Lender smallLender1 = new Lender("Emma", 0.1, 50);
        Lender smallLender2 = new Lender("Sarah", 0.5, 50);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(smallLender1, smallLender2));

        LoanDetails loanDetails = bestRateMatcher.match(100, lenders);

        Assert.assertEquals(0.3, loanDetails.getRate(), 0);
    }

    @Test
    public void whenCalledWithMultipleLendersFilterOutHighRates_ThenMatchWithLowestRates() throws CannotFullFillLoanRequest {
        Lender smallLender1 = new Lender("Emma", 0.1, 50);
        Lender smallLender2 = new Lender("Sarah", 0.5, 50);
        Lender expensiveLender = new Lender("Sarah", 5.0, 50);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(smallLender1, smallLender2, expensiveLender));

        LoanDetails loanDetails = bestRateMatcher.match(100, lenders);

        Assert.assertEquals(0.3, loanDetails.getRate(), 0);
        Assert.assertEquals(100, loanDetails.getLoanAmount(), 0);
    }

    @Test(expected = CannotFullFillLoanRequest.class)
    public void whenLoanCannotBeFilledCompletely_ReturnException() throws CannotFullFillLoanRequest {
        Lender smallLender1 = new Lender("Emma", 0.1, 50);
        Lender smallLender2 = new Lender("Sarah", 0.5, 50);
        Lender expensiveLender = new Lender("Sarah", 5.0, 50);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(smallLender1, smallLender2, expensiveLender));

        bestRateMatcher.match(200, lenders);
    }
}
