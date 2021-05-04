package com.example.sampleapplication.Network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClientInstance {

    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val CONNECTION_TIMEOUT_MS = 30000

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build()
        }
        return retrofit
    }

    private fun getOkHttpClient(): OkHttpClient {
        val okClientBuilder = OkHttpClient.Builder()
        okClientBuilder.connectTimeout(
            CONNECTION_TIMEOUT_MS.toLong(),
            TimeUnit.MILLISECONDS
        )
        okClientBuilder.readTimeout(
            CONNECTION_TIMEOUT_MS.toLong(),
            TimeUnit.MILLISECONDS
        )
        okClientBuilder.writeTimeout(
            CONNECTION_TIMEOUT_MS.toLong(),
            TimeUnit.MILLISECONDS
        )
        return okClientBuilder.build()
    }
}