package com.zdzimi.registration.service.exception;

public class RepresentativeAlreadyInvitedException extends RuntimeException {

    public RepresentativeAlreadyInvitedException(String username) {
        super("Representative " + username + " already invited.");
    }
}
