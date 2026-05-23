package com.example.gymtrackersinia.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "rutina",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("usuario_id")]
)
data class Rutina(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "usuario_id") val usuarioId: Long,
    val ejercicio: String,
    val series: Int,
    val repeticiones: Int,
    @ColumnInfo(name = "peso_kg") val pesoKg: Double,
    val fecha: String
)
