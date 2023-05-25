package com.example.fd.remotedata

import android.util.Log
import com.example.musicstreaming.services.dtos.NapsterResponse
import com.example.musicstreaming.services.dtos.TopArtist
import com.example.musicstreaming.services.dtos.TopTrack
import java.lang.Exception

internal class RetrofitNapsterServiceImplementation(private val napsterServiceApi: RetrofitNapsterServiceApi) :
    RetrofitNapsterService {

    companion object {
        private const val TAG = "RetrofitNapsterServiceImplementation"
    }

    override suspend fun searchTracks(trackName: String): NapsterResponse? {
        try {
            val response = napsterServiceApi.searchTracks(trackName)
            Log.d(TAG, "searchTracks Response: $response")
            if (response?.meta?.returnedCount != 0) {
                Log.d(TAG, "searchTracks 200")
                return response
            }
            Log.d(TAG, "searchTracks 400 ERROR")
            return null
        } catch (exception: Exception) {
            Log.e(TAG, exception.stackTraceToString())
            return null
        }

    }

    override suspend fun getTopArtists(): TopArtist? {
        try {
            val response = napsterServiceApi.getTopArtists()
            Log.d(TAG, "getTopArtists Response: $response")
            if (response.artists != null) {
                Log.d(TAG, "getTopArtists 200")
                return response
            }
            Log.d(TAG, "getTopArtists 400 ERROR")
            return null
        } catch (exception: Exception) {
            Log.e(TAG, exception.stackTraceToString())
            return null
        }
    }

    override suspend fun getTopTracks(artistId: String): TopTrack? {
        try {
            val response = napsterServiceApi.getTopTracks(artistId)
            Log.d(TAG, "getTopTracks Response: $response")
            if (response.tracks != null) {
                Log.d(TAG, "getTopTracks 200")
                return response
            }
            Log.d(TAG, "getTopTracks 400 ERROR")
            return null
        } catch (exception: Exception) {
            Log.e(TAG, exception.stackTraceToString())
            return null
        }
    }
}