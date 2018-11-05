package com.kata.loan.matcher;

import com.kata.loan.dto.Lender;
import com.kata.loan.dto.LoanAllocation;
import com.kata.loan.dto.LoanDetails;
import com.kata.loan.exception.CannotFulFillLoanRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Naive implementation of the matcher which provides the lowest interest for the borrower but with the
 * most risk for the lenders. This could match 1 borrower to 1 lender.
 */

public class SplitAcrossLendersMatcher implements Matcher {
    private int lendersToSplitBy;

    public SplitAcrossLendersMatcher(int lendersToSplitBy) {
        this.lendersToSplitBy = lendersToSplitBy;
    }

    @Override
    public LoanDetails match(int loanRequest, ArrayList<Lender> lenders) throws CannotFulFillLoanRequest {
        lenders.sort(Comparator.comparing(Lender::getRate));

        BigDecimal splitLendingAmount = new BigDecimal(loanRequest).divide(new BigDecimal(lendersToSplitBy), 0, RoundingMode.CEILING);

        validateEnoughLendersToFulfillRequest(lenders);

        LoanAllocation loanAllocation = new LoanAllocation();
        for (int i = 0; i < lendersToSplitBy; i++) {
            Lender lender = lenders.get(i);
            if (lender.getAvailable().compareTo(splitLendingAmount) >= 0) {
                loanAllocation.addLender(lender.getRate(), splitLendingAmount);
            }
        }

        if (!hasLoanBeenFulfilled(new BigDecimal(loanRequest), loanAllocation)) throw new CannotFulFillLoanRequest();

        return new LoanDetails(loanAllocation.getRate(), loanAllocation.getLoanedAmount());
    }

    private void validateEnoughLendersToFulfillRequest(ArrayList<Lender> lenders) throws CannotFulFillLoanRequest {
        if (lenders.size() < lendersToSplitBy) throw new CannotFulFillLoanRequest();
    }

    private boolean hasLoanBeenFulfilled(BigDecimal loanRequest, LoanAllocation loanAllocation) {
        return loanAllocation.loanedAmount()
                .setScale(0, RoundingMode.CEILING)
                .compareTo(loanRequest) >= 0;
    }
}
