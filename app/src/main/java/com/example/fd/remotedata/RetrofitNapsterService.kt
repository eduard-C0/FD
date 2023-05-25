package com.example.fd.remotedata


import com.example.musicstreaming.services.dtos.NapsterResponse
import com.example.musicstreaming.services.dtos.TopArtist
import com.example.musicstreaming.services.dtos.TopTrack

interface RetrofitNapsterService {
    suspend fun searchTracks(trackName: String): NapsterResponse?
    suspend fun getTopArtists(): TopArtist?
    suspend fun getTopTracks(artistId: String): TopTrack?
}