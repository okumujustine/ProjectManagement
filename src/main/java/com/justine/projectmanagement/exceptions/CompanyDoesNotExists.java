package com.justine.projectmanagement.exceptions;

public class CompanyDoesNotExists extends RuntimeException {
    public CompanyDoesNotExists(String message) {
        super(message);
    }
}
