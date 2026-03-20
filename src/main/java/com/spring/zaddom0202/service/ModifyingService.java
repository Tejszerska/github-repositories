package com.spring.zaddom0202.service;

import com.spring.zaddom0202.mapper.RepoMapper;
import com.spring.zaddom0202.model.Repo;
import com.spring.zaddom0202.repository.RepoRepository;
import com.spring.zaddom0202.client.GithubClient;
import com.spring.zaddom0202.dto.RepoDto;
import com.spring.zaddom0202.dto.in.RepoFromGH;
import com.spring.zaddom0202.exception.DuplicateRepoException;
import com.spring.zaddom0202.exception.RepoNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ModifyingService {
    private final RepoRepository repoRepository;
    private final ReadingService readingService;
    private final GithubClient githubClient;

    public List<RepoDto> saveFromGitHub(String username) {
        List<RepoFromGH> repos = githubClient.getRepos(username);
//        MNIEJ ZAPYTAŃ DO BAZY
        List<String> repoNamesFromDb = repoRepository.findReposByOwner(username).stream()
                .map(Repo::getName)
                .toList();

        List<Repo> reposToSave = repos.stream()
                .filter(r -> !r.fork())
                .filter(r -> repoNamesFromDb.contains(r.name()))
                .map(r -> new Repo(username, r.name()))
                .toList();

        repoRepository.saveAll(reposToSave);
        return RepoMapper.mapFromEntityListToDtoList(reposToSave);

//        MOJA WERSJA
//        List<Repo> reposForDb = repos.stream()
//                .filter(r -> !r.fork())
//                .filter(r -> !readingService.isDuplicate(username, r.name()))
//                .map(r -> new Repo(username, r.name()))
//                .toList();
//        int size = reposForDb.size();
//        log.info("{} records from GitHub are prepared to database", size);
//        repoRepository.saveAll(reposForDb);
//        return RepoMapper.mapFromEntityListToDtoList(reposForDb);
    }

    public void deleteById(Long id) {
        readingService.existsById(id);
        repoRepository.deleteById(id);
    }

    public RepoDto addRepo(RepoDto repoDto) {
        if (readingService.isDuplicate(repoDto.owner(), repoDto.name()))
            throw new DuplicateRepoException("No duplicates are allowed");
        Repo repo = RepoMapper.mapFromDtoToEntity(repoDto);
        Repo repoSaved = repoRepository.save(repo);
        return RepoMapper.mapFromEntityToDto(repoSaved);
    }

    public RepoDto updatePartiallyById(Long id, RepoDto repoFromUser) {
        Optional<Repo> optionalRepoById = repoRepository.findById(id);
        if (optionalRepoById.isPresent()) {
            Repo repoFromDb = optionalRepoById.get();
            if(repoFromUser.owner() != null) repoFromDb.setOwner(repoFromUser.owner());
            if(repoFromUser.name() != null) repoFromDb.setName(repoFromUser.name());
            return RepoMapper.mapFromEntityToDto(repoFromDb);
        } else {
            throw new RepoNotFoundException("No Repo by the id: " + id);
        }
    }
}
