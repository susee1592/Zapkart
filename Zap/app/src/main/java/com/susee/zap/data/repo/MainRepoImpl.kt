package com.susee.zap.data.repo

import com.susee.zap.data.model.LandingData
import com.susee.zap.data.source.ApiService
import com.susee.zap.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepoImpl @Inject constructor(
    private val apiService: ApiService
) : MainRepo {
    override suspend fun fetchLandingData(): Flow<DataState<List<LandingData>>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.getLandingData()
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}