package com.djv.data.remotesource

import com.djv.data.model.SearchResultData
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicRemoteSource {

    @GET("search?media=music")
    suspend fun getMusics(@Query("term") keySearch: String): SearchResultData
}