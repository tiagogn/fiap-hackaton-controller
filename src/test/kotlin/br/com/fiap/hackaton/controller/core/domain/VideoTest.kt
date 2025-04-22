package br.com.fiap.hackaton.controller.core.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.util.UUID

class VideoTest {

    @Test
    fun `should create Video with correct attributes`() {
        val id = UUID.randomUUID()
        val name = "video.mp4"
        val size = 2048
        val contentType = "video/mp4"
        val byteArray = byteArrayOf(1, 2, 3, 4)
        val inputStream = ByteArrayInputStream(byteArray)
        val zipFileName = "video.zip"
        val uploadId = "upload-123"

        val video = Video(
            id = id,
            name = name,
            size = size,
            contentType = contentType,
            byteArrayInputStream = inputStream,
            status = StatusVideo.PROCESSED,
            zipFileName = zipFileName,
            uploadId = uploadId
        )

        assertEquals(id, video.id)
        assertEquals(name, video.name)
        assertEquals(size, video.size)
        assertEquals(contentType, video.contentType)
        assertEquals(inputStream, video.byteArrayInputStream)
        assertEquals(StatusVideo.PROCESSED, video.status)
        assertEquals(zipFileName, video.zipFileName)
        assertEquals(uploadId, video.uploadId)
    }

    @Test
    fun `should create Video with default values`() {
        val video = Video(
            name = "default.mp4",
            size = 1024,
            contentType = "video/mp4"
        )

        assertNotNull(video.id)
        assertEquals("default.mp4", video.name)
        assertEquals(1024, video.size)
        assertEquals("video/mp4", video.contentType)
        assertNull(video.byteArrayInputStream)
        assertEquals(StatusVideo.PENDING, video.status)
        assertNull(video.zipFileName)
        assertNull(video.uploadId)
    }
}
