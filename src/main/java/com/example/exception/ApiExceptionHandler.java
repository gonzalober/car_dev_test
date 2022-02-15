package com.example.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(value = { ApiRequestException.class })
  public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
    // payload
    ApiException apiException = new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST,
        ZonedDateTime.now(ZoneId.of("Z")));
    // return response
    return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
  }
}
