package br.com.fiap.hackaton.controller.infrastructure.api

import br.com.fiap.hackaton.controller.core.application.ListVideo
import br.com.fiap.hackaton.controller.core.dto.VideoOutput
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/list")
class ListController(
    private val listVideo: ListVideo
) {
    @GetMapping
    fun list(@RequestHeader(required = true) cpf: String): ResponseEntity<List<VideoOutput>> {

        val listVideoOutput = listVideo.execute(cpf)

        return if (listVideoOutput.isNotEmpty()) {
            ResponseEntity.ok(listVideoOutput)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}