package com.customer.demo.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

@ControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(e -> (FieldError) e)
                .collect(toMap(FieldError::getField, e -> defaultIfBlank(e.getDefaultMessage(), "invalid value")));
        ex.getBody().setProperty("errors", errors);
        return createResponseEntity(ex.getBody(), headers, status, request);
    }
}