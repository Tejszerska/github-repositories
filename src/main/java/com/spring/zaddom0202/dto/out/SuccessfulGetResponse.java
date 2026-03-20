package com.spring.zaddom0202.dto.out;

import java.util.List;

public record SuccessfulGetResponse(String ownerLogin, List<RepoBranches> data) {
}
