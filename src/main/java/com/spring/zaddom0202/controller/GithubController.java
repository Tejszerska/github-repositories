package com.spring.zaddom0202.controller;

import com.spring.zaddom0202.dto.RepoDto;
import com.spring.zaddom0202.dto.in.CreateRepoDto;
import com.spring.zaddom0202.dto.in.PatchRepoRequest;
import com.spring.zaddom0202.dto.out.*;
import com.spring.zaddom0202.mapper.RepoMapper;
import com.spring.zaddom0202.service.GithubServiceInitial;
import com.spring.zaddom0202.service.ModifyingService;
import com.spring.zaddom0202.service.ReadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping
public class GithubController {
    private final GithubServiceInitial githubServiceInitial;
    private final ModifyingService modifyingService;
    private final ReadingService readingService;

    // just get JSON from GitHub
    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulGetResponse> getGitHubData(@PathVariable String username) {
        SuccessfulGetResponse successfulGetResponse = githubServiceInitial.generateResponse(username);
        return ResponseEntity.ok(successfulGetResponse);
    }

    // save to database from GitHub
    @PostMapping(value = "/db/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulSaveGitHubResponse> saveGitHubData(@PathVariable String username) {
        List<RepoDto> repos = modifyingService.saveFromGitHub(username);
        SuccessfulSaveGitHubResponse successfulSaveGitHubResponse = new SuccessfulSaveGitHubResponse(repos);
        return ResponseEntity.ok(successfulSaveGitHubResponse);
    }

    //save to database from JSON
    @PostMapping(value = "/db/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulSaveJsonResponse> saveJsonData(@RequestBody CreateRepoDto request) {
        RepoDto repoFromJson = RepoMapper.mapFromCreateRepoDtoToRepo(request);
        RepoDto repoAfterSaving = modifyingService.addRepo(repoFromJson);
        return ResponseEntity.ok(new SuccessfulSaveJsonResponse(repoAfterSaving));
    }


    // get all repos from db
    @GetMapping("/db/all")
    public ResponseEntity<Page<RepoDto>> getFromDB(@PageableDefault(size = 5) Pageable pageable) {
        Page<RepoDto> allRepos = readingService.getAllRepos(pageable);
        return ResponseEntity.ok(allRepos);
    }

    //get by username from db
    @GetMapping("/db/{username}/")
    public ResponseEntity<GetReposFromDbByUsernameDto> getFromDbByUsername(@PathVariable String username) {
        List<RepoDto> reposByUsername = readingService.getByUsername(username);
        GetReposFromDbByUsernameDto getReposFromDbByUsernameDto = new GetReposFromDbByUsernameDto(reposByUsername);
        return ResponseEntity.ok(getReposFromDbByUsernameDto);
    }

    @DeleteMapping("/db/{id}")
    public ResponseEntity<DeleteRepoResponse> deleteFromDB(@PathVariable Long id) {
        modifyingService.deleteById(id);
        DeleteRepoResponse deleteRepoResponse = new DeleteRepoResponse("Repo by id= " + id + " deleted successfully", HttpStatus.OK);
        return ResponseEntity.ok(deleteRepoResponse);
    }

    @PatchMapping("/db/{id}")
    public ResponseEntity<PatchRepoResponse> updatePartiallyRepo(@PathVariable Long id,
                                                                 @RequestBody PatchRepoRequest request) {
        RepoDto oldRepo = request.repo();
        RepoDto newRepo = modifyingService.updatePartiallyById(id, oldRepo);
        return ResponseEntity.ok(new PatchRepoResponse(newRepo));
    }

}