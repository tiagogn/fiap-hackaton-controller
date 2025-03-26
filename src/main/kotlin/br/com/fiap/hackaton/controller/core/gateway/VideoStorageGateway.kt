package br.com.fiap.hackaton.controller.core.gateway

import br.com.fiap.hackaton.controller.core.domain.Upload
import br.com.fiap.hackaton.controller.core.domain.Video

interface VideoStorageGateway {
    fun writeAllBytes(upload: Upload)
    fun readAllBytes(cpf: String, video: Video)
}