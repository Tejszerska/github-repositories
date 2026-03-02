package com.spring.zaddom0202.dto.out;

import com.spring.zaddom0202.dto.out.BranchOut;

import java.util.List;

public record RepoBranches(String repositoryName, List<BranchOut> branches) {
}
