package br.com.fiap.hackaton.controller.infrastructure.gateway

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import software.amazon.awssdk.services.sqs.model.SendMessageResponse
import java.util.UUID
import java.util.function.Consumer

class SendMessageGatewayImplTest {

    private val sqsClient = mockk<SqsClient>()
    private val queueName = "test-queue"
    private val expectedQueueUrl = "https://sqs.amazonaws.com/123456789012/test-queue"
    private val gateway = SendMessageGatewayImpl(sqsClient, queueName)

    @Test
    fun `should send message to SQS`() {
        val uploadId = UUID.randomUUID()

        every {
            sqsClient.getQueueUrl(any<Consumer<GetQueueUrlRequest.Builder>>())
        } returns GetQueueUrlResponse.builder()
            .queueUrl("https://sqs.amazonaws.com/123456789012/test-queue")
            .build()



        every {
            sqsClient.sendMessage(any<SendMessageRequest>())
        } returns SendMessageResponse.builder().messageId("mocked-message-id").build()

        gateway.send(uploadId)

        verify {
            sqsClient.getQueueUrl(any<Consumer<GetQueueUrlRequest.Builder>>())
            sqsClient.sendMessage(withArg<SendMessageRequest> {
                assert(it.queueUrl() == expectedQueueUrl)
                assert(it.messageBody() == uploadId.toString())
            })
        }

        confirmVerified(sqsClient)
    }
}
