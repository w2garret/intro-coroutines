package tasks

import contributors.GitHubService
import contributors.RequestData
import contributors.User
import contributors.logRepos
import contributors.logUsers

suspend fun loadContributorsProgress(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, completed: Boolean) -> Unit
) {
  val repos = service.getOrgRepos(req.org).also { logRepos(req, it) }.bodyList()
  var contributors = listOf<User>()
  for ((index, repo) in repos.withIndex()) {
    val users =
        service.getRepoContributors(req.org, repo.name).also { logUsers(repo, it) }.bodyList()
    contributors = (contributors + users).aggregate()
    updateResults(contributors, index == repos.lastIndex)
  }
}
