package br.com.fiap.hackaton.controller.core.application

import br.com.fiap.hackaton.controller.core.domain.Video

interface ListarVideo {
    fun execute(): List<Video>
}