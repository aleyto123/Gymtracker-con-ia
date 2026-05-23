package com.example.gymtrackersinia.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
        topBar = { TopAppBar(title = { Text("Agregar rutina") }, navigationIcon = { TextButton(onBack) { Text("Volver") } }) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        RutinaForm(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
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
            submitText = "Guardar",
            onSubmit = { viewModel.agregarRutina(usuarioId, ejercicio, series, repeticiones, pesoKg, fecha, onBack) }
        )
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
        topBar = { TopAppBar(title = { Text("Rutinas") }, navigationIcon = { TextButton(onBack) { Text("Volver") } }) },
        floatingActionButton = { FloatingActionButton(onAgregar) { Text("+") } }
    ) { padding ->
        if (rutinas.isEmpty()) {
            Column(Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
                Text("No hay rutinas registradas")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(rutinas) { rutina ->
                    RutinaItem(rutina, { onEditar(rutina.id) }, { rutinaAEliminar = rutina })
                }
            }
        }
    }

    rutinaAEliminar?.let { rutina ->
        AlertDialog(
            onDismissRequest = { rutinaAEliminar = null },
            title = { Text("Eliminar rutina") },
            text = { Text("¿Deseas eliminar ${rutina.ejercicio}?") },
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
        topBar = {
            TopAppBar(
                title = { Text("Editar rutina") },
                navigationIcon = { TextButton(onBack) { Text("Volver") } },
                actions = { TextButton({ confirmarEliminar = true }) { Text("Eliminar") } }
            )
        }
    ) { padding ->
        RutinaForm(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
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
            submitText = "Actualizar",
            onSubmit = { viewModel.actualizarRutina(rutinaId, usuarioId, ejercicio, series, repeticiones, pesoKg, fecha, onBack) }
        )
    }

    if (confirmarEliminar) {
        AlertDialog(
            onDismissRequest = { confirmarEliminar = false },
            title = { Text("Eliminar rutina") },
            text = { Text("¿Deseas eliminar esta rutina?") },
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
    modifier: Modifier,
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
    Column(modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(ejercicio, onEjercicioChange, label = { Text("Ejercicio") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(series, onSeriesChange, label = { Text("Series") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(repeticiones, onRepeticionesChange, label = { Text("Repeticiones") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(pesoKg, onPesoKgChange, label = { Text("Peso kg") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(fecha, onFechaChange, label = { Text("Fecha") }, modifier = Modifier.fillMaxWidth())
        Button(onSubmit, modifier = Modifier.fillMaxWidth()) { Text(submitText) }
    }
}

@Composable
private fun RutinaItem(rutina: Rutina, onEditar: () -> Unit, onEliminar: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(rutina.ejercicio, style = MaterialTheme.typography.titleMedium)
        Text("Fecha: ${rutina.fecha}")
        Text("Series: ${rutina.series}  Repeticiones: ${rutina.repeticiones}  Peso: ${rutina.pesoKg} kg")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(onEditar) { Text("Editar") }
            TextButton(onEliminar) { Text("Eliminar") }
        }
    }
}
