package br.com.fiap.hackaton.controller.infrastructure.api

import br.com.fiap.hackaton.controller.core.exception.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorController {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(exception: UserNotFoundException): ProblemDetail {
        val problemDetail: ProblemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            exception.message
        )
        return problemDetail
    }
}