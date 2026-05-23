package com.example.gymtrackersinia.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    @Insert
    suspend fun insertar(usuario: Usuario): Long

    @Query("SELECT * FROM usuario WHERE email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): Usuario?

    @Query("SELECT * FROM usuario WHERE email = :email AND password = :password LIMIT 1")
    suspend fun buscarPorCredenciales(email: String, password: String): Usuario?

    @Query("SELECT * FROM usuario WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Long): Usuario?
}
