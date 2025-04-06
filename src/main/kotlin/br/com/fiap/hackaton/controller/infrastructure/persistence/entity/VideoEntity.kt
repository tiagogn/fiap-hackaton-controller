package br.com.fiap.hackaton.controller.infrastructure.persistence.entity

import br.com.fiap.hackaton.controller.core.domain.StatusVideo
import jakarta.persistence.*
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType
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
    val uploadEntity: UploadEntity,
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    val status: StatusVideo = StatusVideo.PENDING
) {
    fun toDomain() = br.com.fiap.hackaton.controller.core.domain.Video(
        id = id,
        name = name,
        size = size,
        contentType = contentType,
        uri = uri,
        status = status
    )

    companion object {
        fun toEntity(video: br.com.fiap.hackaton.controller.core.domain.Video, uploadEntity: UploadEntity): VideoEntity {
            return VideoEntity(
                id = video.id,
                name = video.name,
                size = video.size,
                contentType = video.contentType,
                uri = video.uri,
                uploadEntity = uploadEntity,
                status = video.status
            )
        }
    }
}
