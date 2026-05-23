package com.example.gymtrackersinia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFF171A35),
                drawerContentColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .background(AppBackground)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    WorkoutArtwork(variant = 0, modifier = Modifier.padding(top = 8.dp).fillMaxSize(0.34f))
                    Text("GymTracker", fontWeight = FontWeight.Black, color = Color.White)
                    NavigationDrawerItem(
                        label = { Text("Agregar rutina") },
                        selected = false,
                        onClick = { navController.navigate(Routes.agregarRutina(usuarioId)) }
                    )
                    NavigationDrawerItem(
                        label = { Text("Mis rutinas") },
                        selected = false,
                        onClick = { navController.navigate(Routes.listaRutinas(usuarioId)) }
                    )
                    NavigationDrawerItem(
                        label = { Text("Perfil y estadisticas") },
                        selected = false,
                        onClick = { navController.navigate(Routes.perfil(usuarioId)) }
                    )
                }
            }
        }
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("GymTracker Pro", fontWeight = FontWeight.Black) },
                    navigationIcon = {
                        TextButton({ scope.launch { drawerState.open() } }) { Text("Menu") }
                    },
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
                        title = "Listo para entrenar",
                        subtitle = "Organiza tus ejercicios, guarda marcas y vuelve a tus rutinas sin perderte.",
                        artworkVariant = 1,
                        brush = SunriseBrush
                    )
                    SectionTitle("Accesos rapidos")
                    ActionCard(
                        title = "Nueva rutina",
                        subtitle = "Registra ejercicio, series, repeticiones, peso y fecha.",
                        brush = BerryBrush,
                        artworkVariant = 0,
                        onClick = { navController.navigate(Routes.agregarRutina(usuarioId)) }
                    )
                    ActionCard(
                        title = "Mis entrenamientos",
                        subtitle = "Consulta, edita o elimina tus rutinas guardadas.",
                        brush = LimeBrush,
                        artworkVariant = 2,
                        onClick = { navController.navigate(Routes.listaRutinas(usuarioId)) }
                    )
                    ActionCard(
                        title = "Mi progreso",
                        subtitle = "Mira volumen total y cantidad de rutinas completadas.",
                        brush = SunriseBrush,
                        artworkVariant = 1,
                        onClick = { navController.navigate(Routes.perfil(usuarioId)) }
                    )
                }
            }
        }
    }
}
