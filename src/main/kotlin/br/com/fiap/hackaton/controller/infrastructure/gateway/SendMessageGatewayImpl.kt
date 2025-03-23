package br.com.fiap.hackaton.controller.infrastructure.gateway

import br.com.fiap.hackaton.controller.core.gateway.SendVideoMessageGateway
import org.springframework.stereotype.Component
import java.util.*

@Component
class SendMessageGatewayImpl: SendVideoMessageGateway {
    override fun send(uploadId: UUID) {
        println("Sending message for upload $uploadId")
    }
}