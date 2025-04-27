package br.com.fiap.hackaton.controller.performance

import io.gatling.javaapi.core.*
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.http.HttpDsl.*

import java.time.Duration

class VideoUploadSimulation : Simulation() {

    private val httpProtocol = http
        .baseUrl("http://localhost:8080/controller")
        .userAgentHeader("Gatling/Performance Test")

    private val scn = scenario("Video Upload Test")
        .exec(
            http("Upload Video")
                .post("/v1/upload")
                .header("Content-Type", "multipart/form-data")
                .header("cpf", "12345678901")
                .formUpload("files", "test-video.mp4")
                .check(status().`is`(200))
        )
        .pause(Duration.ofSeconds(1))
        .exec(
            http("List Videos")
                .get("/v1/list")
                .header("cpf", "12345678901")
                .check(status().`is`(200))
        )

    init {
        setUp(
            scn.injectOpen(
                rampUsers(10).during(Duration.ofSeconds(5)),
                constantUsersPerSec(2.0).during(Duration.ofSeconds(10)),
                rampUsers(20).during(Duration.ofSeconds(5))
            )
        ).protocols(httpProtocol)
    }
} 