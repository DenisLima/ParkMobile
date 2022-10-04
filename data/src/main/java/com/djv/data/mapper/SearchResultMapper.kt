package com.djv.data.mapper

import com.djv.data.model.SearchResultData
import com.djv.domain.model.SearchMusicResult

fun SearchResultData.transform(): SearchMusicResult =
    SearchMusicResult(
        resultCount = this.resultCount,
        results = this.results.map {
            it.transform()
        }
    )