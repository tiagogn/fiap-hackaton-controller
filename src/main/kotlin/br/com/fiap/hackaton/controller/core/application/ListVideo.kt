package br.com.fiap.hackaton.controller.core.application

import br.com.fiap.hackaton.controller.core.dto.VideoOutput

interface ListVideo {
    fun execute(cpf: String): List<VideoOutput>
}