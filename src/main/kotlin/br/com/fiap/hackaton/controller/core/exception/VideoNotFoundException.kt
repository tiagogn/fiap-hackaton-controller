package br.com.fiap.hackaton.controller.core.exception

class VideoNotFoundException: RuntimeException {
    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable): super(message, cause)
}