package com.kata.zopa.matcher;

import com.kata.zopa.dto.Lender;
import com.kata.zopa.dto.LoanDetails;
import com.kata.zopa.exception.CannotFullFillLoanRequest;

import java.util.ArrayList;

public interface Matcher {

    LoanDetails match(int loanRequest, ArrayList<Lender> lenders) throws CannotFullFillLoanRequest;
}
