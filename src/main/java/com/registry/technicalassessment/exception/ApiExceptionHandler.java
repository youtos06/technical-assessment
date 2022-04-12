package com.registry.technicalassessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException (MethodArgumentTypeMismatchException ex){
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                ex.getMessage(),
                101,
                ZonedDateTime.now()
        );
        return new ResponseEntity(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> MethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                ex.getBindingResult().getFieldError().getDefaultMessage(),
                102,
                ZonedDateTime.now()
        );
        return new ResponseEntity(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
