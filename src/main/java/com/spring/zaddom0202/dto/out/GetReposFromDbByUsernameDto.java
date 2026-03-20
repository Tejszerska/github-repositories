package com.spring.zaddom0202.dto.out;

import com.spring.zaddom0202.dto.RepoDto;

import java.util.List;

public record GetReposFromDbByUsernameDto(List<RepoDto> repo) {
}
