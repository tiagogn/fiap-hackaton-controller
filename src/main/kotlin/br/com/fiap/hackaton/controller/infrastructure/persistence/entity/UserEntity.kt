package br.com.fiap.hackaton.controller.infrastructure.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "\"user\"")
data class UserEntity (
    @Id
    val id: UUID,
    val name: String,
    val email: String,
    val cpf: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun toDomain() = br.com.fiap.hackaton.controller.core.domain.User(
        id = id,
        name = name,
        email = email,
        cpf = cpf
    )

    companion object {
        fun toEntity(user: br.com.fiap.hackaton.controller.core.domain.User): UserEntity {
            return UserEntity(
                id = user.id,
                name = user.name,
                email = user.email,
                cpf = user.cpf
            )
        }
    }
}

