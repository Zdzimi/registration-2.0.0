package com.zdzimi.registration.controller.restController.advice;

import com.zdzimi.registration.service.exception.DeletePlaceErrorResponse;
import com.zdzimi.registration.service.exception.DeletePlaceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DeletePlaceExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(DeletePlaceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public DeletePlaceErrorResponse deletePlaceExceptionHandler(DeletePlaceException exception) {
        return exception.getDeletePlaceErrorResponse();
    }
}
