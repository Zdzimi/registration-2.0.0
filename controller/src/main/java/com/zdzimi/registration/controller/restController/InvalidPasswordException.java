package com.zdzimi.registration.controller.restController;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Invalid password");
    }
}
