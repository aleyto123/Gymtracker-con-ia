package com.example.gymtrackersinia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gymtrackersinia.ui.viewmodel.GymViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioScreen(usuarioId: Long, viewModel: GymViewModel, onBack: () -> Unit, onLogout: () -> Unit) {
    val usuario by viewModel.usuarioActual.collectAsState()
    val totalRutinas by viewModel.contarRutinas(usuarioId).collectAsState(initial = 0)
    val volumenTotal by viewModel.volumenTotal(usuarioId).collectAsState(initial = 0.0)

    LaunchedEffect(usuarioId) {
        viewModel.cargarUsuario(usuarioId)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfil") }, navigationIcon = { TextButton(onBack) { Text("Volver") } }) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Usuario", style = MaterialTheme.typography.titleLarge)
            Text("Nombre: ${usuario?.nombre.orEmpty()}")
            Text("Email: ${usuario?.email.orEmpty()}")
            Text("Rutinas registradas: $totalRutinas")
            Text("Volumen total: ${volumenTotal ?: 0.0} kg")
            Button(
                onClick = {
                    viewModel.cerrarSesion()
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}
