package br.com.fiap.hackaton.controller.core.persistence

import br.com.fiap.hackaton.controller.core.domain.User

interface UserRepository {
    fun findByCpf(cpf: String): User?
}