package com.example.sampleapplication.Network

import com.example.sampleapplication.Model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.LinkedHashMap

interface GetWeatherData {

    @GET("weather?")
    fun getWeatherData(@QueryMap params : LinkedHashMap<String, String>): Call<WeatherModel?>?
}