package br.com.fiap.hackaton.controller.core.dto

import br.com.fiap.hackaton.controller.core.domain.StatusVideo
import br.com.fiap.hackaton.controller.core.domain.Upload
import br.com.fiap.hackaton.controller.core.domain.User
import br.com.fiap.hackaton.controller.core.domain.Video
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID
import kotlin.test.assertEquals

class VideoOutputTest {

    @Test
    fun `should convert single upload to VideoOutput with processed video`() {
        val uploadDate = LocalDateTime.now()
        val video = Video(
            id = UUID.randomUUID(),
            name = "video1.mp4",
            size = 1024,
            contentType = "video/mp4",
            status = StatusVideo.PROCESSED,
            zipFileName = "video1.zip"
        )

        val user = User(UUID.randomUUID(), "Test User", "test@email.com", "12345678900")
        val upload = Upload(videos = listOf(video), user = user, creationDate = uploadDate)

        val downloadUrl = "https://meu-bucket.s3.amazonaws.com/"

        val result = VideoOutput.toListVideoOutputList(upload, downloadUrl)

        assertEquals(1, result.videoOutputDetail.size)
        val detail = result.videoOutputDetail[0]

        assertEquals("video1.mp4", detail.name)
        assertEquals(1024, detail.size)
        assertEquals("video/mp4", detail.contentType)
        assertEquals("https://meu-bucket.s3.amazonaws.com/video1", detail.uri)
        assertEquals(uploadDate, detail.uploadDdate)
        assertEquals("PROCESSED", detail.status)
    }

    @Test
    fun `should convert single upload with non-processed video and return empty uri`() {
        val uploadDate = LocalDateTime.now()
        val video = Video(
            name = "video2.mp4",
            size = 2048,
            contentType = "video/mp4",
            status = StatusVideo.PENDING
        )

        val user = User(UUID.randomUUID(), "Test User", "test@email.com", "98765432100")
        val upload = Upload(videos = listOf(video), user = user, creationDate = uploadDate)

        val result = VideoOutput.toListVideoOutputList(upload, "https://meu-bucket/")

        assertEquals(1, result.videoOutputDetail.size)
        val detail = result.videoOutputDetail[0]

        assertEquals("video2.mp4", detail.name)
        assertEquals(2048, detail.size)
        assertEquals("video/mp4", detail.contentType)
        assertEquals("", detail.uri)
        assertEquals(uploadDate, detail.uploadDdate)
        assertEquals("PENDING", detail.status)
    }

    @Test
    fun `should convert list of uploads to list of VideoOutput`() {
        val video1 = Video(
            name = "video1.mp4",
            size = 1000,
            contentType = "video/mp4",
            status = StatusVideo.PROCESSED,
            zipFileName = "video1.zip"
        )

        val video2 = Video(
            name = "video2.mp4",
            size = 2000,
            contentType = "video/mp4",
            status = StatusVideo.ERROR
        )

        val user = User(UUID.randomUUID(), "Test User", "test@email.com", "00000000000")
        val uploadDate = LocalDateTime.now()

        val upload1 = Upload(user = user, videos = listOf(video1), creationDate = uploadDate)
        val upload2 = Upload(user = user, videos = listOf(video2), creationDate = uploadDate)

        val downloadUrl = "https://cdn.exemplo/"

        val result = VideoOutput.toListVideoOutputList(listOf(upload1, upload2), downloadUrl)

        assertEquals(2, result.size)

        val detail1 = result[0].videoOutputDetail[0]
        val detail2 = result[1].videoOutputDetail[0]

        assertEquals("video1.mp4", detail1.name)
        assertEquals("https://cdn.exemplo/video1", detail1.uri)

        assertEquals("video2.mp4", detail2.name)
        assertEquals("", detail2.uri)
    }
}
