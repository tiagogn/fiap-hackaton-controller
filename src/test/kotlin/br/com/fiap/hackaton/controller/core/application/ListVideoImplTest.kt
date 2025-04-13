package br.com.fiap.hackaton.controller.core.application

import br.com.fiap.hackaton.controller.core.domain.StatusVideo
import br.com.fiap.hackaton.controller.core.domain.Upload
import br.com.fiap.hackaton.controller.core.domain.User
import br.com.fiap.hackaton.controller.core.domain.Video
import br.com.fiap.hackaton.controller.core.exception.UserNotFoundException
import br.com.fiap.hackaton.controller.core.persistence.UploadRepository
import br.com.fiap.hackaton.controller.core.persistence.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.UUID

class ListVideoImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var uploadRepository: UploadRepository
    private lateinit var listVideo: ListVideoImpl

    private val downloadUrl = "https://meu-download-url.com/"

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        uploadRepository = mockk()
        listVideo = ListVideoImpl(userRepository, uploadRepository, downloadUrl)
    }

    @Test
    fun `should return video output list with uri when video is processed`() {
        val cpf = "12345678900"
        val user = User(
            id = UUID.randomUUID(),
            cpf = cpf,
            name = "Test User",
            email = "test@email.com"
        )

        val now = LocalDateTime.now()

        val video1 = Video(
            id = UUID.randomUUID(),
            name = "video1.mp4",
            size = 1024,
            contentType = "video/mp4",
            status = StatusVideo.PROCESSED,
            zipFileName = "video1.zip",
            uploadId = "upload123"
        )

        val video2 = Video(
            id = UUID.randomUUID(),
            name = "video2.mp4",
            size = 2048,
            contentType = "video/mp4",
            status = StatusVideo.PENDING,
            zipFileName = "video2.zip",
            uploadId = "upload123"
        )

        val upload = Upload(
            id = UUID.randomUUID(),
            user = user,
            videos = listOf(video1, video2),
            creationDate = now
        )

        every { userRepository.findByCpf(cpf) } returns user
        every { uploadRepository.findByUser(user) } returns listOf(upload)

        val result = listVideo.execute(cpf)

        assertEquals(1, result.size)
        val videoOutput = result[0]

        assertEquals(now.toString(), videoOutput.createdAt)
        assertEquals(2, videoOutput.videoOutputDetail.size)

        val detail1 = videoOutput.videoOutputDetail[0]
        assertEquals("video1.mp4", detail1.name)
        assertEquals(1024, detail1.size)
        assertEquals("video/mp4", detail1.contentType)
        assertEquals("https://meu-download-url.com/video1", detail1.uri)
        assertEquals(now, detail1.uploadDdate)
        assertEquals("PROCESSED", detail1.status)

        val detail2 = videoOutput.videoOutputDetail[1]
        assertEquals("video2.mp4", detail2.name)
        assertEquals(2048, detail2.size)
        assertEquals("video/mp4", detail2.contentType)
        assertEquals("", detail2.uri)
        assertEquals(now, detail2.uploadDdate)
        assertEquals("PENDING", detail2.status)
    }

    @Test
    fun `should throw UserNotFoundException when user is not found`() {
        val cpf = "00000000000"

        every { userRepository.findByCpf(cpf) } returns null

        val exception = assertThrows(UserNotFoundException::class.java) {
            listVideo.execute(cpf)
        }

        assertEquals("User $cpf not found", exception.message)
    }
}
