package com.zdzimi.registration.service.exception;

import lombok.Getter;

@Getter
public class DeletePlaceErrorResponse {

    private final String placeName;
    private final String message;

    public DeletePlaceErrorResponse(String placeName) {
        this.placeName = placeName;
        this.message = "Cannot delete place: " + placeName;
    }
}
