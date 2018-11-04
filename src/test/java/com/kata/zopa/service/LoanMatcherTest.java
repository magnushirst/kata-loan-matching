package com.kata.zopa.service;

import com.kata.zopa.dto.Lender;
import com.kata.zopa.dto.LoanDetails;
import com.kata.zopa.dto.LoanRepayments;
import com.kata.zopa.exception.CannotFulFillLoanRequest;
import com.kata.zopa.matcher.BestRateMatcher;
import com.kata.zopa.repository.LenderRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoanMatcherTest {

    private BestRateMatcher bestRateMatcher = new BestRateMatcher();

    @Before
    public void setUp() {
        bestRateMatcher = new BestRateMatcher();
    }

    @Test
    public void withOnlyOneLender_ThenMatch() throws CannotFulFillLoanRequest {
        LenderRepository mockLenderRepository = mock(LenderRepository.class);

        Lender lender = new Lender("Bob", 0.07, 1000.0);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender));
        when(mockLenderRepository.getLenders()).thenReturn(lenders);

        MatchingService matchingService = new MatchingService(mockLenderRepository, bestRateMatcher);

        LoanDetails loanDetails = matchingService.match(1000);

        assertEquals(0.07, loanDetails.getYearlyRate(), 0);
    }

    @Test
    public void withOnlyOneLender_ThenRepaymentsAreCalculated() throws CannotFulFillLoanRequest {
        LenderRepository mockLenderRepository = mock(LenderRepository.class);
        BigDecimal expectedMonthlyRepayment = new BigDecimal("30.88");
        BigDecimal expectedTotalRepayment =  new BigDecimal("1111.58");


        Lender lender = new Lender("Bob", 0.07, 1000.0);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender));
        when(mockLenderRepository.getLenders()).thenReturn(lenders);

        MatchingService matchingService = new MatchingService(mockLenderRepository, bestRateMatcher);

        LoanDetails loanDetails = matchingService.match(1000);
        LoanRepayments loanRepayments = loanDetails.calculateRepayments();

        assertEquals(expectedMonthlyRepayment, loanRepayments.getMonthlyRepayment().setScale(2, HALF_EVEN));
        assertEquals(expectedTotalRepayment, loanRepayments.getTotalRepayment().setScale(2, HALF_EVEN));
    }
}
