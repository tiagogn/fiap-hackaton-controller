package br.com.fiap.hackaton.controller.infrastructure.gateway

import br.com.fiap.hackaton.controller.core.gateway.SendVideoMessageGateway
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import java.util.*

@Component
class SendMessageGatewayImpl(
    private val sqsClient: SqsClient,
    @Value("\${aws.sqs.queue}")
    private val queueName: String,
): SendVideoMessageGateway {

    private val logger = LoggerFactory.getLogger(SendMessageGatewayImpl::class.java)

    override fun send(uploadId: UUID) {
        val queueUrl = sqsClient.getQueueUrl { it.queueName(queueName) }.queueUrl()

        val request = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageBody(uploadId.toString())
            .build()

        val result = sqsClient.sendMessage(request)
        logger.info("Mensagem ${uploadId} enviada com ID: ${result.messageId()}")
    }
}