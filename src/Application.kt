package com.example.mehulapi

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.routing
import users

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    routing {
        this.users()
    }

}

