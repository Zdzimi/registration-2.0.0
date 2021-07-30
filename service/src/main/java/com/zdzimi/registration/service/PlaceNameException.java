package com.zdzimi.registration.service;

import javax.validation.constraints.NotNull;

public class PlaceNameException extends RuntimeException {

    public PlaceNameException(@NotNull String placeName) {
        super("Place: " + placeName + " already exist.");
    }
}
