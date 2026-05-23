package com.example.gymtrackersinia.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.gymtrackersinia.data.local.Rutina
import com.example.gymtrackersinia.data.local.Usuario
import com.example.gymtrackersinia.data.repository.GymRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class GymViewModel(private val repository: GymRepository) : ViewModel() {
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    fun login(email: String, password: String, onSuccess: (Long) -> Unit) {
        viewModelScope.launch {
            val usuario = repository.login(email.trim(), password)
            if (usuario == null) {
                _mensaje.value = "Usuario o contraseña incorrectos"
            } else {
                _usuarioActual.value = usuario
                onSuccess(usuario.id)
            }
        }
    }

    fun registrar(nombre: String, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val correo = email.trim()
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                _mensaje.value = "Email inválido"
                return@launch
            }
            if (nombre.isBlank() || password.isBlank()) {
                _mensaje.value = "Completa todos los campos"
                return@launch
            }
            repository.registrarUsuario(nombre.trim(), correo, password)
                .onSuccess {
                    _mensaje.value = "Usuario registrado"
                    onSuccess()
                }
                .onFailure { _mensaje.value = it.message ?: "No se pudo registrar" }
        }
    }

    fun cargarUsuario(usuarioId: Long) {
        viewModelScope.launch {
            _usuarioActual.value = repository.buscarUsuario(usuarioId)
        }
    }

    fun listarRutinas(usuarioId: Long): Flow<List<Rutina>> {
        return if (usuarioId > 0) repository.listarRutinas(usuarioId) else flowOf(emptyList())
    }

    fun contarRutinas(usuarioId: Long): Flow<Int> {
        return if (usuarioId > 0) repository.contarRutinas(usuarioId) else flowOf(0)
    }

    fun volumenTotal(usuarioId: Long): Flow<Double?> {
        return if (usuarioId > 0) repository.volumenTotal(usuarioId) else flowOf(0.0)
    }

    suspend fun buscarRutina(rutinaId: Long): Rutina? = repository.buscarRutina(rutinaId)

    fun agregarRutina(
        usuarioId: Long,
        ejercicio: String,
        series: String,
        repeticiones: String,
        pesoKg: String,
        fecha: String,
        onSuccess: () -> Unit
    ) {
        val rutina = construirRutina(0, usuarioId, ejercicio, series, repeticiones, pesoKg, fecha) ?: return
        viewModelScope.launch {
            repository.agregarRutina(rutina)
            _mensaje.value = "Rutina agregada"
            onSuccess()
        }
    }

    fun actualizarRutina(
        rutinaId: Long,
        usuarioId: Long,
        ejercicio: String,
        series: String,
        repeticiones: String,
        pesoKg: String,
        fecha: String,
        onSuccess: () -> Unit
    ) {
        val rutina = construirRutina(rutinaId, usuarioId, ejercicio, series, repeticiones, pesoKg, fecha) ?: return
        viewModelScope.launch {
            repository.actualizarRutina(rutina)
            _mensaje.value = "Rutina actualizada"
            onSuccess()
        }
    }

    fun eliminarRutina(rutina: Rutina, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            repository.eliminarRutina(rutina)
            _mensaje.value = "Rutina eliminada"
            onSuccess()
        }
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
    }

    fun limpiarMensaje() {
        _mensaje.value = null
    }

    private fun construirRutina(
        id: Long,
        usuarioId: Long,
        ejercicio: String,
        series: String,
        repeticiones: String,
        pesoKg: String,
        fecha: String
    ): Rutina? {
        val seriesInt = series.toIntOrNull()
        val repeticionesInt = repeticiones.toIntOrNull()
        val pesoDouble = pesoKg.toDoubleOrNull()
        if (ejercicio.isBlank() || fecha.isBlank() || seriesInt == null || repeticionesInt == null || pesoDouble == null) {
            _mensaje.value = "Completa la rutina con valores válidos"
            return null
        }
        return Rutina(id, usuarioId, ejercicio.trim(), seriesInt, repeticionesInt, pesoDouble, fecha.trim())
    }
}

class GymViewModelFactory(private val repository: GymRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GymViewModel::class.java)) return GymViewModel(repository) as T
        throw IllegalArgumentException("ViewModel desconocido")
    }
}
