package br.com.fiap.hackaton.controller.core.domain

import java.util.UUID

data class User (
    val id: UUID,
    val name: String,
    val email: String,
    val cpf: String
)