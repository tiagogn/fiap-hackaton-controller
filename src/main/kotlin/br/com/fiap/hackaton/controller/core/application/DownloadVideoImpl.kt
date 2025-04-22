package br.com.fiap.hackaton.controller.core.application

import br.com.fiap.hackaton.controller.core.dto.VideoBytesOutput
import br.com.fiap.hackaton.controller.core.exception.VideoNotFoundException
import br.com.fiap.hackaton.controller.core.gateway.VideoStorageGateway
import br.com.fiap.hackaton.controller.core.persistence.UploadRepository
import java.util.*

class DownloadVideoImpl(
    private val uploadRepository: UploadRepository,
    private val videoStorageGateway: VideoStorageGateway
): DownloadVideo {
    override fun execute(cpf: String, videoId: UUID): VideoBytesOutput {

        val video = uploadRepository.findVideoByVideoId(videoId) ?: throw VideoNotFoundException("Video $videoId not found")

        videoStorageGateway.readAllBytes(video)

        video.byteArrayInputStream?.available()?.toLong() ?: throw VideoNotFoundException("Video $videoId not found")

        return VideoBytesOutput(
            name = video.zipFileName!!,
            contentType = video.contentType,
            bytes = video.byteArrayInputStream!!.readAllBytes(),
        )
    }
}