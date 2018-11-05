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

public class BestRateMatcher implements Matcher {
    @Override
    public LoanDetails match(int loanRequest, ArrayList<Lender> lenders) throws CannotFulFillLoanRequest {
        lenders.sort(Comparator.comparing(Lender::getRate));

        LoanAllocation loanAllocation = new LoanAllocation();
        for (Lender lender : lenders) {
            if (!hasLoanBeenFulfilled(loanRequest, loanAllocation)) {
                BigDecimal outstandingLoanContribution = loanAllocation.loanedAmount().subtract(new BigDecimal(loanRequest));
                BigDecimal lendersContribution = lender.getAvailable().compareTo(outstandingLoanContribution) == 1
                        ? lender.getAvailable()
                        : outstandingLoanContribution;
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
        return loanAllocation.loanedAmount()
                .setScale(0, RoundingMode.FLOOR)
                .compareTo(new BigDecimal(loanRequest)) == 0;
    }
}
