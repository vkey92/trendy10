package com.example.trendy10

import com.example.trendy10.repos.TrendyRepo
import com.example.trendy10.api.TrendyApi
import com.example.trendy10.models.TweetListItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class TrendyRepoTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repo: TrendyRepo
    private lateinit var api: TrendyApi

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        api = mockk()
        repo = TrendyRepo(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadAllCategories emits categories, news and quotes`() = runTest {
        // Given
        coEvery { api.getCategories() } returns Response.success(listOf("cat1", "cat2"))
        coEvery { api.getNewsCategories() } returns Response.success(listOf("news1"))
        coEvery { api.getQuatoCategories() } returns Response.success(listOf("quote1"))

        // When
        repo.loadAllCategories()

        // Advance coroutine execution
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(listOf("cat1", "cat2"), repo.categories.value)
        assertEquals(listOf("news1"), repo.newsCategories.value)
        assertEquals(listOf("quote1"), repo.quatoCategories.value)
    }

    @Test
    fun `getTweets emits tweets for given category`() = runTest {
        // Given
        val tweets = listOf(TweetListItem(category = "hello", value = "Hello World"))
        coEvery { api.getTweets(any()) } returns Response.success(tweets)

        // When
        repo.getTweets("sports", "tweets")

        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(tweets, repo.tweets.value)
    }

    @Test
    fun `loadAllCategories does not emit when apis fail`() = runTest {
        // Given (failed responses)
        coEvery { api.getCategories() } returns Response.error(404, okhttp3.ResponseBody.create(null, ""))
        coEvery { api.getNewsCategories() } returns Response.error(500, okhttp3.ResponseBody.create(null, ""))
        coEvery { api.getQuatoCategories() } returns Response.error(400, okhttp3.ResponseBody.create(null, ""))

        // When
        repo.loadAllCategories()

        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(emptyList<String>(), repo.categories.value)
        assertEquals(emptyList<String>(), repo.newsCategories.value)
        assertEquals(emptyList<String>(), repo.quatoCategories.value)
    }
}
