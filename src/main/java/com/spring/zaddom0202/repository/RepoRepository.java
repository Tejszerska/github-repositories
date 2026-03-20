package com.spring.zaddom0202.repository;
import com.spring.zaddom0202.model.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RepoRepository extends JpaRepository<Repo, Long> {

    boolean existsByOwnerAndName(String owner, String name);

    boolean existsByOwner(String owner);

    List<Repo> findReposByOwner(String owner);

}
