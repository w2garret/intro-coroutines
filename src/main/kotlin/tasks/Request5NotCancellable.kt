package tasks

import contributors.GitHubService
import contributors.RequestData
import contributors.User
import contributors.log
import contributors.logRepos
import contributors.logUsers
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay

@OptIn(DelicateCoroutinesApi::class)
suspend fun loadContributorsNotCancellable(service: GitHubService, req: RequestData): List<User> {
  val repos = service.getOrgRepos(req.org).also { logRepos(req, it) }.bodyList()
  val contributors =
      repos.map { repo ->
        GlobalScope.async {
          log("starting loading for ${repo.name}").also { delay(3000) }
          service.getRepoContributors(req.org, repo.name).also { logUsers(repo, it) }.bodyList()
        }
      }
  return contributors.awaitAll().flatten().aggregate()
}
