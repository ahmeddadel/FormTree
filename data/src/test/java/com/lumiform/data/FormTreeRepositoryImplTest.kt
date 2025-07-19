package com.lumiform.data

import com.google.gson.Gson
import com.lumiform.data.local.db.dao.CachedJsonDao
import com.lumiform.data.local.db.entity.CachedJsonEntity
import com.lumiform.data.remote.api.IApiService
import com.lumiform.data.remote.dto.ContentItemDto
import com.lumiform.data.repository.FormTreeRepositoryImpl
import com.lumiform.domain.model.ContentItemModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

/**
 * @created 19/07/2025 - 3:11 PM
 * @project FormTree
 * @author adell
 */
@ExperimentalCoroutinesApi
class FormTreeRepositoryImplTest {

    private lateinit var repository: FormTreeRepositoryImpl

    private val apiService: IApiService = mock()
    private val dao: CachedJsonDao = mock()
    private val gson = Gson()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = FormTreeRepositoryImpl(apiService, dao, gson)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getContent should fetch from remote and save to cache`() = runTest {
        // Given
        val dto = ContentItemDto(type = "text", title = "Test Question")
        whenever(apiService.fetchContent()).thenReturn(dto)
        whenever(dao.getAllCached()).thenReturn(emptyList())

        // When
        val result = repository.getContent()

        // Then
        assertEquals(1, result.size)
        assertTrue(result[0] is ContentItemModel.TextQuestion)
        verify(dao).insertCache(any())
    }

    @Test
    fun `getContent should return from cache when network fails`() = runTest {
        // Given
        val dto = ContentItemDto(type = "text", title = "Cached Question")
        val cachedJson = gson.toJson(dto)

        whenever(apiService.fetchContent()).thenThrow(RuntimeException("Network error"))
        whenever(dao.getLastCached()).thenReturn(CachedJsonEntity(json = cachedJson))

        // When
        val result = repository.getContent()

        // Then
        assertEquals(1, result.size)
        assertEquals("Cached Question", (result[0] as ContentItemModel.TextQuestion).title)
    }

    @Test(expected = IOException::class)
    fun `getContent should throw when both remote and cache fail`() = runTest {
        // Given
        whenever(apiService.fetchContent()).thenThrow(RuntimeException("Network error"))
        whenever(dao.getLastCached()).thenReturn(null)

        // When
        repository.getContent()

        // Then
        // Expect IOException
    }
}
