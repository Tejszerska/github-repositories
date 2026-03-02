package com.spring.zaddom0202.app;

import com.spring.zaddom0202.client.GithubClient;
import com.spring.zaddom0202.dto.in.BranchFromGH;
import com.spring.zaddom0202.dto.in.RepoFromGH;
import com.spring.zaddom0202.dto.out.BranchOut;
import com.spring.zaddom0202.dto.out.RepoBranches;
import com.spring.zaddom0202.dto.out.SuccessfulResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final GithubClient githubClient;

    SuccessfulResponse generateResponse(String username) {
        List<RepoFromGH> repos = githubClient.getRepos(username);

        List<RepoBranches> repoBranchesList = new ArrayList<>();

        for (RepoFromGH rgh : repos) {
            if (rgh.fork()) continue;

            String repoName = rgh.name();
            List<BranchFromGH> branchesFromGH = githubClient.getBranches(username, repoName);
            List<BranchOut> branchesForResponse = new ArrayList<>();
            for (BranchFromGH bgh : branchesFromGH) {
                branchesForResponse.add(new BranchOut(bgh.name(), bgh.commit().sha()));

            }
            RepoBranches repoBranches = new RepoBranches(repoName, branchesForResponse);
            repoBranchesList.add(repoBranches);
        }
        return new SuccessfulResponse(username, repoBranchesList);
    }
}
