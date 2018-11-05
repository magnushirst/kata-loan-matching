package com.kata.loan.matcher;

import com.kata.loan.dto.Lender;
import com.kata.loan.dto.LoanAllocation;
import com.kata.loan.dto.LoanDetails;
import com.kata.loan.exception.CannotFulFillLoanRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


/**
 * Naive implementation of the matcher which provides the lowest interest for the borrower but with the
 * most risk for the lenders. This could match 1 borrower to 1 lender.
 */

public class DiverseMatcher implements Matcher {
    @Override
    public LoanDetails match(int loanRequest, ArrayList<Lender> lenders) throws CannotFulFillLoanRequest {
        BigDecimal splitLendingAmount = new BigDecimal(loanRequest).divide(new BigDecimal(lenders.size()), 2, RoundingMode.HALF_EVEN);

        LoanAllocation loanAllocation = new LoanAllocation();
        for (Lender lender : lenders) {
            if (lender.getAvailable().compareTo(splitLendingAmount) >= 0) {
                loanAllocation.addLender(lender.getRate(), splitLendingAmount);
            }
        }
        return new LoanDetails(loanAllocation.getRate(), loanAllocation.getLoanedAmount());
    }

    private boolean hasLoanBeenFulfilled(BigDecimal loanRequest, LoanAllocation loanAllocation) {
        return loanAllocation.loanedAmount()
                .setScale(0, RoundingMode.FLOOR)
                .compareTo(loanRequest) == 0;
    }
}
