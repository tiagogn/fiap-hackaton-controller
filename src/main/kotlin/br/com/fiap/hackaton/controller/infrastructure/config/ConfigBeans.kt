package br.com.fiap.hackaton.controller.infrastructure.config

import br.com.fiap.hackaton.controller.core.application.*
import br.com.fiap.hackaton.controller.core.gateway.SendVideoMessageGateway
import br.com.fiap.hackaton.controller.core.gateway.VideoStorageGateway
import br.com.fiap.hackaton.controller.core.persistence.UploadRepository
import br.com.fiap.hackaton.controller.core.persistence.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConfigBeans {

    @Bean
    fun saveVideo(
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

    @Bean
    fun listVideo(
        userRepository: UserRepository,
        uploadRepository: UploadRepository
    ): ListVideo {
        return ListVideoImpl(
            userRepository,
            uploadRepository
        )
    }

    @Bean
    fun downloadVideo(
        uploadRepository: UploadRepository,
        videoStorageGateway: VideoStorageGateway
    ): DownloadVideo {
        return DownloadVideoImpl(
            uploadRepository,
            videoStorageGateway
        )
    }

}