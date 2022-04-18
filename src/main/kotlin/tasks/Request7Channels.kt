package tasks

import contributors.GitHubService
import contributors.RequestData
import contributors.User
import contributors.log
import contributors.logRepos
import contributors.logUsers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

suspend fun loadContributorsChannels(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, completed: Boolean) -> Unit
) {
  coroutineScope {
    val channel = Channel<List<User>>()
    val repos = service.getOrgRepos(req.org).also { logRepos(req, it) }.bodyList()

    for (repo in repos) {
      launch {
        log("starting loading for ${repo.name}").also { delay(3000) }
        val users =
            service.getRepoContributors(req.org, repo.name).also { logUsers(repo, it) }.bodyList()
        channel.send(users)
      }
    }

    var contributors = listOf<User>()
    repeat(repos.size) {
      val users = channel.receive()
      contributors = (contributors + users).aggregate()
      updateResults(contributors, it == repos.lastIndex)
    }
  }
}
