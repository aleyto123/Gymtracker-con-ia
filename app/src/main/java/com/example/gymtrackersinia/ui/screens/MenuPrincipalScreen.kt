package com.example.gymtrackersinia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymtrackersinia.ui.navigation.Routes
import com.example.gymtrackersinia.ui.viewmodel.GymViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipalScreen(usuarioId: Long, viewModel: GymViewModel, navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(usuarioId) {
        viewModel.cargarUsuario(usuarioId)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú", modifier = Modifier.padding(16.dp))
                NavigationDrawerItem({ Text("Agregar rutina") }, false, { navController.navigate(Routes.agregarRutina(usuarioId)) })
                NavigationDrawerItem({ Text("Lista de rutinas") }, false, { navController.navigate(Routes.listaRutinas(usuarioId)) })
                NavigationDrawerItem({ Text("Perfil") }, false, { navController.navigate(Routes.perfil(usuarioId)) })
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("GymTracker Pro") },
                    navigationIcon = {
                        TextButton({ scope.launch { drawerState.open() } }) { Text("Menú") }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button({ navController.navigate(Routes.agregarRutina(usuarioId)) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Agregar rutina")
                }
                Button({ navController.navigate(Routes.listaRutinas(usuarioId)) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Ver rutinas")
                }
                Button({ navController.navigate(Routes.perfil(usuarioId)) }, modifier = Modifier.fillMaxWidth()) {
                    Text("Perfil")
                }
            }
        }
    }
}
