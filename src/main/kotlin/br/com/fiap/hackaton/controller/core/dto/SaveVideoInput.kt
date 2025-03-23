package br.com.fiap.hackaton.controller.core.dto

import java.io.InputStream

data class SaveVideoInput(
    val name: String,
    val size: Int,
    val contentType: String,
    val bytes: ByteArray,
    val inputStream: InputStream
)
