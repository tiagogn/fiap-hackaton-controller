package br.com.fiap.hackaton.controller.infrastructure.api

import br.com.fiap.hackaton.controller.core.application.SaveVideo
import br.com.fiap.hackaton.controller.core.dto.SaveVideoInput
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/v1/upload")
class UploadController(
    private val salvarVideo: SaveVideo
) {

    @PostMapping
    fun upload(@RequestHeader(name = "cpf", required = true) cpf: String,
               @RequestParam( name = "files", required = true ) files: List<MultipartFile>): ResponseEntity<Unit> {

        val salvarVideoInputList = files.map { file ->
                SaveVideoInput(
                    name = file.originalFilename!!,
                    size = file.size.toInt(),
                    contentType = file.contentType!!,
                    bytes = file.bytes,
                    inputStream = file.inputStream
                )
        }

        salvarVideo.execute(cpf, salvarVideoInputList)
        return ResponseEntity.ok().build()
    }
}