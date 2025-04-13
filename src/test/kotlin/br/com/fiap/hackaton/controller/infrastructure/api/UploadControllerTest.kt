package br.com.fiap.hackaton.controller.infrastructure.api

import br.com.fiap.hackaton.controller.core.application.SaveVideo
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.multipart

@WebMvcTest(UploadController::class)
class UploadControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var saveVideo: SaveVideo

    @Test
    fun `should upload multiple video files successfully`() {
        val cpf = "12345678900"
        val file1 = MockMultipartFile("files", "video1.mp4", "video/mp4", "file content 1".toByteArray())
        val file2 = MockMultipartFile("files", "video2.mp4", "video/mp4", "file content 2".toByteArray())

        every { saveVideo.execute(any(), any()) } returns Unit

        mockMvc.multipart("/v1/upload") {
            file(file1)
            file(file2)
            header("cpf", cpf)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `should return bad request when no files are provided`() {
        val cpf = "12345678900"

        mockMvc.multipart("/v1/upload") {
            header("cpf", cpf)
        }.andExpect {
            status { isBadRequest() }
        }
    }


    @Test
    fun `should return bad request when CPF header is missing`() {
        val file = MockMultipartFile("files", "video.mp4", "video/mp4", "file content".toByteArray())

        mockMvc.multipart("/v1/upload") {
            file(file)
        }.andExpect {
            status { isBadRequest() }
        }
    }
}
