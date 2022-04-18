package tasks

import contributors.MockGithubService
import contributors.expectedConcurrentResults
import contributors.testRequestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.currentTime
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.DefaultAsserter.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class Request5ConcurrentKtTest {
  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun testConcurrent() = runTest {
    val startTime = currentTime
    val result = loadContributorsConcurrent(MockGithubService, testRequestData)
    assertEquals(
        "Wrong result for 'loadContributorsConcurrent'", expectedConcurrentResults.users, result)

    val totalTime = currentTime - startTime
    val timeFromStart = expectedConcurrentResults.timeFromStart

    assertEquals(
        "The calls run concurrently, so the total virtual time should be 2200 ms: " +
            "1000 ms for repos request plus max(1000, 1200, 800) = 1200 ms for concurrent contributors requests)",
        expectedConcurrentResults.timeFromStart,
        totalTime)

    assertTrue(
        "The calls run concurrently, so the total virtual time should be 2200 ms: " +
            "1000 ms for repos request plus max(1000, 1200, 800) = 1200 ms for concurrent contributors requests)",
        totalTime in timeFromStart..(timeFromStart + 500),
    )
  }
}
