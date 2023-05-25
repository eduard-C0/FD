package com.example.musicstreaming.services.dtos

data class Album(
    val type: String,
    val id: String,
    val name: String,
    val trackCount: Int,
    val artistName: String
)
