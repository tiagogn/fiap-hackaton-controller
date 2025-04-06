package br.com.fiap.hackaton.controller.core.domain

import java.time.LocalDateTime
import java.util.*

data class Upload (
    val id: UUID = UUID.randomUUID(),
    val videos: List<Video> = listOf(),
    val user: User,
    val creationDate: LocalDateTime = LocalDateTime.now(),
    val zipFile: String? = null,
)