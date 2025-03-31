package br.com.fiap.hackaton.controller.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.services.sqs.SqsClient
import java.net.URI

@Configuration
class SQSConfig {
    @Value("\${aws.accessKey}")
    private lateinit var accessKey: String

    @Value("\${aws.secretKey}")
    private lateinit var secretKey: String

    @Value("\${aws.region}")
    private lateinit var region: String

    @Value("\${aws.sqs.endpoint}")
    private lateinit var sqsEndpoint: String

    @Bean
    fun sqsClient(): SqsClient {
        val credentials = AwsBasicCredentials.create(accessKey, secretKey)

        return SqsClient.builder()
            .credentialsProvider { credentials }
            .region(software.amazon.awssdk.regions.Region.of(region))
            .endpointOverride(URI.create(sqsEndpoint))
            .build()
    }
}