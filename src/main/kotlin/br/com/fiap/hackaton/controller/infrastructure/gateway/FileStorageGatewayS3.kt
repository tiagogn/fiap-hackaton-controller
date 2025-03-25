package br.com.fiap.hackaton.controller.infrastructure.gateway

import br.com.fiap.hackaton.controller.core.domain.Upload
import br.com.fiap.hackaton.controller.core.gateway.VideoStorageGateway
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Component
class FileStorageGatewayS3(
    private val s3Client: S3Client,
    @Value("\${aws.s3.bucket}")
    private val bucketName: String
): VideoStorageGateway {
    override fun writeAll(upload: Upload) {
        upload.videos.forEach{
            val fileSize = it.byteArrayInputStream?.available()?.toLong() ?: 0
            val putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(upload.user.cpf)
                .contentType(it.contentType)
                .tagging("cpf=${upload.user.cpf},uploadId=${upload.id}")
                .contentLength(fileSize)
                .build()
            it.byteArrayInputStream?.reset()
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(it.byteArrayInputStream, fileSize))
            it.byteArrayInputStream?.close()
        }
    }
}