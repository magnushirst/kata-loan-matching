package com.kata.zopa.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InputValidationServiceTest {

    @Test
    public void whenPassed0_ReturnFalse() {
        assertEquals(false, InputValidationService.isLoanRequestValid(0)); // Test failure message isn't as clear if you use assertFalse
    }

    @Test
    public void whenPassed1000_ReturnTrue() {
        assertEquals(true, InputValidationService.isLoanRequestValid(1000));
    }

    @Test
    public void whenPassed15000_ReturnTrue() {
        assertEquals(true, InputValidationService.isLoanRequestValid(15000));
    }

    @Test
    public void whenPassed15001_ReturnFalse() {
        assertEquals(false, InputValidationService.isLoanRequestValid(15001));
    }

    @Test
    public void whenRequestIsInRangeButNotIncrementOf100_ReturnFalse() {
        assertEquals(false, InputValidationService.isLoanRequestValid(1099));
    }

}