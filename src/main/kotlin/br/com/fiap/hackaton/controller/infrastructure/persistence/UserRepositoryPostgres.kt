package br.com.fiap.hackaton.controller.infrastructure.persistence

import br.com.fiap.hackaton.controller.core.domain.User
import br.com.fiap.hackaton.controller.core.persistence.UserRepository
import br.com.fiap.hackaton.controller.infrastructure.persistence.entity.UserEntity
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Repository

@Repository
@Qualifier("userRepository")
class UserRepositoryPostgres(
    private val entityManager: EntityManager,
): UserRepository {

    override fun findByCpf(cpf: String): User? {
        val userEntity = entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.cpf = :cpf")
            .setParameter("cpf", cpf)
            .singleResult as UserEntity?
        return userEntity?.toDomain()
    }
}