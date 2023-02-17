package org.example.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ResponseStatusException.class)
    protected ResponseEntity<Object> handleResponseException(ResponseStatusException exception, WebRequest request){
        return handleExceptionInternal(exception,exception.getReason(),new HttpHeaders(),exception.getStatusCode(),request);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    protected ResponseEntity<Object> handleStateException(IllegalStateException exception, WebRequest request){
        return handleExceptionInternal(exception,"Field already in use",new HttpHeaders(), HttpStatus.BAD_REQUEST,request);
    }
}
