package com.spring.zaddom0202.dto.out;

import java.util.List;

public record SuccessfulResponse(String ownerLogin, List<RepoBranches> data) {
}
