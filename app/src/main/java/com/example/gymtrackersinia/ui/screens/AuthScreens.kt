package com.example.gymtrackersinia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("GymTracker Pro", style = MaterialTheme.typography.headlineSmall)
            OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Button({ viewModel.login(email, password, onLogin) }, modifier = Modifier.fillMaxWidth()) {
                Text("Iniciar sesión")
            }
            TextButton(onRegistro, modifier = Modifier.fillMaxWidth()) {
                Text("Registrarse")
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

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Registro", style = MaterialTheme.typography.headlineSmall)
            OutlinedTextField(nombre, { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Button({ viewModel.registrar(nombre, email, password, onBackToLogin) }, modifier = Modifier.fillMaxWidth()) {
                Text("Guardar")
            }
            TextButton(onBackToLogin, modifier = Modifier.fillMaxWidth()) {
                Text("Volver")
            }
        }
    }
}
