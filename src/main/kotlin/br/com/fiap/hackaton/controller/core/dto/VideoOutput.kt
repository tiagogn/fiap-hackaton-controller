package br.com.fiap.hackaton.controller.core.dto

import br.com.fiap.hackaton.controller.core.domain.StatusVideo
import br.com.fiap.hackaton.controller.core.domain.Upload
import java.time.LocalDateTime

data class VideoOutput (
    val name: String,
    val size: Int,
    val contentType: String,
    val uri : String,
    val uploadDdate: LocalDateTime,
    val status: String
){
    companion object {
        fun toListVideoOutputList(upload: Upload, downloadUrl: String): List<VideoOutput> =
            upload.videos
                .map { video ->
                    VideoOutput(
                        name = video.name,
                        size = video.size,
                        contentType = video.contentType,
                        uri = if (video.status == StatusVideo.PROCESSED ) downloadUrl + video.zipFileName else "",
                        uploadDdate = upload.creationDate,
                        status = video.status.name
                    )
                }

        fun toListVideoOutputList(uploads: List<Upload>, downloadUrl: String): List<VideoOutput> = uploads.flatMap { toListVideoOutputList(it, downloadUrl) }
    }
}