package br.com.fiap.hackaton.controller.core.persistence

import br.com.fiap.hackaton.controller.core.domain.Upload
import br.com.fiap.hackaton.controller.core.domain.User
import br.com.fiap.hackaton.controller.core.domain.Video
import java.util.UUID

interface UploadRepository {
    fun save(upload: Upload) : Upload
    fun findByUser(user: User) : List<Upload>
    fun findVideoByVideoId(videoId: UUID) : Video?
}