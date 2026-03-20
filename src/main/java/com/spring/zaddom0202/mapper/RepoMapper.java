package com.spring.zaddom0202.mapper;

import com.spring.zaddom0202.dto.RepoDto;
import com.spring.zaddom0202.dto.in.CreateRepoDto;
import com.spring.zaddom0202.model.Repo;
import org.springframework.data.domain.Page;

import java.util.List;

public class RepoMapper {
    public static RepoDto mapFromEntityToDto(com.spring.zaddom0202.model.Repo repo) {
        return new RepoDto(repo.getId(), repo.getOwner(), repo.getName());
    }

    public static com.spring.zaddom0202.model.Repo mapFromDtoToEntity(RepoDto repo) {
        return new com.spring.zaddom0202.model.Repo(repo.id(), repo.owner(), repo.name());
    }

    public static List<RepoDto> mapFromEntityListToDtoList(List<com.spring.zaddom0202.model.Repo> repos) {
        return repos.stream().map(RepoMapper::mapFromEntityToDto).toList();
    }

    public static RepoDto mapFromCreateRepoDtoToRepo(CreateRepoDto createRepoDto) {
        return createRepoDto.repo();
    }

    public static Page<RepoDto> mapFromPageEntityToPageDto(Page<Repo> all) {
        return all.map(RepoMapper::mapFromEntityToDto);
    }
}
