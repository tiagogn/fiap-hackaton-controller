package br.com.fiap.hackaton.controller.core.application

import br.com.fiap.hackaton.controller.core.dto.VideoBytesOutput
import br.com.fiap.hackaton.controller.core.gateway.VideoStorageGateway
import br.com.fiap.hackaton.controller.core.persistence.UploadRepository
import java.util.*

class DownloadVideoImpl(
    private val uploadRepository: UploadRepository,
    private val videoStorageGateway: VideoStorageGateway
): DownloadVideo {
    override fun execute(cpf: String, videoId: UUID): VideoBytesOutput {

        val video = uploadRepository.findVideoByVideoId(videoId) ?: throw Exception("Video not found")

        videoStorageGateway.readAllBytes(cpf, video)

        video.byteArrayInputStream?.available()?.toLong() ?: throw Exception("Video not found")

        return VideoBytesOutput(
            name = video.name,
            contentType = video.contentType,
            bytes = video.byteArrayInputStream!!.readAllBytes()
        )
    }
}