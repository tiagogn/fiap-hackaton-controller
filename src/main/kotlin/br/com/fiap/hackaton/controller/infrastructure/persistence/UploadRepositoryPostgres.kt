package br.com.fiap.hackaton.controller.infrastructure.persistence

import br.com.fiap.hackaton.controller.core.domain.Upload
import br.com.fiap.hackaton.controller.core.persistence.UploadRepository
import br.com.fiap.hackaton.controller.infrastructure.persistence.entity.UploadEntity
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

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
}