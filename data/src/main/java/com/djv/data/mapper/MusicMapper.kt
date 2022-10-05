package com.djv.data.mapper

import com.djv.data.model.MusicData
import com.djv.domain.model.Music

fun MusicData.transform(): Music =
    Music(
        artistName = this.artistName,
        trackName = this.trackName,
        pictureUrl = this.pictureUrl,
        collectionName = this.collectionName,
        collectionPrice = this.collectionPrice,
        trackPrice = this.trackPrice,
        currency = this.currency
    )