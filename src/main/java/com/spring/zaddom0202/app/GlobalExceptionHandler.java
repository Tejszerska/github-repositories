package com.spring.zaddom0202.app;

import com.spring.zaddom0202.dto.out.ErrorResponse;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleNotFound(FeignException.NotFound e) {
        ErrorResponse errorResponse = new ErrorResponse(404, "User not found");
        log.warn(e.status() + " -> " + e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorResponse> handleWrongMedia(HttpMediaTypeNotAcceptableException e) {
        ErrorResponse errorResponse = new ErrorResponse(406, "Accept header must be application/json");
        log.warn("406  -> " + e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }
}
