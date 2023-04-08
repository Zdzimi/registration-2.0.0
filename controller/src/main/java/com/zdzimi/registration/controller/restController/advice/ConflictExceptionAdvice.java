package com.zdzimi.registration.controller.restController.advice;

import com.zdzimi.registration.service.exception.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
public class ConflictExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public List<String> conflictExceptionHandler(ConflictException exception) {
        return exception.getConflicts();
    }

}
