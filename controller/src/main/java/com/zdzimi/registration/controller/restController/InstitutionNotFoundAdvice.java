package com.zdzimi.registration.controller.restController;

import com.zdzimi.registration.data.exception.InstitutionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InstitutionNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(InstitutionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String institutionNotFoundHandler(InstitutionNotFoundException e) {
        return e.getMessage();
    }
}
