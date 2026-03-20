package com.spring.zaddom0202.dto.out;

import org.springframework.http.HttpStatus;

public record DeleteRepoResponse(String message, HttpStatus status) {
}
