package com.zdzimi.registration.data.exception;

public class VisitNotFoundException extends RuntimeException {

    public VisitNotFoundException(long visitId) {
        super("Could not find: " + visitId);
    }
}
