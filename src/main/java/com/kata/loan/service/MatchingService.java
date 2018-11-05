package com.kata.loan.service;

import com.kata.loan.dto.Lender;
import com.kata.loan.dto.LoanDetails;
import com.kata.loan.exception.CannotFulFillLoanRequest;
import com.kata.loan.matcher.Matcher;
import com.kata.loan.repository.LenderRepository;

import java.io.IOException;
import java.util.ArrayList;

public class MatchingService {
    LenderRepository lenderRepository;
    Matcher matcher;
    public MatchingService(LenderRepository lenderRepository, Matcher matcher) {
        this.lenderRepository = lenderRepository;
        this.matcher = matcher;
    }

    public LoanDetails match(int loanRequest) throws CannotFulFillLoanRequest, IOException {
        ArrayList<Lender> lenders = lenderRepository.getLenders();
        LoanDetails loanDetails = matcher.match(loanRequest, lenders);
        return loanDetails;
    }
}
