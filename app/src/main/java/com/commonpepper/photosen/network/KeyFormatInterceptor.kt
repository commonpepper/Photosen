package com.commonpepper.photosen.network

import android.util.Log

import com.commonpepper.photosen.BuildConfig

import java.io.IOException

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class KeyFormatInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.APP_KEY)
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1")
                .build()

        Log.d(TAG, url.toString())

        val requestBuilder = original.newBuilder()
                .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        private val TAG = KeyFormatInterceptor::class.java.simpleName
    }
}