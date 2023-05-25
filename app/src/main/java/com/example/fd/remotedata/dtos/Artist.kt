package com.example.musicstreaming.services.dtos

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Artist(
    val type: String,
    val id: String,
    val href: String,
    val name: String,
    var topTracks: List<Track>?,
)
