package com.kata.zopa;

import com.kata.zopa.dto.LoanDetails;
import com.kata.zopa.dto.LoanRepayments;
import com.kata.zopa.exception.CannotFulFillLoanRequest;
import com.kata.zopa.matcher.BestRateMatcher;
import com.kata.zopa.matcher.Matcher;
import com.kata.zopa.repository.LenderRepository;
import com.kata.zopa.service.InputValidationService;
import com.kata.zopa.service.MatchingService;

import java.math.RoundingMode;

import static java.lang.System.exit;
import static java.math.RoundingMode.HALF_EVEN;

public class App {

    public static void main(String... args) {
        String csvPath = "";
        int loanRequest = 0;

        try {
            csvPath = args[0];
            loanRequest = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("Error: Unable to parse command line parameters [path/file.csv 1000]");
            exit(1);
        }

        exitIfInvalidLoanRequest(loanRequest);

        LenderRepository lenderRepository = new LenderRepository(csvPath);
        Matcher matcher = new BestRateMatcher();
        MatchingService matchingService = new MatchingService(lenderRepository, matcher);

        try{
            LoanDetails loanDetails = matchingService.match(loanRequest);
            LoanRepayments loanRepayments = loanDetails.calculateRepayments();
            System.out.println(String.format("Requested amount: £%d", loanRequest));
            System.out.println(String.format("Rate: %d%", loanDetails.getYearlyRate()));
            System.out.println(String.format("Monthly repayment: £%d", loanRepayments.getMonthlyRepayment().setScale(2, HALF_EVEN)));
            System.out.println(String.format("Total repayment: £%d", loanRepayments.getTotalRepayment().setScale(2, HALF_EVEN)));


        }catch (CannotFulFillLoanRequest cannotFulFillLoanRequest){
            exitWithUnableToMatch();
        }
    }

    private static void exitWithUnableToMatch() {
        System.out.println("Sorry we are unable to match your request to our lenders. Please try again with a lower amount");
        exit(1);
    }

    private static void exitIfInvalidLoanRequest(int loanRequest) {
        if (!InputValidationService.isLoanRequestValid(loanRequest)) {
            System.out.println("Error: Loan Request must be multiple of £100 between £1000 and £15000 inclusive");
            exit(1);
        }
    }
}
