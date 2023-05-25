package com.example.musicstreaming.services.dtos

data class Track(
    val type: String,
    val id: String,
    val playbackSeconds: String,
    val name: String,
    val artistName: String,
    val artistId: String,
    val albumName: String,
    val albumId: String,
    val previewURL: String,
    val isStreamable: Boolean
)
