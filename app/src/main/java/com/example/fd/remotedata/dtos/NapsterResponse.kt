package com.example.musicstreaming.services.dtos

import com.google.gson.annotations.SerializedName

data class NapsterResponse(

    @SerializedName("meta")
    val meta: Meta,

    @SerializedName("search")
    val searchTrackResult: SearchTrackResult
)
