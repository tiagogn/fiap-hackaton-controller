package br.com.fiap.hackaton.controller.core.persistence

import br.com.fiap.hackaton.controller.core.domain.Upload

interface UploadRepository {
    fun save(upload: Upload) : Upload
}