package com.zdzimi.registration.controller.restController.advice;

import com.zdzimi.registration.controller.restController.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidPasswordAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidPasswordException.class)
    public String badPasswordHandler(InvalidPasswordException e) {
        return e.getMessage();
    }
}
