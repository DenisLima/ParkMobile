package com.djv.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Music(

    val artistName: String,

    val trackName: String,

    val pictureUrl: String,

    val collectionName: String?,

    val collectionPrice: String?,

    val trackPrice: String?,

    val currency: String
): Parcelable
