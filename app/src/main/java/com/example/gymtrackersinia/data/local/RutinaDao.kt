package com.example.gymtrackersinia.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RutinaDao {
    @Insert
    suspend fun insertar(rutina: Rutina): Long

    @Update
    suspend fun actualizar(rutina: Rutina)

    @Delete
    suspend fun eliminar(rutina: Rutina)

    @Query("SELECT * FROM rutina WHERE usuario_id = :usuarioId ORDER BY id DESC")
    fun listarPorUsuario(usuarioId: Long): Flow<List<Rutina>>

    @Query("SELECT * FROM rutina WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Long): Rutina?

    @Query("SELECT COUNT(*) FROM rutina WHERE usuario_id = :usuarioId")
    fun contarPorUsuario(usuarioId: Long): Flow<Int>

    @Query("SELECT SUM(peso_kg * series * repeticiones) FROM rutina WHERE usuario_id = :usuarioId")
    fun volumenTotalPorUsuario(usuarioId: Long): Flow<Double?>
}
