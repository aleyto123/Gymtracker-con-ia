package com.example.gymtrackersinia.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.api-ninjas.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val exerciseApiService: ExerciseApiService by lazy {
        retrofit.create(ExerciseApiService::class.java)
    }
}
