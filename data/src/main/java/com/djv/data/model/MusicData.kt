package com.djv.data.model

import com.google.gson.annotations.SerializedName

data class MusicData(

    @SerializedName("artistName")
    val artistName: String,

    @SerializedName("trackName")
    val trackName: String,

    @SerializedName("artworkUrl100")
    val pictureUrl: String
)
