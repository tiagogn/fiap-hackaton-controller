package br.com.fiap.hackaton.controller.infrastructure.api

import br.com.fiap.hackaton.controller.core.application.DownloadVideo
import br.com.fiap.hackaton.controller.core.dto.VideoBytesOutput
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.UUID

@WebMvcTest(DownloadController::class)
class DownloadControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var downloadVideo: DownloadVideo

    @Test
    fun `should download video successfully`() {
        val videoId = UUID.randomUUID()
        val cpf = "12345678900"
        val fileName = "video.mp4"
        val contentType = "video/mp4"
        val fileBytes = byteArrayOf(1, 2, 3)

        val videoOutput = VideoBytesOutput(
            name = fileName,
            contentType = contentType,
            bytes = fileBytes
        )

        every { downloadVideo.execute(cpf, videoId) } returns videoOutput

        mockMvc.perform(
            get("/v1/download/$videoId")
                .header("cpf", cpf)
        )
            .andExpect(status().isOk)
            .andExpect(header().string("Content-Disposition", "attachment; filename=$fileName"))
            .andExpect(header().string("Content-Type", contentType))
            .andExpect(content().bytes(fileBytes))

        verify(exactly = 1) { downloadVideo.execute(cpf, videoId) }
    }
}
