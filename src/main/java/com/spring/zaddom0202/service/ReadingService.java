package com.spring.zaddom0202.service;

import com.spring.zaddom0202.mapper.RepoMapper;
import com.spring.zaddom0202.model.Repo;
import com.spring.zaddom0202.repository.RepoRepository;
import com.spring.zaddom0202.dto.RepoDto;
import com.spring.zaddom0202.exception.RepoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadingService {
    private final RepoRepository repoRepository;

    public boolean isDuplicate(String owner, String name){
        return repoRepository.existsByOwnerAndName(owner, name);
    }

    public void existsById(Long id){
        if(!repoRepository.existsById(id)){
            throw new RepoNotFoundException("Repo by id= " + id + "wasn't found");
        }
    }

    public void existsByOwner(String owner){
        if(!repoRepository.existsByOwner(owner)){
            throw new RepoNotFoundException("Repo by owner= \"" + owner + "\" wasn't found");
        }
    }

    public Page<RepoDto> getAllRepos(@PageableDefault(size = 15) Pageable pageable) {
        Page<Repo> all = repoRepository.findAll(pageable);
        return RepoMapper.mapFromPageEntityToPageDto(all);
    }

    public List<RepoDto> getByUsername(String owner) {
        existsByOwner(owner);
        List<Repo> reposByOwner = repoRepository.findReposByOwner(owner);
        return RepoMapper.mapFromEntityListToDtoList(reposByOwner);
    }
}
