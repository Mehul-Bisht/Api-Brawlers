package com.example.mehulapi

import Brawler
import BrawlerDetail
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.StatusPages
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    val port = System.getenv("PORT")?.toInt() ?: 8080

    var brawlerChromatic : Brawler? = null

    embeddedServer(Netty,port) {
        install(StatusPages) {
            exception<Throwable> { error ->
                print("Exception occurred ${error.localizedMessage}")
            }
        }

        install(io.ktor.features.ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }

        val masterList = ArrayList<Brawler>()
        BrawlerData.legendaryList.forEach {
            masterList.add(it) }
        BrawlerData.chromaticList.forEach {
            masterList.add(it) }
        BrawlerData.mythicList.forEach {
            masterList.add(it) }
        BrawlerData.epicList.forEach {
            masterList.add(it) }
        BrawlerData.superRareList.forEach {
            masterList.add(it) }
        BrawlerData.rareList.forEach {
            masterList.add(it) }
        BrawlerData.trophyRoadList.forEach {
            masterList.add(it) }
        masterList.add(BrawlerData.shelly)


        val masterListPro = ArrayList<BrawlerDetail>()
        BrawlerDetailData.legendaryListDetail.forEach {
            masterListPro.add(it) }
        BrawlerDetailData.chromaticListDetail.forEach {
            masterListPro.add(it) }
        BrawlerDetailData.mythicListDetail.forEach {
            masterListPro.add(it) }
        BrawlerDetailData.epicListDetail.forEach {
            masterListPro.add(it) }
        BrawlerDetailData.superRareListDetail.forEach {
            masterListPro.add(it) }
        BrawlerDetailData.rareListDetail.forEach {
            masterListPro.add(it) }
        BrawlerDetailData.trophyRoadListDetail.forEach {
            masterListPro.add(it) }
        masterListPro.add(BrawlerDetailData.Shelly)

        routing {
            get("/allBrawlers") {
                call.respond(masterList)
            }

            get("/legendary") {
                call.respond(BrawlerData.legendaryList)
            }

            get("/chromatic") {
                call.respond(BrawlerData.chromaticList)
            }

            get("/mythic") {
                call.respond(BrawlerData.mythicList)
            }

            get("/epic") {
                call.respond(BrawlerData.epicList)
            }

            get("/superRare") {
                call.respond(BrawlerData.superRareList)
            }

            get("/rare") {
                call.respond(BrawlerData.rareList)
            }

            get("/trophyRoad") {
                call.respond(BrawlerData.trophyRoadList)
            }

            for (brawler in masterList){
                if(brawler.name != "8-Bit"){
                get("/${brawler.name}") {
                    call.respond(masterListPro.get(masterList.indexOf(brawler)))
                }
                }
                else{
                    get("/eightBit") {
                        call.respond(masterListPro.get(masterList.indexOf(BrawlerData.eightBit)))
                    }
                }
            }

            put("/chromatic") {
                brawlerChromatic = call.receive<Brawler>()
                brawlerChromatic?.let {
                    val tempList = BrawlerData.chromaticList.toMutableList()
                    tempList.add(brawlerChromatic!!)
                    tempList.sortBy {
                        it.name
                    }
                    BrawlerData.chromaticList = tempList
                }
            }

        }


    }.start(wait = true)

}

