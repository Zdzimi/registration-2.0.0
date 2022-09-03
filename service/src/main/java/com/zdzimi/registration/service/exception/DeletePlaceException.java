package com.zdzimi.registration.service.exception;

import lombok.Getter;

@Getter
public class DeletePlaceException extends RuntimeException {

    private final DeletePlaceErrorResponse deletePlaceErrorResponse;

    public DeletePlaceException(String placeName) {
        this.deletePlaceErrorResponse = new DeletePlaceErrorResponse(placeName);
    }
}
