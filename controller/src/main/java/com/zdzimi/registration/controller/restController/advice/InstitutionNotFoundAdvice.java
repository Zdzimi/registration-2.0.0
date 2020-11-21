package com.zdzimi.registration.controller.restController.advice;

import com.zdzimi.registration.data.exception.InstitutionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class InstitutionNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(InstitutionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String institutionNotFoundHandler(InstitutionNotFoundException e) {
        return e.getMessage();
    }
}
