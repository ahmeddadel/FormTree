package com.lumiform.domain

import com.lumiform.domain.model.ContentItemModel
import com.lumiform.domain.repository.IFormTreeRepository
import com.lumiform.domain.usecase.GetContentUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

/**
 * @created 19/07/2025 - 3:33 PM
 * @project FormTree
 * @author adell
 */
@OptIn(ExperimentalCoroutinesApi::class)
class GetContentUseCaseTest {

    private val repository: IFormTreeRepository = mock()
    private lateinit var getContentUseCase: GetContentUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getContentUseCase = GetContentUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke emits Loading and Success when repository returns data`() = runTest {
        // Given
        val expectedList = listOf(ContentItemModel.TextQuestion("Hello"))
        whenever(repository.getContent()).thenReturn(expectedList)

        // When
        val result = getContentUseCase().toList()

        // Then
        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals(expectedList, result[1].data)
    }

    @Test
    fun `invoke emits Loading and Error when repository throws exception`() = runTest {
        // Given
        val errorMessage = "Network Error"
        whenever(repository.getContent()).thenThrow(RuntimeException(errorMessage))

        // When
        val result = getContentUseCase().toList()

        // Then
        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)
        assertEquals(errorMessage, result[1].message)
    }
}