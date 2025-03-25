package br.com.fiap.hackaton.controller.core.domain

import java.io.ByteArrayInputStream
import java.util.*

data class Video (
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val size: Int,
    val contentType: String,
    val uri : String,
    val byteArrayInputStream: ByteArrayInputStream? = null
)