package com.example.fd.remotedata

import androidx.annotation.WorkerThread
import com.example.musicstreaming.services.dtos.NapsterResponse
import com.example.musicstreaming.services.dtos.TopArtist
import com.example.musicstreaming.services.dtos.TopTrack
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class NapsterService @Inject constructor(private val retrofitNapsterService: RetrofitNapsterService){

    @WorkerThread
    fun searchTracks(trackName: String): NapsterResponse? = runBlocking {
        retrofitNapsterService.searchTracks(trackName)
    }

    @WorkerThread
    fun getTopArtists(): TopArtist? = runBlocking {
        retrofitNapsterService.getTopArtists()
    }

    @WorkerThread
    fun getTopTracks(artistId: String): TopTrack? = runBlocking {
        retrofitNapsterService.getTopTracks(artistId)
    }
}