package br.com.fiap.hackaton.controller.core.application

import br.com.fiap.hackaton.controller.core.dto.VideoOutput
import br.com.fiap.hackaton.controller.core.exception.UserNotFoundException
import br.com.fiap.hackaton.controller.core.persistence.UploadRepository
import br.com.fiap.hackaton.controller.core.persistence.UserRepository

class ListVideoImpl(
    private val userRepository: UserRepository,
    private val uploadRepository: UploadRepository,
): ListVideo {
    override fun execute(cpf: String): List<VideoOutput> {

        val user = userRepository.findByCpf(cpf) ?: throw UserNotFoundException("User $cpf not found")

        val uploads = uploadRepository.findByUser(user)

        return VideoOutput.toListVideoOutputList(uploads)
    }
}