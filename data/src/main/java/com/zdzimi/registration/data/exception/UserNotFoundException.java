package com.zdzimi.registration.data.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("Could not find user: " + username);
    }

    public UserNotFoundException(String representativeName, String institutionName) {
        super("Could not find representative: " + representativeName + " in " + institutionName);
    }
}
