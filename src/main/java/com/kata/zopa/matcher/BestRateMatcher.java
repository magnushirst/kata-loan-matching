package com.kata.zopa.matcher;

import com.kata.zopa.dto.Lender;
import com.kata.zopa.dto.LoanAllocation;
import com.kata.zopa.dto.LoanDetails;
import com.kata.zopa.exception.CannotFulFillLoanRequest;

import java.util.ArrayList;
import java.util.Comparator;


/**
 * Naive implementation of the matcher which provides the lowest interest for the borrower but with the
 * most risk for the lenders. This could match 1 borrower to 1 lender.
 */

public class BestRateMatcher implements Matcher {
    @Override
    public LoanDetails match(int loanRequest, ArrayList<Lender> lenders) throws CannotFulFillLoanRequest {
        lenders.sort(Comparator.comparing(Lender::getRate));

        LoanAllocation loanAllocation = new LoanAllocation();
        for (Lender lender : lenders) {
            if (!hasLoanBeenFulfilled(loanRequest, loanAllocation)) {
                double outstandingLoanContribution = loanAllocation.loanedAmount() - loanRequest;
                double lendersContribution = lender.getAvailable() > outstandingLoanContribution ? lender.getAvailable() : outstandingLoanContribution;
                loanAllocation.addLender(lender.getRate(), lendersContribution);
            }
        }

        if (hasLoanBeenFulfilled(loanRequest, loanAllocation)) {
            return new LoanDetails(loanAllocation.getRate(), loanAllocation.getLoanedAmount());
        } else {
            throw new CannotFulFillLoanRequest();
        }
    }

    private boolean hasLoanBeenFulfilled(int loanRequest, LoanAllocation loanAllocation) {
        return loanAllocation.loanedAmount() == loanRequest;
    }
}
