package com.kata.loan.matcher;

import com.kata.loan.dto.Lender;
import com.kata.loan.dto.LoanDetails;
import com.kata.loan.exception.CannotFulFillLoanRequest;

import java.util.ArrayList;

public interface Matcher {

    LoanDetails match(int loanRequest, ArrayList<Lender> lenders) throws CannotFulFillLoanRequest;
}
