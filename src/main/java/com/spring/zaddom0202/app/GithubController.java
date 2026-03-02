package com.spring.zaddom0202.app;

import com.spring.zaddom0202.dto.out.SuccessfulResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class GithubController {
    private final GithubService githubService;

    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse> getGitHubData(@PathVariable String username) {
        SuccessfulResponse successfulResponse = githubService.generateResponse(username);
        return ResponseEntity.ok(successfulResponse);

    }

}