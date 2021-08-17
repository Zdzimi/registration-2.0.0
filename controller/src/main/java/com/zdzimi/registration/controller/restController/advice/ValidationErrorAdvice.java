package com.zdzimi.registration.controller.restController.advice;

import com.zdzimi.registration.core.model.validation.ValidationErrorResponse;
import com.zdzimi.registration.core.model.validation.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ValidationErrorAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException exception) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        for (ConstraintViolation violation : exception.getConstraintViolations()) {
            errorResponse.getViolations().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorResponse.getViolations().add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return errorResponse;
    }
}
