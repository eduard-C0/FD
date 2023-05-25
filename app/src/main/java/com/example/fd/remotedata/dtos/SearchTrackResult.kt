package com.example.musicstreaming.services.dtos

import com.google.gson.annotations.SerializedName

data class SearchTrackResult(

    @SerializedName("query")
    val query: String? = null,

    @SerializedName("data")
    val data: TrackResult? = null,

    @SerializedName("order")
    val order: List<String>? = null
)
