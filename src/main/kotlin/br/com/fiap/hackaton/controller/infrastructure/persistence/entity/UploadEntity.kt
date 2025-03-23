package br.com.fiap.hackaton.controller.infrastructure.persistence.entity

import br.com.fiap.hackaton.controller.core.domain.Upload
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "upload")
data class UploadEntity (
    @Id
    var id: UUID,
    @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER, mappedBy = "uploadEntity")
    val videos: MutableList<VideoEntity> = mutableListOf(),
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    val userEntity: UserEntity,
    val creationDate: LocalDateTime,
){
    fun toDomain() = Upload(
        id = id,
        videos = videos.map { it.toDomain() },
        user = userEntity.toDomain(),
        creationDate = creationDate
    )

    companion object {
        fun toEntity(upload: Upload): UploadEntity {

            val uploadEntity = UploadEntity(
                id = upload.id,
                userEntity = UserEntity.toEntity(upload.user),
                creationDate = upload.creationDate
            )
            val videos = upload.videos.map { VideoEntity.toEntity(it, uploadEntity) }
            uploadEntity.videos.addAll(videos)
            return uploadEntity
        }
    }
}