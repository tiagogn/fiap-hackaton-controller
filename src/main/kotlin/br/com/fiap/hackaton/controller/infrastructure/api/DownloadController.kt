package br.com.fiap.hackaton.controller.infrastructure.api

import br.com.fiap.hackaton.controller.core.application.DownloadVideo
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import java.util.UUID

@Controller
@RequestMapping("/v1/download")
class DownloadController(
    private val downloadVideo: DownloadVideo
) {

    @GetMapping("/{videoId}")
    fun download(@RequestHeader(required = true) cpf: String, @PathVariable(required = true) videoId: UUID): ResponseEntity<ByteArray> {

        val videoBytesOutput = downloadVideo.execute(cpf, videoId)

        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=${videoBytesOutput.name}")
            .header("Content-Type", videoBytesOutput.contentType)
            .body(videoBytesOutput.bytes)
    }
}