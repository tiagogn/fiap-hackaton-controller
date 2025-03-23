package br.com.fiap.hackaton.controller.core.domain

import java.io.InputStream
import java.util.UUID

data class Video (
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val size: Int,
    val contentType: String,
    val uri : String,
    val bytes: ByteArray? = null,
    val inputStream: InputStream? = null
)