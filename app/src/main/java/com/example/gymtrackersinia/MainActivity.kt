package com.example.gymtrackersinia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymtrackersinia.ui.navigation.GymTrackerNavHost
import com.example.gymtrackersinia.ui.viewmodel.GymViewModel
import com.example.gymtrackersinia.ui.viewmodel.GymViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = (application as GymTrackerApplication).repository
        setContent {
            val viewModel: GymViewModel = viewModel(factory = GymViewModelFactory(repository))
            GymTrackerNavHost(viewModel)
        }
    }
}
