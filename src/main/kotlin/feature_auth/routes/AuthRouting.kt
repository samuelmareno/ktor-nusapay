package payment2go.co.id.feature_auth.routes

import feature_auth.routes.dro.UserDro
import feature_auth.routes.dro.UserDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import payment2go.co.id.core.GenericResponse

fun Application.configureAuthRouting(client: HttpClient) {
    val baseUrl = environment.config.property("ktor.client.baseUrl").getString()
    routing {
        post("/api/v1/oauth/token") {
            val user = call.receiveNullable<UserDro>()
            if (user == null) {
                call.respond(HttpStatusCode.Forbidden, "Invalid request body")
                return@post
            }
            val response: GenericResponse<UserDto> = client.post("$baseUrl/api/v1/oauth/token") {
                contentType(ContentType.Application.Json)
                setBody(user)
            }.body()
            call.respond(HttpStatusCode.OK, response)
        }
    }
}