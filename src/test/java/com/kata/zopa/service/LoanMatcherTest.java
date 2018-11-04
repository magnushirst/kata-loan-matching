package com.kata.zopa.service;

import com.kata.zopa.dto.Lender;
import com.kata.zopa.dto.LoanDetails;
import com.kata.zopa.exception.CannotFullFillLoanRequest;
import com.kata.zopa.matcher.BestRateMatcher;
import com.kata.zopa.repository.LenderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoanMatcherTest {

    private BestRateMatcher bestRateMatcher = new BestRateMatcher();

    @Before
    public void setUp() {
        bestRateMatcher = new BestRateMatcher();
    }

    @Test
    public void withOnlyOneLender_ThenMatch() throws CannotFullFillLoanRequest {
        //given
        LenderRepository mockLenderRepository = mock(LenderRepository.class);

        Lender lender = new Lender("Bob", 0.1, 100.0);
        ArrayList<Lender> lenders = new ArrayList<>(Arrays.asList(lender));
        when(mockLenderRepository.getLenders()).thenReturn(lenders);

        MatchingService matchingService = new MatchingService(mockLenderRepository, bestRateMatcher);

        //when
        LoanDetails loanDetails = matchingService.match(100);

        //then
        Assert.assertEquals(0.1, loanDetails.getRate(), 0);
    }
}
