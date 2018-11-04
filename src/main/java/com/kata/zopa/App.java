package com.kata.zopa;

import com.kata.zopa.service.InputValidationService;

import static java.lang.System.exit;

public class App {

    public static void main(String... args) {
        String csvPath;
        int loanRequest = 0;

        try {
            csvPath = args[0];
            loanRequest = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("Error: Unable to parse command line parameters [path/file.csv 1000]");
            exit(1);
        }

        exitIfInvalidLoanRequest(loanRequest);
    }

    private static void exitIfInvalidLoanRequest(int loanRequest) {
        if (!InputValidationService.isLoanRequestValid(loanRequest)) {
            System.out.println("Error: Loan Request must be multiple of £100 between £1000 and £15000 inclusive");
            exit(1);
        }
    }
}
