package br.com.fiap.hackaton.controller.core.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class UserTest {

    @Test
    fun `should create User with correct attributes`() {
        val id = UUID.randomUUID()
        val name = "John Doe"
        val email = "test@email.com"
        val cpf = "12345678900"

        val user = User(
            id = id,
            name = name,
            email = email,
            cpf = cpf
        )

        assertEquals(id, user.id)
        assertEquals(name, user.name)
        assertEquals(email, user.email)
        assertEquals(cpf, user.cpf)
    }
}
