package com.registry.technicalassessment.exception;

import org.springframework.http.HttpStatus;

public class BusinessApiException extends RuntimeException {
    private HttpStatus httpStatus;

    public BusinessApiException(String message,HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
