package com.zdzimi.registration.controller.restController.advice;

import com.zdzimi.registration.data.exception.PlaceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class PlaceNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PlaceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String placeNotFoundHandler(PlaceNotFoundException exception) {
        return exception.getMessage();
    }
}
