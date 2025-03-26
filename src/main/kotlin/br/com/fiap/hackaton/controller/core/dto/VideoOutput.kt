package br.com.fiap.hackaton.controller.core.dto

import br.com.fiap.hackaton.controller.core.domain.Upload
import java.time.LocalDateTime
import java.util.*

data class VideoOutput (
    val id: UUID,
    val name: String,
    val size: Int,
    val contentType: String,
    val uri : String,
    val uploadDdate: LocalDateTime
){
    companion object {
        fun toListVideoOutputList(upload: Upload): List<VideoOutput> =
            upload.videos
                .map { video ->
                    VideoOutput(
                        id = video.id,
                        name = video.name,
                        size = video.size,
                        contentType = video.contentType,
                        uri = video.uri,
                        uploadDdate = upload.creationDate
                    )
                }

        fun toListVideoOutputList(uploads: List<Upload>): List<VideoOutput> = uploads.flatMap { toListVideoOutputList(it) }
    }
}