package br.com.fiap.hackaton.controller.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI

@Configuration
class AWSConfig(
    @Value("\${aws.accessKey}")
    val awsAccessKey: String,
    @Value("\${aws.secretKey}")
    val awsSecretKey: String,
    @Value("\${aws.region}")
    val awsRegion: String,
    @Value("\${aws.endpoint}")
    val endpoint: String,
    @Value("\${aws.forcePathStyle}")
    val forcePathStyle: Boolean
) {
    @Bean
    fun s3Client(): S3Client {
        return S3Client
            .builder()
            .region(software.amazon.awssdk.regions.Region.of(awsRegion))
            .credentialsProvider { AwsBasicCredentials.create(awsAccessKey, awsSecretKey) }
            .endpointOverride(URI.create(endpoint))
            .forcePathStyle(forcePathStyle)
            .build()
    }

}