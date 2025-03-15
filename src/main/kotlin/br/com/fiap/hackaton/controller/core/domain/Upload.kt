package br.com.fiap.hackaton.controller.core.domain

import java.util.*

data class Upload (
    var id: UUID,
    val videos: List<Video>,
    val usuario: Usuario
)