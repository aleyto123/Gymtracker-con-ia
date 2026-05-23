package com.example.gymtrackersinia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.gymtrackersinia.ui.viewmodel.GymViewModel

@Composable
fun LoginScreen(viewModel: GymViewModel, onLogin: (Long) -> Unit, onRegistro: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val mensaje by viewModel.mensaje.collectAsState()

    LaunchedEffect(mensaje) {
        mensaje?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.limpiarMensaje()
        }
    }

    Scaffold(containerColor = Color.Transparent, snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
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
                    title = "GymTracker Pro",
                    subtitle = "Entrena, registra y mira tu progreso con una app hecha para dar ganas de moverse.",
                    artworkVariant = 0,
                    brush = BerryBrush
                )
                GlassPanel {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Bienvenido de nuevo", color = Color(0xFF101227), fontWeight = FontWeight.Black)
                        BrightTextField(email, { email = it }, "Email")
                        BrightTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = "Contrasena",
                            visualTransformation = PasswordVisualTransformation()
                        )
                        PrimaryAction("Iniciar sesion", { viewModel.login(email, password, onLogin) })
                    }
                }
                SoftTextButton("Crear una cuenta nueva", onRegistro)
            }
        }
    }
}

@Composable
fun RegistroScreen(viewModel: GymViewModel, onBackToLogin: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val mensaje by viewModel.mensaje.collectAsState()

    LaunchedEffect(mensaje) {
        mensaje?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.limpiarMensaje()
        }
    }

    Scaffold(containerColor = Color.Transparent, snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
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
                    title = "Crea tu energia",
                    subtitle = "Tu rutina empieza con un perfil simple y listo para guardar todo offline.",
                    artworkVariant = 1,
                    brush = SunriseBrush
                )
                GlassPanel {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Nuevo usuario", color = Color(0xFF101227), fontWeight = FontWeight.Black)
                        BrightTextField(nombre, { nombre = it }, "Nombre")
                        BrightTextField(email, { email = it }, "Email")
                        BrightTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = "Contrasena",
                            visualTransformation = PasswordVisualTransformation()
                        )
                        PrimaryAction("Guardar cuenta", { viewModel.registrar(nombre, email, password, onBackToLogin) })
                    }
                }
                SoftTextButton("Volver al inicio de sesion", onBackToLogin)
            }
        }
    }
}
