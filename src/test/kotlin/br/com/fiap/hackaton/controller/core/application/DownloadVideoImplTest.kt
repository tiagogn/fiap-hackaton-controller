package br.com.fiap.hackaton.controller.core.application

import br.com.fiap.hackaton.controller.core.domain.Video
import br.com.fiap.hackaton.controller.core.dto.VideoBytesOutput
import br.com.fiap.hackaton.controller.core.exception.VideoNotFoundException
import br.com.fiap.hackaton.controller.core.gateway.VideoStorageGateway
import br.com.fiap.hackaton.controller.core.persistence.UploadRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DownloadVideoImplTest {

    private lateinit var uploadRepository: UploadRepository
    private lateinit var videoStorageGateway: VideoStorageGateway
    private lateinit var downloadVideo: DownloadVideoImpl

    @BeforeEach
    fun setup() {
        uploadRepository = mockk()
        videoStorageGateway = mockk()
        downloadVideo = DownloadVideoImpl(uploadRepository, videoStorageGateway)
    }

    @Test
    fun `should return video bytes when video is found`() {
        val cpf = "12345678900"
        val videoId = UUID.randomUUID()
        val videoBytes = "video-content".toByteArray()

        val video = mockk<Video>(relaxed = true)
        every { video.zipFileName } returns "example.mp4"
        every { video.contentType } returns "video/mp4"
        every { video.byteArrayInputStream } returns ByteArrayInputStream(videoBytes)

        every { uploadRepository.findVideoByVideoId(videoId) } returns video
        every { videoStorageGateway.readAllBytes(video) } just Runs

        val result: VideoBytesOutput = downloadVideo.execute(cpf, videoId)

        assertEquals("example.mp4", result.name)
        assertEquals("video/mp4", result.contentType)
        assertEquals(videoBytes.toList(), result.bytes.toList())

        verify(exactly = 1) { uploadRepository.findVideoByVideoId(videoId) }
        verify(exactly = 1) { videoStorageGateway.readAllBytes(video) }
    }

    @Test
    fun `should throw VideoNotFoundException when video is not found`() {
        val cpf = "12345678900"
        val videoId = UUID.randomUUID()

        every { uploadRepository.findVideoByVideoId(videoId) } returns null

        val exception = assertFailsWith<VideoNotFoundException> {
            downloadVideo.execute(cpf, videoId)
        }

        assertEquals("Video $videoId not found", exception.message)
        verify(exactly = 1) { uploadRepository.findVideoByVideoId(videoId) }
        verify(exactly = 0) { videoStorageGateway.readAllBytes(any()) }
    }

    @Test
    fun `should throw VideoNotFoundException when byteArrayInputStream is null`() {
        val cpf = "12345678900"
        val videoId = UUID.randomUUID()

        val video = mockk<Video>(relaxed = true)
        every { video.byteArrayInputStream } returns null

        every { uploadRepository.findVideoByVideoId(videoId) } returns video
        every { videoStorageGateway.readAllBytes(video) } just Runs

        val exception = assertFailsWith<VideoNotFoundException> {
            downloadVideo.execute(cpf, videoId)
        }

        assertEquals("Video $videoId not found", exception.message)
        verify(exactly = 1) { videoStorageGateway.readAllBytes(video) }
    }
}
