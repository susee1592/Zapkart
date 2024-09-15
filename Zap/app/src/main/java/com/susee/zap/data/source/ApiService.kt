package com.susee.zap.data.source

import com.susee.zap.data.model.LandingData
import retrofit2.http.GET

interface ApiService {
    @GET("b/5BEJ")
    suspend fun getLandingData(): List<LandingData>
}