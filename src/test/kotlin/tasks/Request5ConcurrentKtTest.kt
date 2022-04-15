package tasks

import contributors.MockGithubService
import contributors.expectedConcurrentResults
import contributors.testRequestData
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.DefaultAsserter.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class Request5ConcurrentKtTest {
  @Test
  fun testConcurrent() = runBlocking {
    val startTime = System.currentTimeMillis()
    val result = loadContributorsConcurrent(MockGithubService, testRequestData)
    assertEquals(
        "Wrong result for 'loadContributorsConcurrent'", expectedConcurrentResults.users, result)
    val totalTime = System.currentTimeMillis() - startTime
    /*
    // TODO: uncomment this assertion
    Assert.assertEquals(
        "The calls run concurrently, so the total virtual time should be 2200 ms: " +
                "1000 ms for repos request plus max(1000, 1200, 800) = 1200 ms for concurrent contributors requests)",
        expectedConcurrentResults.timeFromStart, totalTime
    )
    */
    assertTrue(
        "The calls run concurrently, so the total virtual time should be 2200 ms: " +
            "1000 ms for repos request plus max(1000, 1200, 800) = 1200 ms for concurrent contributors requests)",
        totalTime in
            expectedConcurrentResults.timeFromStart..(expectedConcurrentResults.timeFromStart +
                    500))
  }
}
