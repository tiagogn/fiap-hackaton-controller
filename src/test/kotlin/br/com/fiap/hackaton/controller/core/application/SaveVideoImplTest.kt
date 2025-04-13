package br.com.fiap.hackaton.controller.core.application

import br.com.fiap.hackaton.controller.core.domain.User
import br.com.fiap.hackaton.controller.core.dto.SaveVideoInput
import br.com.fiap.hackaton.controller.core.exception.UserNotFoundException
import br.com.fiap.hackaton.controller.core.gateway.SendVideoMessageGateway
import br.com.fiap.hackaton.controller.core.gateway.VideoStorageGateway
import br.com.fiap.hackaton.controller.core.persistence.UploadRepository
import br.com.fiap.hackaton.controller.core.persistence.UserRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import java.io.InputStream
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SaveVideoImplTest {

    private val userRepository: UserRepository = mockk()
    private val uploadRepository: UploadRepository = mockk()
    private val videoStorageGateway: VideoStorageGateway = mockk()
    private val sendVideoMessageGateway: SendVideoMessageGateway = mockk()
    private lateinit var saveVideo: SaveVideoImpl

    @BeforeEach
    fun setUp() {
        saveVideo = SaveVideoImpl(userRepository, uploadRepository, videoStorageGateway, sendVideoMessageGateway)
    }

    @Test
    fun `should save video when user is found`() {
        val cpf = "12345678900"
        val user = User(id = UUID.randomUUID(), cpf = cpf, name = "Test User", email = "test@email.com")
        val input = SaveVideoInput(
            name = "video.mp4",
            size = 1024,
            contentType = "video/mp4",
            bytes = ByteArray(10),
            inputStream = mockk<InputStream>()
        )

        every { userRepository.findByCpf(cpf) } returns user
        every { videoStorageGateway.writeAllBytes(any()) } just runs
        every { uploadRepository.save(any()) } returnsArgument 0
        every { sendVideoMessageGateway.send(any()) } just runs

        saveVideo.execute(cpf, listOf(input))

        verify { userRepository.findByCpf(cpf) }
        verify { videoStorageGateway.writeAllBytes(any()) }
        verify { uploadRepository.save(any()) }
        verify { sendVideoMessageGateway.send(any()) }
    }

    @Test
    fun `should throw UserNotFoundException when user not found`() {
        val cpf = "00000000000"
        every { userRepository.findByCpf(cpf) } returns null

        assertThrows(UserNotFoundException::class.java) {
            saveVideo.execute(cpf, emptyList())
        }

        verify { userRepository.findByCpf(cpf) }
    }
}
