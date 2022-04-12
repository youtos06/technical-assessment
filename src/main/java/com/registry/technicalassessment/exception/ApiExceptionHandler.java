package com.registry.technicalassessment.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZonedDateTime;


@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = {BusinessApiException.class})
    public ResponseEntity<Object> handleApiRequestException(BusinessApiException ex){
        logger.error(ex.getMessage());
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                ex.getMessage(),
                100,
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiExceptionResponse,ex.getHttpStatus());
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException (MethodArgumentTypeMismatchException ex){
        logger.error(ex.getMessage());
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                ex.getMessage(),
                101,
                ZonedDateTime.now()
        );
        return new ResponseEntity(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> MethodArgumentNotValidException(MethodArgumentNotValidException ex){
        logger.error(ex.getBindingResult().getFieldError().getDefaultMessage());
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                ex.getBindingResult().getFieldError().getDefaultMessage(),
                102,
                ZonedDateTime.now()
        );
        return new ResponseEntity(apiExceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
