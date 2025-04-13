package br.com.fiap.hackaton.controller.core.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

class UploadTest {

    @Test
    fun `should create Upload with correct attributes`() {
        val user = User(
            id = UUID.randomUUID(),
            cpf = "12345678900",
            name = "John Doe",
            email = "test@email.com"
        )

        val video1 = Video(
            id = UUID.randomUUID(),
            name = "video1.mp4",
            size = 1024,
            contentType = "video/mp4",
            status = StatusVideo.PROCESSED,
            zipFileName = "video1.zip",
            uploadId = "upload-123"
        )

        val video2 = Video(
            id = UUID.randomUUID(),
            name = "video2.mp4",
            size = 2048,
            contentType = "video/mp4",
            status = StatusVideo.PENDING,
            zipFileName = "video2.zip",
            uploadId = "upload-123"
        )

        val creationTimeBefore = LocalDateTime.now()

        val upload = Upload(
            videos = listOf(video1, video2),
            user = user
        )

        val creationTimeAfter = LocalDateTime.now()

        assertNotNull(upload.id)
        assertEquals(user, upload.user)
        assertEquals(2, upload.videos.size)
        assertEquals(video1, upload.videos[0])
        assertEquals(video2, upload.videos[1])
        assert(upload.creationDate.isAfter(creationTimeBefore) || upload.creationDate.isEqual(creationTimeBefore))
        assert(upload.creationDate.isBefore(creationTimeAfter) || upload.creationDate.isEqual(creationTimeAfter))
    }
}
