package br.com.fiap.hackaton.controller.infrastructure.gateway

import br.com.fiap.hackaton.controller.core.domain.Video
import br.com.fiap.hackaton.controller.core.gateway.VideoStorageGateway
import org.springframework.stereotype.Component

@Component
class FileStorageGatewayS3: VideoStorageGateway {
    override fun writeAll(files: List<Video>) {
        println("Writing ${files.size} videos")
    }
}