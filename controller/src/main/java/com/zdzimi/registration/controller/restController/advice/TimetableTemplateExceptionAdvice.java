package com.zdzimi.registration.controller.restController.advice;

import com.zdzimi.registration.core.model.template.TimetableTemplateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class TimetableTemplateExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(TimetableTemplateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String timetableTemplateHandler(TimetableTemplateException exception) {
        return exception.getMessage();
    }
}
