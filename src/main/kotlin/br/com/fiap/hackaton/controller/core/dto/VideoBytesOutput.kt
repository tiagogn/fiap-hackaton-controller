package br.com.fiap.hackaton.controller.core.dto

data class VideoBytesOutput (
    val name: String,
    val contentType: String,
    val bytes: ByteArray
)