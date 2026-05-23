package com.example.gymtrackersinia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gymtrackersinia.data.local.Rutina
import com.example.gymtrackersinia.ui.viewmodel.GymViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarRutinaScreen(usuarioId: Long, viewModel: GymViewModel, onBack: () -> Unit) {
    var ejercicio by remember { mutableStateOf("") }
    var series by remember { mutableStateOf("") }
    var repeticiones by remember { mutableStateOf("") }
    var pesoKg by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val mensaje by viewModel.mensaje.collectAsState()

    LaunchedEffect(mensaje) {
        mensaje?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.limpiarMensaje()
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            GymTopBar("Nueva rutina", onBack)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
                    title = "Construye tu sesion",
                    subtitle = "Dale forma a tu entrenamiento y guardalo para seguir mejorando.",
                    artworkVariant = 0,
                    brush = BerryBrush
                )
                RutinaForm(
                    ejercicio = ejercicio,
                    onEjercicioChange = { ejercicio = it },
                    series = series,
                    onSeriesChange = { series = it },
                    repeticiones = repeticiones,
                    onRepeticionesChange = { repeticiones = it },
                    pesoKg = pesoKg,
                    onPesoKgChange = { pesoKg = it },
                    fecha = fecha,
                    onFechaChange = { fecha = it },
                    submitText = "Guardar rutina",
                    onSubmit = { viewModel.agregarRutina(usuarioId, ejercicio, series, repeticiones, pesoKg, fecha, onBack) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRutinasScreen(
    usuarioId: Long,
    viewModel: GymViewModel,
    onAgregar: () -> Unit,
    onEditar: (Long) -> Unit,
    onBack: () -> Unit
) {
    val rutinas by viewModel.listarRutinas(usuarioId).collectAsState(initial = emptyList())
    var rutinaAEliminar by remember { mutableStateOf<Rutina?>(null) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = { GymTopBar("Mis rutinas", onBack) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAgregar,
                containerColor = Color(0xFFFFD166),
                contentColor = Color(0xFF101227)
            ) {
                Text("+", fontWeight = FontWeight.Black)
            }
        }
    ) { padding ->
        GymBackground {
            if (rutinas.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(18.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    EmptyState(
                        title = "Tu tablero esta esperando",
                        subtitle = "Crea una rutina y empieza a llenar esta pantalla con entrenamientos que se sientan tuyos.",
                        onAction = onAgregar
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        HeroPanel(
                            title = "Entrenamientos guardados",
                            subtitle = "${rutinas.size} rutinas listas para revisar o mejorar.",
                            artworkVariant = 2,
                            brush = LimeBrush
                        )
                    }
                    items(rutinas) { rutina ->
                        RutinaItem(
                            rutina = rutina,
                            onEditar = { onEditar(rutina.id) },
                            onEliminar = { rutinaAEliminar = rutina }
                        )
                    }
                }
            }
        }
    }

    rutinaAEliminar?.let { rutina ->
        AlertDialog(
            onDismissRequest = { rutinaAEliminar = null },
            title = { Text("Eliminar rutina") },
            text = { Text("Deseas eliminar ${rutina.ejercicio}?") },
            confirmButton = {
                TextButton({
                    viewModel.eliminarRutina(rutina)
                    rutinaAEliminar = null
                }) { Text("Eliminar") }
            },
            dismissButton = { TextButton({ rutinaAEliminar = null }) { Text("Cancelar") } }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleRutinaScreen(usuarioId: Long, rutinaId: Long, viewModel: GymViewModel, onBack: () -> Unit) {
    var rutinaActual by remember { mutableStateOf<Rutina?>(null) }
    var ejercicio by remember { mutableStateOf("") }
    var series by remember { mutableStateOf("") }
    var repeticiones by remember { mutableStateOf("") }
    var pesoKg by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var confirmarEliminar by remember { mutableStateOf(false) }

    LaunchedEffect(rutinaId) {
        val rutina = viewModel.buscarRutina(rutinaId)
        rutinaActual = rutina
        rutina?.let {
            ejercicio = it.ejercicio
            series = it.series.toString()
            repeticiones = it.repeticiones.toString()
            pesoKg = it.pesoKg.toString()
            fecha = it.fecha
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Editar rutina", fontWeight = FontWeight.Black) },
                navigationIcon = { TextButton(onBack) { Text("Volver") } },
                actions = { TextButton({ confirmarEliminar = true }) { Text("Eliminar") } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF101227),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color(0xFFFFD166),
                    actionIconContentColor = Color(0xFFFF8CC6)
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
                    title = "Ajusta y mejora",
                    subtitle = "Pequenos cambios hoy pueden convertirse en marcas grandes manana.",
                    artworkVariant = 1,
                    brush = SunriseBrush
                )
                RutinaForm(
                    ejercicio = ejercicio,
                    onEjercicioChange = { ejercicio = it },
                    series = series,
                    onSeriesChange = { series = it },
                    repeticiones = repeticiones,
                    onRepeticionesChange = { repeticiones = it },
                    pesoKg = pesoKg,
                    onPesoKgChange = { pesoKg = it },
                    fecha = fecha,
                    onFechaChange = { fecha = it },
                    submitText = "Actualizar rutina",
                    onSubmit = { viewModel.actualizarRutina(rutinaId, usuarioId, ejercicio, series, repeticiones, pesoKg, fecha, onBack) }
                )
            }
        }
    }

    if (confirmarEliminar) {
        AlertDialog(
            onDismissRequest = { confirmarEliminar = false },
            title = { Text("Eliminar rutina") },
            text = { Text("Deseas eliminar esta rutina?") },
            confirmButton = {
                TextButton({
                    rutinaActual?.let { viewModel.eliminarRutina(it, onBack) }
                    confirmarEliminar = false
                }) { Text("Eliminar") }
            },
            dismissButton = { TextButton({ confirmarEliminar = false }) { Text("Cancelar") } }
        )
    }
}

@Composable
private fun RutinaForm(
    ejercicio: String,
    onEjercicioChange: (String) -> Unit,
    series: String,
    onSeriesChange: (String) -> Unit,
    repeticiones: String,
    onRepeticionesChange: (String) -> Unit,
    pesoKg: String,
    onPesoKgChange: (String) -> Unit,
    fecha: String,
    onFechaChange: (String) -> Unit,
    submitText: String,
    onSubmit: () -> Unit
) {
    GlassPanel {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Datos del ejercicio", color = Color(0xFF101227), fontWeight = FontWeight.Black)
            BrightTextField(ejercicio, onEjercicioChange, "Ejercicio")
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                BrightTextField(
                    value = series,
                    onValueChange = onSeriesChange,
                    label = "Series",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                BrightTextField(
                    value = repeticiones,
                    onValueChange = onRepeticionesChange,
                    label = "Reps",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
            BrightTextField(
                value = pesoKg,
                onValueChange = onPesoKgChange,
                label = "Peso kg",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            BrightTextField(fecha, onFechaChange, "Fecha")
            PrimaryAction(submitText, onSubmit)
        }
    }
}

@Composable
private fun RutinaItem(rutina: Rutina, onEditar: () -> Unit, onEliminar: () -> Unit) {
    val variant = kotlin.math.abs(rutina.ejercicio.hashCode()) % 3
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.94f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WorkoutArtwork(variant = variant, modifier = Modifier.size(86.dp).background(BerryBrush))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(rutina.ejercicio, color = Color(0xFF101227), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
                Text("Fecha: ${rutina.fecha}", color = Color(0xFF4B5578))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SmallBadgeDark("${rutina.series} series")
                    SmallBadgeDark("${rutina.repeticiones} reps")
                    SmallBadgeDark("${rutina.pesoKg} kg")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onEditar) { Text("Editar") }
                    TextButton(onEliminar) { Text("Eliminar", color = Color(0xFFFF4FA3)) }
                }
            }
        }
    }
}

@Composable
private fun SmallBadgeDark(text: String) {
    Text(
        text = text,
        color = Color.White,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .background(BerryBrush, androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 5.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GymTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.Black) },
        navigationIcon = { TextButton(onBack) { Text("Volver") } },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF101227),
            titleContentColor = Color.White,
            navigationIconContentColor = Color(0xFFFFD166)
        )
    )
}
