package br.com.fiap.hackaton.controller.infrastructure.gateway

import br.com.fiap.hackaton.controller.core.domain.Upload
import br.com.fiap.hackaton.controller.core.domain.Video
import br.com.fiap.hackaton.controller.core.gateway.VideoStorageGateway
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.ResponseBytes
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectResponse
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Component
class FileStorageGatewayS3(
    private val s3Client: S3Client,
    @Value("\${aws.s3.bucket}")
    private val bucketName: String
): VideoStorageGateway {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun writeAllBytes(upload: Upload) {
        upload.videos.forEach{
            val fileSize = it.byteArrayInputStream?.available()?.toLong() ?: 0
            val putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("${upload.id}/${it.name}")
                .contentType(it.contentType)
                .contentLength(fileSize)
                .build()
            it.byteArrayInputStream?.reset()
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(it.byteArrayInputStream, fileSize))
            it.byteArrayInputStream?.close()
        }
    }

    override fun readAllBytes(video: Video) {
        val key = "${video.uploadId}/${video.zipFileName}"
        video.byteArrayInputStream = downloadFile(key).inputStream()
    }

    private fun downloadFile(s3Key: String): ByteArray {
        return try {
            val getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build()

            val objectBytes: ResponseBytes<GetObjectResponse>? = s3Client.getObjectAsBytes(getObjectRequest)

            objectBytes?.asByteArray() ?: byteArrayOf()
        } catch (e: Exception) {
            logger.error(e.message)
            byteArrayOf()
        }
    }

}