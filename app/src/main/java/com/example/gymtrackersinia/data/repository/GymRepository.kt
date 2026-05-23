package com.example.gymtrackersinia.data.repository

import com.example.gymtrackersinia.data.local.Rutina
import com.example.gymtrackersinia.data.local.RutinaDao
import com.example.gymtrackersinia.data.local.Usuario
import com.example.gymtrackersinia.data.local.UsuarioDao
import com.example.gymtrackersinia.data.remote.ExerciseApiService
import com.example.gymtrackersinia.data.remote.ExerciseDto
import kotlinx.coroutines.flow.Flow

class GymRepository(
    private val usuarioDao: UsuarioDao,
    private val rutinaDao: RutinaDao,
    private val exerciseApiService: ExerciseApiService
) {
    suspend fun registrarUsuario(nombre: String, email: String, password: String): Result<Long> {
        if (usuarioDao.buscarPorEmail(email) != null) {
            return Result.failure(IllegalArgumentException("El usuario ya existe"))
        }
        return Result.success(usuarioDao.insertar(Usuario(nombre = nombre, email = email, password = password)))
    }

    suspend fun login(email: String, password: String): Usuario? = usuarioDao.buscarPorCredenciales(email, password)
    suspend fun buscarUsuario(id: Long): Usuario? = usuarioDao.buscarPorId(id)
    fun listarRutinas(usuarioId: Long): Flow<List<Rutina>> = rutinaDao.listarPorUsuario(usuarioId)
    suspend fun buscarRutina(id: Long): Rutina? = rutinaDao.buscarPorId(id)
    suspend fun agregarRutina(rutina: Rutina) = rutinaDao.insertar(rutina)
    suspend fun actualizarRutina(rutina: Rutina) = rutinaDao.actualizar(rutina)
    suspend fun eliminarRutina(rutina: Rutina) = rutinaDao.eliminar(rutina)
    fun contarRutinas(usuarioId: Long): Flow<Int> = rutinaDao.contarPorUsuario(usuarioId)
    fun volumenTotal(usuarioId: Long): Flow<Double?> = rutinaDao.volumenTotalPorUsuario(usuarioId)
    suspend fun listarEjerciciosRemotos(muscle: String? = null): List<ExerciseDto> = exerciseApiService.listarEjercicios(muscle)
}
