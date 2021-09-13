package com.zdzimi.registration.controller.restController.advice;

import com.zdzimi.registration.service.RepresentativeAlreadyInvitedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RepresentativeAlreadyInvitedAdvice {

    @ResponseBody
    @ExceptionHandler(RepresentativeAlreadyInvitedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String representativeAlreadyInvitedHandler(RepresentativeAlreadyInvitedException exception) {
        return exception.getMessage();
    }
}
