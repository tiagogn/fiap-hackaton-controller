package br.com.fiap.hackaton.controller.core.application

import br.com.fiap.hackaton.controller.core.dto.VideoBytesOutput
import java.util.*

interface DownloadVideo {
    fun execute(cpf: String, videoId: UUID): VideoBytesOutput
}