package com.susee.zap.data.repo

import com.susee.zap.data.model.LandingData
import com.susee.zap.utils.DataState
import kotlinx.coroutines.flow.Flow

interface MainRepo {
    suspend fun fetchLandingData(): Flow<DataState<List<LandingData>>>
}