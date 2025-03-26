package br.com.fiap.hackaton.controller.infrastructure.persistence

import br.com.fiap.hackaton.controller.core.domain.Upload
import br.com.fiap.hackaton.controller.core.domain.User
import br.com.fiap.hackaton.controller.core.domain.Video
import br.com.fiap.hackaton.controller.core.persistence.UploadRepository
import br.com.fiap.hackaton.controller.infrastructure.persistence.entity.UploadEntity
import br.com.fiap.hackaton.controller.infrastructure.persistence.entity.UserEntity
import br.com.fiap.hackaton.controller.infrastructure.persistence.entity.VideoEntity
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Qualifier("uploadRepository")
@Transactional
class UploadRepositoryPostgres(
    private val entityManager: EntityManager,
): UploadRepository {
    override fun save(upload: Upload): Upload {
        val uploadEntity = UploadEntity.toEntity(upload)
        entityManager.persist(uploadEntity)
        return uploadEntity.toDomain()
    }

    override fun findByUser(user: User): List<Upload> {
        val userEntity = UserEntity.toEntity(user)
        val query = entityManager.createQuery("SELECT u FROM UploadEntity u WHERE u.userEntity = :userEntity", UploadEntity::class.java)
        query.setParameter("userEntity", userEntity)
        return query.resultList.map { it.toDomain() }
    }

    override fun findVideoByVideoId(videoId: UUID): Video? {
        val query = entityManager.createQuery("SELECT v from VideoEntity v where v.id = :videoId", VideoEntity::class.java)
        query.setParameter("videoId", videoId)
        return query.resultList.firstOrNull()?.toDomain()
    }
}