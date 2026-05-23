package com.example.gymtrackersinia.ui.navigation

object Routes {
    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val MENU = "menu/{usuarioId}"
    const val AGREGAR_RUTINA = "agregar_rutina/{usuarioId}"
    const val LISTA_RUTINAS = "lista_rutinas/{usuarioId}"
    const val DETALLE_RUTINA = "detalle_rutina/{usuarioId}/{rutinaId}"
    const val PERFIL = "perfil/{usuarioId}"

    fun menu(usuarioId: Long) = "menu/$usuarioId"
    fun agregarRutina(usuarioId: Long) = "agregar_rutina/$usuarioId"
    fun listaRutinas(usuarioId: Long) = "lista_rutinas/$usuarioId"
    fun detalleRutina(usuarioId: Long, rutinaId: Long) = "detalle_rutina/$usuarioId/$rutinaId"
    fun perfil(usuarioId: Long) = "perfil/$usuarioId"
}
