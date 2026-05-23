package com.example.gymtrackersinia.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gymtrackersinia.ui.screens.AgregarRutinaScreen
import com.example.gymtrackersinia.ui.screens.DetalleRutinaScreen
import com.example.gymtrackersinia.ui.screens.ListaRutinasScreen
import com.example.gymtrackersinia.ui.screens.LoginScreen
import com.example.gymtrackersinia.ui.screens.MenuPrincipalScreen
import com.example.gymtrackersinia.ui.screens.PerfilUsuarioScreen
import com.example.gymtrackersinia.ui.screens.RegistroScreen
import com.example.gymtrackersinia.ui.viewmodel.GymViewModel

@Composable
fun GymTrackerNavHost(viewModel: GymViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(
                viewModel = viewModel,
                onLogin = { usuarioId ->
                    navController.navigate(Routes.menu(usuarioId)) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onRegistro = { navController.navigate(Routes.REGISTRO) }
            )
        }
        composable(Routes.REGISTRO) {
            RegistroScreen(viewModel = viewModel, onBackToLogin = { navController.popBackStack() })
        }
        composable(Routes.MENU, arguments = listOf(navArgument("usuarioId") { type = NavType.LongType })) {
            val usuarioId = it.arguments?.getLong("usuarioId") ?: 0L
            MenuPrincipalScreen(usuarioId, viewModel, navController)
        }
        composable(Routes.AGREGAR_RUTINA, arguments = listOf(navArgument("usuarioId") { type = NavType.LongType })) {
            val usuarioId = it.arguments?.getLong("usuarioId") ?: 0L
            AgregarRutinaScreen(usuarioId, viewModel) { navController.popBackStack() }
        }
        composable(Routes.LISTA_RUTINAS, arguments = listOf(navArgument("usuarioId") { type = NavType.LongType })) {
            val usuarioId = it.arguments?.getLong("usuarioId") ?: 0L
            ListaRutinasScreen(
                usuarioId = usuarioId,
                viewModel = viewModel,
                onAgregar = { navController.navigate(Routes.agregarRutina(usuarioId)) },
                onEditar = { rutinaId -> navController.navigate(Routes.detalleRutina(usuarioId, rutinaId)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            Routes.DETALLE_RUTINA,
            arguments = listOf(
                navArgument("usuarioId") { type = NavType.LongType },
                navArgument("rutinaId") { type = NavType.LongType }
            )
        ) {
            val usuarioId = it.arguments?.getLong("usuarioId") ?: 0L
            val rutinaId = it.arguments?.getLong("rutinaId") ?: 0L
            DetalleRutinaScreen(usuarioId, rutinaId, viewModel) { navController.popBackStack() }
        }
        composable(Routes.PERFIL, arguments = listOf(navArgument("usuarioId") { type = NavType.LongType })) {
            val usuarioId = it.arguments?.getLong("usuarioId") ?: 0L
            PerfilUsuarioScreen(
                usuarioId = usuarioId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
    }
}
