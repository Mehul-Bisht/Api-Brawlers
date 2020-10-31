package com.example.mehulapi

import RandomClass
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.StatusPages
import io.ktor.jackson.jackson
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import users
import java.lang.Compiler.enable

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    //You re still missing some stuff like the port and other things
    val port = System.getenv("PORT")?.toInt() ?: 8080

    embeddedServer(Netty,port) {
        install(StatusPages) {
            exception<Throwable> { error ->
                print("Exception Occured ${error.localizedMessage}")
            }
        }

        install(io.ktor.features.ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }

        routing {
            get("/userDetails") {
                var randomClass = RandomClass("John","Carlos",30)
                call.respond(randomClass)
            }
        }


    }.start(wait = true)

}

