package br.com.fiap.hackaton.controller.infrastructure.persistence.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "video")
data class VideoEntity (
    @Id
    var id: UUID,
    val name: String,
    val size: Int,
    val contentType: String,
    val uri : String,
    @JoinColumn(name = "upload_id")
    @ManyToOne(fetch = FetchType.EAGER)
    val uploadEntity: UploadEntity
) {
    fun toDomain() = br.com.fiap.hackaton.controller.core.domain.Video(
        id = id,
        name = name,
        size = size,
        contentType = contentType,
        uri = uri
    )

    companion object {
        fun toEntity(video: br.com.fiap.hackaton.controller.core.domain.Video, uploadEntity: UploadEntity): VideoEntity {
            return VideoEntity(
                id = video.id,
                name = video.name,
                size = video.size,
                contentType = video.contentType,
                uri = video.uri,
                uploadEntity = uploadEntity
            )
        }
    }
}