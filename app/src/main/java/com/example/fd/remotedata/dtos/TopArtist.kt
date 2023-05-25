package com.example.musicstreaming.services.dtos

import com.google.gson.annotations.SerializedName

data class TopArtist(
    @SerializedName("artists")
    val artists: List<Artist>?
)
