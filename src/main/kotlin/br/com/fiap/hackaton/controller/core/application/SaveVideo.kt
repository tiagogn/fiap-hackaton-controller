package br.com.fiap.hackaton.controller.core.application

import br.com.fiap.hackaton.controller.core.dto.SaveVideoInput

interface SaveVideo {
    fun execute(cpf: String, saveVideoInputList: List<SaveVideoInput>)
}