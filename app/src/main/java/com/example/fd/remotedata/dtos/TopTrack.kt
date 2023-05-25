package com.example.musicstreaming.services.dtos

import com.google.gson.annotations.SerializedName

data class TopTrack(
    @SerializedName("tracks")
    val tracks: List<Track>?
)
