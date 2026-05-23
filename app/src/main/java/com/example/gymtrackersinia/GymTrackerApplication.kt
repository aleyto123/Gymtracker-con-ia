package com.example.gymtrackersinia

import android.app.Application
import com.example.gymtrackersinia.data.local.AppDatabase
import com.example.gymtrackersinia.data.remote.RetrofitClient
import com.example.gymtrackersinia.data.repository.GymRepository

class GymTrackerApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy {
        GymRepository(database.usuarioDao(), database.rutinaDao(), RetrofitClient.exerciseApiService)
    }
}
