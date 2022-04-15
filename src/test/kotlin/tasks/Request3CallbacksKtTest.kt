package tasks

import contributors.MockGithubService
import contributors.expectedResults
import contributors.testRequestData
import kotlin.test.DefaultAsserter.assertEquals
import org.junit.jupiter.api.Test

class Request3CallbacksKtTest {
  @Test
  fun testDataIsLoaded() {
    loadContributorsCallbacks(MockGithubService, testRequestData) {
      assertEquals("Wrong result for 'loadContributorsCallbacks'", expectedResults.users, it)
    }
  }
}
