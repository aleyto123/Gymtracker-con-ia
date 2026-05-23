package com.example.gymtrackersinia.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseApiService {
    @GET("v1/exercises")
    suspend fun listarEjercicios(@Query("muscle") muscle: String? = null): List<ExerciseDto>
}
