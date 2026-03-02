package com.spring.zaddom0202.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public record ErrorResponse(int status, @JsonProperty("Message") String message) {
}
