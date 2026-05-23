package com.example.gymtrackersinia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Perfil", fontWeight = FontWeight.Black) },
                navigationIcon = { TextButton(onBack) { Text("Volver") } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF101227),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color(0xFFFFD166)
                )
            )
        }
    ) { padding ->
        GymBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeroPanel(
                    title = usuario?.nombre?.ifBlank { "Atleta" } ?: "Atleta",
                    subtitle = usuario?.email.orEmpty().ifBlank { "Tu progreso vive aqui." },
                    artworkVariant = 2,
                    brush = BerryBrush
                )
                SectionTitle("Resumen")
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatTile(
                        label = "Rutinas",
                        value = totalRutinas.toString(),
                        brush = SunriseBrush,
                        modifier = Modifier.weight(1f)
                    )
                    StatTile(
                        label = "Volumen kg",
                        value = String.format("%.1f", volumenTotal ?: 0.0),
                        brush = LimeBrush,
                        modifier = Modifier.weight(1f)
                    )
                }
                GlassPanel {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Datos del usuario", color = Color(0xFF101227), fontWeight = FontWeight.Black)
                        Text("Nombre: ${usuario?.nombre.orEmpty()}", color = Color(0xFF293052))
                        Text("Email: ${usuario?.email.orEmpty()}", color = Color(0xFF293052))
                    }
                }
                PrimaryAction(
                    text = "Cerrar sesion",
                    onClick = {
                        viewModel.cerrarSesion()
                        onLogout()
                    }
                )
            }
        }
    }
}
