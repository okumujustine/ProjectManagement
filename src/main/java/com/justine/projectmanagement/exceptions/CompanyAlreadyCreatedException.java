package com.justine.projectmanagement.exceptions;

public class CompanyAlreadyCreatedException extends RuntimeException {
    public CompanyAlreadyCreatedException(String message) {
        super(message);
    }
}
