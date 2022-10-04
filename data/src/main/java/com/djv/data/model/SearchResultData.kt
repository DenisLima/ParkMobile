package com.djv.data.model

import com.google.gson.annotations.SerializedName

data class SearchResultData(

    @SerializedName("resultCount")
    val resultCount: Int,

    @SerializedName("results")
    val results: List<MusicData>
)
