package tasks

import contributors.GitHubService
import contributors.RequestData
import contributors.User
import contributors.log
import contributors.logRepos
import contributors.logUsers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

suspend fun loadContributorsConcurrent(service: GitHubService, req: RequestData): List<User> =
    coroutineScope {
      val repos = service.getOrgRepos(req.org).also { logRepos(req, it) }.bodyList()
      val contributors =
          repos.map { repo ->
            async {
              log("starting loading for ${repo.name}").also { delay(3000) }
              service.getRepoContributors(req.org, repo.name).also { logUsers(repo, it) }.bodyList()
            }
          }
      contributors.awaitAll().flatten().aggregate()
    }
