package com.zdzimi.registration.controller.restController.advice;

import com.zdzimi.registration.service.exception.NoVisitsCreatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class NoVisitsCreatedExceptionAdvice {

  @ResponseBody
  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(NoVisitsCreatedException.class)
  public String noVisitsCreatedExceptionHandler(NoVisitsCreatedException exception) {
    return exception.getMessage();
  }

}
