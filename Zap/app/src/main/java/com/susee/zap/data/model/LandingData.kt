package com.susee.zap.data.model

import com.google.gson.annotations.SerializedName

data class LandingData(
    @SerializedName("sectionType") var sectionType: String? = null,
    @SerializedName("items") var items: ArrayList<LandingItems> = arrayListOf()
)