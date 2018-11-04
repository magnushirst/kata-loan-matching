package com.kata.zopa.matcher;

import com.kata.zopa.dto.Lender;
import com.kata.zopa.dto.LoanDetails;
import com.kata.zopa.exception.CannotFulFillLoanRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class BestRateMatcherTest {
    private BestRateMatcher bestRateMatcher;

    @Before
    public void setUp() {
        bestRateMatcher = new BestRateMatcher();
    }

    @Test
    public void whenCalledWithOneLender_ThenMatch() throws CannotFulFillLoanRequest {
        Lender lender = new Lender("Sarah", 0.1, 100);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender));

        LoanDetails loanDetails = bestRateMatcher.match(100, lenders);

        assertEquals(.1, loanDetails.getYearlyRate(), 0);
        assertEquals(100, loanDetails.getLoanAmount(), 0);
    }

    @Test
    public void whenCalledWithMultipleLenders_ThenMatchWithLowestRate() throws CannotFulFillLoanRequest {
        Lender expensivelender = new Lender("Emma", 1.0, 100);
        Lender cheapLender = new Lender("Sarah", 0.5, 100);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(expensivelender, cheapLender));

        LoanDetails loanDetails = bestRateMatcher.match(100, lenders);

        assertEquals(0.5, loanDetails.getYearlyRate(), 0);
        assertEquals(100, loanDetails.getLoanAmount(), 0);
    }

    @Test
    public void whenCalledWithMultipleSmallLenders_ThenMatchWithLowestRates() throws CannotFulFillLoanRequest {
        Lender smallLender1 = new Lender("Emma", 0.1, 50);
        Lender smallLender2 = new Lender("Sarah", 0.5, 50);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(smallLender1, smallLender2));

        LoanDetails loanDetails = bestRateMatcher.match(100, lenders);

        assertEquals(0.3, loanDetails.getYearlyRate(), 0);
    }

    @Test
    public void whenCalledWithMultipleLendersFilterOutHighRates_ThenMatchWithLowestRates() throws CannotFulFillLoanRequest {
        Lender smallLender1 = new Lender("Emma", 0.1, 50);
        Lender smallLender2 = new Lender("Sarah", 0.5, 50);
        Lender expensiveLender = new Lender("Sarah", 5.0, 50);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(smallLender1, smallLender2, expensiveLender));

        LoanDetails loanDetails = bestRateMatcher.match(100, lenders);

        assertEquals(0.3, loanDetails.getYearlyRate(), 0);
        assertEquals(100, loanDetails.getLoanAmount(), 0);
    }

    @Test(expected = CannotFulFillLoanRequest.class)
    public void whenLoanCannotBeFilledCompletely_ThrowCannotFulfillException() throws CannotFulFillLoanRequest {
        Lender smallLender1 = new Lender("Emma", 0.1, 50);
        Lender smallLender2 = new Lender("Sarah", 0.5, 50);
        Lender expensiveLender = new Lender("Sarah", 5.0, 50);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(smallLender1, smallLender2, expensiveLender));

        bestRateMatcher.match(200, lenders);
    }

    @Test(expected = CannotFulFillLoanRequest.class)
    public void whenThereAreNoLenders_ThrowCannotFulfillException() throws CannotFulFillLoanRequest {
        ArrayList<Lender> lenders = new ArrayList<>();
        bestRateMatcher.match(200, lenders);
    }
}
