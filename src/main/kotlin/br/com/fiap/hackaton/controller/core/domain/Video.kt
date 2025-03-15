package br.com.fiap.hackaton.controller.core.domain

import java.util.UUID

data class Video (
    var id: UUID,
    val nome: String,
    val uri: String
)