package com.example.musicstreaming.network.interceptor

import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class HeaderInterceptor() : Interceptor {
    companion object {
        private const val AUTHORIZATION = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .addHeader(AUTHORIZATION, "")
            .build()
        return chain.proceed(request)
    }

}