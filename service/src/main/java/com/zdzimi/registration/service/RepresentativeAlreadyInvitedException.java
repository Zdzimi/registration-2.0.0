package com.zdzimi.registration.service;

public class RepresentativeAlreadyInvitedException extends RuntimeException {

    public RepresentativeAlreadyInvitedException(String username) {
        super("Representative " + username + " already invited.");
    }
}
