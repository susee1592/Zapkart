package com.susee.zap.data.model

import com.google.gson.annotations.SerializedName

data class LandingItems(
    @SerializedName("title") var title: String? = null,
    @SerializedName("image") var image: String? = null
)