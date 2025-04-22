package br.com.fiap.hackaton.controller.infrastructure.gateway

import br.com.fiap.hackaton.controller.core.domain.Upload
import br.com.fiap.hackaton.controller.core.domain.User
import br.com.fiap.hackaton.controller.core.domain.Video
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import software.amazon.awssdk.core.ResponseBytes
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectResponse
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectResponse
import java.io.ByteArrayInputStream
import java.lang.reflect.Method
import java.util.*

class FileStorageGatewayS3Test {

    private lateinit var s3Client: S3Client
    private lateinit var gateway: FileStorageGatewayS3
    private val bucketName = "test-bucket"

    @BeforeEach
    fun setUp() {
        s3Client = mockk(relaxed = true)
        gateway = FileStorageGatewayS3(s3Client, bucketName)
    }

    @Test
    fun `should write all bytes to S3`() {
        val bytes = "video-data".toByteArray()
        val inputStream = ByteArrayInputStream(bytes)
        val video = Video(
            name = "video.mp4",
            size = bytes.size,
            contentType = "video/mp4",
            byteArrayInputStream = inputStream
        )
        val user = User(UUID.randomUUID(), "Test", "test@email.com", "12345678900")
        val upload = Upload(user = user, videos = listOf(video))

        every {
            s3Client.putObject(any<PutObjectRequest>(), any<RequestBody>())
        } returns mockk<PutObjectResponse>()

        gateway.writeAllBytes(upload)

        verify(exactly = 1) {
            s3Client.putObject(any<PutObjectRequest>(), any<RequestBody>())
        }
    }

    @Test
    fun `should write all bytes to S3 with zero bytes`() {
        val inputStream = null
        val video = Video(
            name = "video.mp4",
            size = 0,
            contentType = "video/mp4",
            byteArrayInputStream = inputStream
        )
        val user = User(UUID.randomUUID(), "Test", "test@email.com", "12345678900")
        val upload = Upload(user = user, videos = listOf(video))

        every {
            s3Client.putObject(any<PutObjectRequest>(), any<RequestBody>())
        } returns mockk<PutObjectResponse>()

        gateway.writeAllBytes(upload)

        verify(exactly = 0) {
            s3Client.putObject(any<PutObjectRequest>(), any<RequestBody>())
        }
    }

    @Test
    fun `should read all bytes from S3`() {
        val video = Video(
            name = "video.mp4",
            size = 100,
            contentType = "video/mp4",
            byteArrayInputStream = null
        )

        val responseBytes: ResponseBytes<GetObjectResponse> = mockk {
            every { asByteArray() } returns "video-data".toByteArray()
        }

        every { s3Client.getObjectAsBytes(any<GetObjectRequest>()) } returns responseBytes

        gateway.readAllBytes(video)

        assertNotNull(video.byteArrayInputStream)
        assertArrayEquals("video-data".toByteArray(), video.byteArrayInputStream?.readBytes())
    }

    @Test
    fun `downloadFile should return empty array when exception is thrown`() {
        every { s3Client.getObjectAsBytes(any<GetObjectRequest>()) } throws RuntimeException("Download failed")

        val downloadFileMethod: Method = gateway.javaClass.getDeclaredMethod("downloadFile", String::class.java)
        downloadFileMethod.isAccessible = true
        val result = downloadFileMethod.invoke(gateway, "invalid-key") as ByteArray

        assertArrayEquals(byteArrayOf(), result)
    }

    @Test
    fun `downloadFile should return empty array when response is null`() {
        every { s3Client.getObjectAsBytes(any<GetObjectRequest>()) } returns null

        val downloadFileMethod: Method = gateway.javaClass.getDeclaredMethod("downloadFile", String::class.java)
        downloadFileMethod.isAccessible = true
        val result = downloadFileMethod.invoke(gateway, "invalid-key") as ByteArray

        assertArrayEquals(byteArrayOf(), result)
    }
}
