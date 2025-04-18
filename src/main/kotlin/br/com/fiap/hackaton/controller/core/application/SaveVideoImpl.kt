package br.com.fiap.hackaton.controller.core.application

import br.com.fiap.hackaton.controller.core.domain.Upload
import br.com.fiap.hackaton.controller.core.domain.Video
import br.com.fiap.hackaton.controller.core.dto.SaveVideoInput
import br.com.fiap.hackaton.controller.core.exception.UserNotFoundException
import br.com.fiap.hackaton.controller.core.gateway.SendVideoMessageGateway
import br.com.fiap.hackaton.controller.core.gateway.VideoStorageGateway
import br.com.fiap.hackaton.controller.core.persistence.UploadRepository
import br.com.fiap.hackaton.controller.core.persistence.UserRepository
import java.io.ByteArrayInputStream

class SaveVideoImpl(
    private val userRepository: UserRepository,
    private val uploadRepository: UploadRepository,
    private val videoStorageGateway: VideoStorageGateway,
    private val sendVideoMessageGateway: SendVideoMessageGateway
): SaveVideo {
    override fun execute(cpf: String, saveVideoInputList: List<SaveVideoInput>) {

        val user = userRepository.findByCpf(cpf) ?: throw UserNotFoundException("User $cpf not found")

        val videos = saveVideoInputList.map { videoInput ->
            Video(
                name = videoInput.name,
                size = videoInput.size,
                contentType = videoInput.contentType,
                byteArrayInputStream = ByteArrayInputStream(videoInput.bytes),
            )
        }

        val upload = Upload(
            user = user,
            videos = videos
        )

        videoStorageGateway.writeAllBytes(upload)

        uploadRepository.save(upload)

        sendVideoMessageGateway.send(upload.id)
    }
}