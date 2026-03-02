package com.spring.zaddom0202.client;

import com.spring.zaddom0202.dto.in.BranchFromGH;
import com.spring.zaddom0202.dto.in.RepoFromGH;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value="github", configuration = FeignConfig.class)
public interface GithubClient {
    @RequestMapping(method = RequestMethod.GET, value="/users/{userName}/repos")
    List<RepoFromGH> getRepos(@PathVariable("userName") String userName);

    @RequestMapping(method = RequestMethod.GET, value="/repos/{userName}/{repoName}/branches")
    List<BranchFromGH> getBranches(@PathVariable("userName") String userName, @PathVariable("repoName") String repoName);

}
