package tasks

import contributors.MockGithubService
import contributors.progressResults
import contributors.testRequestData
import kotlin.test.DefaultAsserter.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class Request6ProgressKtTest {
  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun testProgress() = runTest {
    val startTime = currentTime
    var index = 0
    loadContributorsProgress(MockGithubService, testRequestData) { users, _ ->
      val expected = progressResults[index++]
      val time = currentTime - startTime
      assertEquals(
          "Expected intermediate result after virtual ${expected.timeFromStart} ms:",
          expected.timeFromStart,
          time,
      )
      assertEquals("Wrong intermediate result after $time:", expected.users, users)
    }
  }
}
