package br.com.fiap.hackaton.controller.infrastructure.api

import br.com.fiap.hackaton.controller.core.application.ListVideo
import br.com.fiap.hackaton.controller.core.dto.VideoOutput
import br.com.fiap.hackaton.controller.core.dto.VideoOutputDetail
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDateTime

@WebMvcTest(ListController::class)
class ListControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var listVideo: ListVideo

    @Test
    fun `should return video list successfully`() {
        val cpf = "12345678900"
        val now = LocalDateTime.now()

        val videos = listOf(
            VideoOutput(
                videoOutputDetail = listOf(
                    VideoOutputDetail(
                        name = "video1.mp4",
                        size = 2048,
                        contentType = "video/mp4",
                        uri = "https://meusvideos.com/video1",
                        uploadDdate = now,
                        status = "PROCESSED"
                    )
                ),
                createdAt = "2023-10-01T10:00:00",
            ),
            VideoOutput(
                videoOutputDetail = listOf(
                    VideoOutputDetail(
                        name = "video2.mp4",
                        size = 1024,
                        contentType = "video/mp4",
                        uri = "https://meusvideos.com/video2",
                        uploadDdate = now,
                        status = "PROCESSED"
                    )
                ),
                createdAt = "2023-10-02T10:00:00",
            )
        )

        every { listVideo.execute(cpf) } returns videos

        mockMvc.get("/v1/list") {
            header("cpf", cpf)
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$.size()") { value(2) }
            jsonPath("$[0].videoOutputDetail[0].name") { value("video1.mp4") }
            jsonPath("$[1].videoOutputDetail[0].name") { value("video2.mp4") }
        }
    }

    @Test
    fun `should return not found when there are no videos`() {
        val cpf = "00000000000"

        every { listVideo.execute(cpf) } returns emptyList()

        mockMvc.get("/v1/list") {
            header("cpf", cpf)
        }.andExpect {
            status { isNotFound() }
        }
    }
}
