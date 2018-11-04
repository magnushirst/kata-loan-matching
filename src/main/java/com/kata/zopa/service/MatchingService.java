package com.kata.zopa.service;

import com.kata.zopa.dto.Lender;
import com.kata.zopa.dto.LoanDetails;
import com.kata.zopa.exception.CannotFullFillLoanRequest;
import com.kata.zopa.matcher.Matcher;
import com.kata.zopa.repository.LenderRepository;

import java.util.ArrayList;

public class MatchingService {
    LenderRepository lenderRepository;
    Matcher matcher;
    public MatchingService(LenderRepository lenderRepository, Matcher matcher) {
        this.lenderRepository = lenderRepository;
        this.matcher = matcher;
    }

    public LoanDetails match(int loanRequest) throws CannotFullFillLoanRequest {
        ArrayList<Lender> lenders = lenderRepository.getLenders();
        LoanDetails loanDetails = matcher.match(loanRequest, lenders);
        return loanDetails;
    }
}
