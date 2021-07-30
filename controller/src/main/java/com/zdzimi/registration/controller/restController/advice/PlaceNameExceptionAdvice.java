package com.zdzimi.registration.controller.restController.advice;

import com.zdzimi.registration.service.PlaceNameException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class PlaceNameExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(PlaceNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String placeNameExceptionHandler(PlaceNameException e) {
        return e.getMessage();
    }
}
