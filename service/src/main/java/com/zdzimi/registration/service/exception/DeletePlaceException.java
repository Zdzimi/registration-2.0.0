package com.zdzimi.registration.service.exception;

import lombok.Getter;

@Getter
public class DeletePlaceException extends RuntimeException {

    private DeletePlaceErrorResponse deletePlaceErrorResponse;

    public DeletePlaceException(String placeName) {
        this.deletePlaceErrorResponse = new DeletePlaceErrorResponse(placeName);
    }
}
