package tasks

import contributors.GitHubService
import contributors.RequestData
import contributors.User
import contributors.logRepos
import contributors.logUsers

suspend fun loadContributorsSuspend(service: GitHubService, req: RequestData): List<User> {
  val repos = service.getOrgRepos(req.org).also { logRepos(req, it) }.bodyList()
  val contributors =
      repos.flatMap { repo ->
        service.getRepoContributors(req.org, repo.name).also { logUsers(repo, it) }.bodyList()
      }
  return contributors.aggregate()
}
