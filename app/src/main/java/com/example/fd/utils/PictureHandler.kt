package com.example.fd.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.fd.R

object PictureHandler {

    private const val APIKEY = "apikey"
    private const val APIKEY_VALUE = "M2ExNTkyZWUtOWVlOS00NDU0LWJhOTEtYWFmMjI0NGZhNTM5\n"
    private const val URL_BASE = "https://api.napster.com/imageserver/v2/albums/"
    private const val URL_BASE_ARTIST_IMAGE_SERVER =
        "https://api.napster.com/imageserver/v2/artists/"
    private const val IMAGE_SIZE = 170
    private const val IMAGE_SIZE_ARTIST_W = 150
    private const val IMAGE_SIZE_ARTIST_H = 100

    fun loadTrackImage(
        context: Context,
        trackId: String,
        image: ImageView,
        imageSize: Int = IMAGE_SIZE
    ) {
        val urlImageSize = "/images/${imageSize}x${imageSize}.jpg"
        val url = "$URL_BASE$trackId$urlImageSize"

        Glide.with(context).load(
            GlideUrl(
                url, LazyHeaders.Builder()
                    .addHeader(APIKEY, APIKEY_VALUE)
                    .build()
            )
        ).error(R.drawable.album).centerCrop().into(image)
    }

    fun loadArtistImage(
        context: Context,
        artistId: String,
        image: ImageView,
    ) {
        val urlImageSize = "/images/${IMAGE_SIZE_ARTIST_W}x${IMAGE_SIZE_ARTIST_H}.jpg"
        val url = "$URL_BASE_ARTIST_IMAGE_SERVER$artistId$urlImageSize"

        Glide.with(context).load(
            GlideUrl(
                url, LazyHeaders.Builder()
                    .addHeader(APIKEY, APIKEY_VALUE)
                    .build()
            )
        )
            .error(R.drawable.album).circleCrop().into(image)
    }

}