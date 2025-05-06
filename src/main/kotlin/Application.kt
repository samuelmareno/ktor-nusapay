package payment2go.co.id

import Version
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import payment2go.co.id.feature_auth.routes.configureAuthRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    routing {
        get("/version") {
            //get version of app from gradle
            call.respond(HttpStatusCode.OK, Version.APP_VERSION)
        }
    }

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        install(Logging) {
            level = LogLevel.BODY
            logger = Logger.DEFAULT
        }
    }

    configureSerialization()
    configureAuthRouting(client)
}
