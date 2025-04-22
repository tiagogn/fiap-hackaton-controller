package br.com.fiap.hackaton.controller.core.domain

import java.io.ByteArrayInputStream
import java.util.*

data class Video (
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val size: Int,
    val contentType: String,
    var byteArrayInputStream: ByteArrayInputStream? = null,
    var status: StatusVideo = StatusVideo.PENDING,
    var zipFileName: String? = null,
    val uploadId: String? = null
)

enum class StatusVideo {
    PENDING,
    PROCESSING,
    PROCESSED,
    ERROR
}