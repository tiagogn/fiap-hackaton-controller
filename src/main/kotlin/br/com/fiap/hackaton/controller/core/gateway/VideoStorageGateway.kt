package br.com.fiap.hackaton.controller.core.gateway

import br.com.fiap.hackaton.controller.core.domain.Upload

interface VideoStorageGateway {
    fun writeAll(upload: Upload)
}