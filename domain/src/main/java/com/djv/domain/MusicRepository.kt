package com.djv.domain

import com.djv.domain.model.SearchMusicResult
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    suspend fun getMusics(searchKey: String): Flow<SearchMusicResult>
}