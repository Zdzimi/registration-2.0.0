package com.zdzimi.registration.data.exception;

public class InstitutionNotFoundException extends RuntimeException {

    public InstitutionNotFoundException(String institutionName) {
        super("Could not find institution: " + institutionName);
    }
}
