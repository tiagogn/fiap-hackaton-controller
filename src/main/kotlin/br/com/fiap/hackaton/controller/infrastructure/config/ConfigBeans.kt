package br.com.fiap.hackaton.controller.infrastructure.config

import br.com.fiap.hackaton.controller.core.application.SaveVideo
import br.com.fiap.hackaton.controller.core.application.SaveVideoImpl
import br.com.fiap.hackaton.controller.core.gateway.SendVideoMessageGateway
import br.com.fiap.hackaton.controller.core.gateway.VideoStorageGateway
import br.com.fiap.hackaton.controller.core.persistence.UploadRepository
import br.com.fiap.hackaton.controller.core.persistence.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConfigBeans {

    @Bean
    fun salvarVideo(
        userRepository: UserRepository,
        uploadRepository: UploadRepository,
        videoStorageGateway: VideoStorageGateway,
        sendVideoMessageGateway: SendVideoMessageGateway
    ): SaveVideo {
        return SaveVideoImpl(
            userRepository,
            uploadRepository,
            videoStorageGateway,
            sendVideoMessageGateway
        )
    }
}