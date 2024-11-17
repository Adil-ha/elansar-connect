package com.adil.payment_service.exception;

public class MaxYearlyContributionExceededException extends RuntimeException {
    public MaxYearlyContributionExceededException(String message) {
        super(message);
    }
}
