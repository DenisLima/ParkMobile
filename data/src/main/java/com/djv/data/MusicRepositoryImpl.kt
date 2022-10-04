package com.djv.data

import com.djv.data.mapper.transform
import com.djv.data.remotesource.MusicRemoteSource
import com.djv.domain.MusicRepository
import com.djv.domain.model.SearchMusicResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MusicRepositoryImpl(
    private val musicRemoteSource: MusicRemoteSource
): MusicRepository {

    override suspend fun getMusics(searchKey: String): Flow<SearchMusicResult> = flow {
        val musicList = musicRemoteSource.getMusics(searchKey)
        emit(musicList.transform())
    }
}