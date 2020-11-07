package com.zdzimi.registration.data.exception;

public class PlaceNotFoundException extends RuntimeException {

    public PlaceNotFoundException(String institutionName, String placeName) {
        super("Could not find result for institution: " + institutionName + ", and place: " + placeName);
    }
}
