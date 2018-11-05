package com.kata.loan.service;

import com.kata.loan.dto.Lender;
import com.kata.loan.dto.LoanDetails;
import com.kata.loan.dto.LoanRepayments;
import com.kata.loan.exception.CannotFulFillLoanRequest;
import com.kata.loan.matcher.BestRateMatcher;
import com.kata.loan.repository.LenderRepository;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

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
    public void withOnlyOneLender_ThenMatch() throws CannotFulFillLoanRequest, IOException {
        LenderRepository mockLenderRepository = mock(LenderRepository.class);

        Lender lender = new Lender("Bob", "0.07", "1000.0");
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender));
        when(mockLenderRepository.getLenders()).thenReturn(lenders);

        MatchingService matchingService = new MatchingService(mockLenderRepository, bestRateMatcher);

        LoanDetails loanDetails = matchingService.match(1000);

        assertEquals(new BigDecimal("7.0"), loanDetails.getYearlyRate());
    }

    @Test
    public void withOnlyOneLender_ThenRepaymentsAreCalculated() throws CannotFulFillLoanRequest, IOException {
        LenderRepository mockLenderRepository = mock(LenderRepository.class);
        BigDecimal expectedMonthlyRepayment = new BigDecimal("30.88");
        BigDecimal expectedTotalRepayment =  new BigDecimal("1111.68");


        Lender lender = new Lender("Bob", "0.07", "1000.0");
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender));
        when(mockLenderRepository.getLenders()).thenReturn(lenders);

        MatchingService matchingService = new MatchingService(mockLenderRepository, bestRateMatcher);

        LoanDetails loanDetails = matchingService.match(1000);
        LoanRepayments loanRepayments = loanDetails.calculateRepayments();

        assertEquals(expectedMonthlyRepayment, loanRepayments.getMonthlyRepayment());
        assertEquals(expectedTotalRepayment, loanRepayments.getTotalRepayment());
    }
}
