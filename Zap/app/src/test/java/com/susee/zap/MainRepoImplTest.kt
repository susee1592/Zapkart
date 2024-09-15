package com.susee.zap

import com.susee.zap.data.model.LandingData
import com.susee.zap.data.repo.MainRepoImpl
import com.susee.zap.data.source.ApiService
import com.susee.zap.utils.DataState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlinx.coroutines.test.runTest

class MainRepoImplTest {

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var mainRepo: MainRepoImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mainRepo = MainRepoImpl(mockApiService)
    }

    @Test
    fun `fetchLandingData emits Loading, then Success`() = runTest {
        // Prepare test data
        val landingData = listOf(LandingData(/* initialize with test data */))
        `when`(mockApiService.getLandingData()).thenReturn(landingData)

        // Collect the flow from the function
        val result = mainRepo.fetchLandingData().toList()

        // Check the result
        assertEquals(DataState.Loading, result[0])
        assertEquals(DataState.Success(landingData), result[1])
    }

    @Test
    fun `fetchLandingData emits Loading, then Error`() = runTest {
        // Prepare exception
        val exception = Exception("Network error")
        `when`(mockApiService.getLandingData()).thenThrow(exception)

        // Collect the flow from the function
        val result = mainRepo.fetchLandingData().toList()

        // Check the result
        assertEquals(DataState.Loading, result[0])
        assertEquals(DataState.Error(exception), result[1])
    }
}