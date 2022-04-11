package com.registry.technicalassessment.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {BusinessApiException.class})
    public ResponseEntity<Object> handleApiRequestException(BusinessApiException ex){
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                ex.getMessage(),
                100,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiExceptionResponse,ex.getHttpStatus());
    }

}
