package com.spring.zaddom0202.controller;

import com.spring.zaddom0202.dto.out.ErrorResponse;
import com.spring.zaddom0202.exception.DuplicateRepoException;
import com.spring.zaddom0202.exception.RepoNotFoundException;
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
        log.warn("{} -> {}", e.status(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorResponse> handleWrongMedia(HttpMediaTypeNotAcceptableException e) {
        ErrorResponse errorResponse = new ErrorResponse(406, "Accept header must be application/json");
        log.warn("406  -> {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }

    @ExceptionHandler(RepoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoRepoRecord(RepoNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(404, "Repo not found in the database");
        log.warn("404 -> {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(DuplicateRepoException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRecord(DuplicateRepoException e) {
        ErrorResponse errorResponse = new ErrorResponse(400, "Duplicates aren't allowed in the database");
        log.warn("400 -> {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
