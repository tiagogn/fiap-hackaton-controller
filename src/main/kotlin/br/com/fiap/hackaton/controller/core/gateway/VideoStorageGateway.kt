package br.com.fiap.hackaton.controller.core.gateway

import br.com.fiap.hackaton.controller.core.domain.Video

interface VideoStorageGateway {
    fun writeAll(files: List<Video>)
}