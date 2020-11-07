package com.zdzimi.registration.data.exception;

public class InstitutionNotFoundException extends RuntimeException {

    public InstitutionNotFoundException(String institutionName) {
        super("Could not find institution: " + institutionName);
    }
    public InstitutionNotFoundException(String username, String institutionName) {
        super("Could not find institution: " + institutionName + " where " + username + " is representative");
    }
}
