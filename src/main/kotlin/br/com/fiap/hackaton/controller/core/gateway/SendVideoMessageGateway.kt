package br.com.fiap.hackaton.controller.core.gateway

import java.util.*

interface SendVideoMessageGateway {
    fun send(uploadId: UUID)
}