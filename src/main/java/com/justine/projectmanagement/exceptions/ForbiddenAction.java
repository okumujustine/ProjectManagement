package com.justine.projectmanagement.exceptions;

public class ForbiddenAction extends RuntimeException {
    public ForbiddenAction(String message) {
        super(message);
    }
}
