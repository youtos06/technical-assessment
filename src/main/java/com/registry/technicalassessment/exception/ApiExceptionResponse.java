package com.registry.technicalassessment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class ApiExceptionResponse {

    private final String message;
    private final int code;
    private final ZonedDateTime timeStamp;
}
