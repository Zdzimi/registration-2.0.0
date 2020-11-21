package com.zdzimi.registration.controller.restController.advice;

import com.zdzimi.registration.data.exception.VisitNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class VisitNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(VisitNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String visitNotFoundHandler(VisitNotFoundException exception) {
        return exception.getMessage();
    }
}
