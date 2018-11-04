package com.kata.zopa.service;

public class InputValidationService {

    private static final int LOWER_LIMIT = 1000;
    private static final int UPPER_LIMIT = 15000;
    private static final int INCREMENT_AMOUNT = 100;

    public static boolean isLoanRequestValid(int loanRequest) {
        return loanRequest >= LOWER_LIMIT
                && loanRequest <= UPPER_LIMIT
                && loanRequest % INCREMENT_AMOUNT == 0;
    }
}
