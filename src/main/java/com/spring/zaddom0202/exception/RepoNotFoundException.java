package com.spring.zaddom0202.exception;

public class RepoNotFoundException extends RuntimeException{
    public RepoNotFoundException(String message) {
        super(message);
    }
}
