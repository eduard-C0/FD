package com.example.fd.remotedata

import com.example.musicstreaming.services.dtos.NapsterResponse
import com.example.musicstreaming.services.dtos.TopArtist
import com.example.musicstreaming.services.dtos.TopTrack
import retrofit2.http.*

interface RetrofitNapsterServiceApi {

    @Headers(
        "contentType: application/json",
        "apikey: M2ExNTkyZWUtOWVlOS00NDU0LWJhOTEtYWFmMjI0NGZhNTM5"
    )
    @GET("v2.2/search")
    suspend fun searchTracks(@Query("query") trackName: String, @Query("type") type: String = "track"): NapsterResponse


    @Headers(
        "contentType: application/json",
        "apikey: M2ExNTkyZWUtOWVlOS00NDU0LWJhOTEtYWFmMjI0NGZhNTM5"
    )
    @GET("v2.2/artists/top")
    suspend fun getTopArtists(@Query("range") range: String = "week"): TopArtist


    @Headers(
        "contentType: application/json",
        "apikey: M2ExNTkyZWUtOWVlOS00NDU0LWJhOTEtYWFmMjI0NGZhNTM5"
    )
    @GET("v2.2/artists/{artistId}/tracks/top")
    suspend fun getTopTracks(@Path("artistId") artistId: String, @Query("limit") limit: Int = 20): TopTrack

}