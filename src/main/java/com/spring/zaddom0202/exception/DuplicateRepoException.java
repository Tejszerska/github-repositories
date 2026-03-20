package com.spring.zaddom0202.exception;

public class DuplicateRepoException extends RuntimeException{
    public DuplicateRepoException(String message) {
        super(message);
    }
}
