package br.com.fiap.hackaton.controller.core.persistence

import br.com.fiap.hackaton.controller.core.domain.Upload
import br.com.fiap.hackaton.controller.core.domain.User

interface UploadRepository {
    fun save(upload: Upload) : Upload
    fun findByUser(user: User) : Upload?
}