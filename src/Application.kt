package com.example.mehulapi

import Brawler
import BrawlerDetail
import Numbers
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
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
                val map = mutableMapOf<String,Any>()
                map["title"] = "All Brawlers "
                map["description"] = "Brawlers of all rarities"
                map["brawlerCount"] = masterList.size
                map["brawlers"] = masterList
                call.respond(map)
            }

            get("/legendary") {
                val map = mutableMapOf<String,Any>()
                map["title"] = "Legendary Brawlers "
                map["description"] = "Brawlers of legendary rarity"
                map["brawlerCount"] = BrawlerData.legendaryList.size
                map["brawlers"] = BrawlerData.legendaryList
                call.respond(map)
            }

            get("/chromatic") {
                val map = mutableMapOf<String,Any>()
                map["title"] = "Chromatic Brawlers "
                map["description"] = "Brawlers of chromatic rarity"
                map["brawlerCount"] = BrawlerData.chromaticList.size
                map["brawlers"] = BrawlerData.chromaticList
                call.respond(map)
            }

            get("/mythic") {
                val map = mutableMapOf<String,Any>()
                map["title"] = "Mythic Brawlers "
                map["description"] = "Brawlers of mythic rarity"
                map["brawlerCount"] = BrawlerData.mythicList.size
                map["brawlers"] = BrawlerData.mythicList
                call.respond(map)
            }

            get("/epic") {
                val map = mutableMapOf<String,Any>()
                map["title"] = "Epic Brawlers "
                map["description"] = "Brawlers of epic rarity"
                map["brawlerCount"] = BrawlerData.epicList.size
                map["brawlers"] = BrawlerData.epicList
                call.respond(map)
            }

            get("/superRare") {
                val map = mutableMapOf<String,Any>()
                map["title"] = "Super-Rare Brawlers "
                map["description"] = "Brawlers of Super-Rare rarity"
                map["brawlerCount"] = BrawlerData.superRareList.size
                map["brawlers"] = BrawlerData.superRareList
                call.respond(map)
            }

            get("/rare") {
                val map = mutableMapOf<String,Any>()
                map["title"] = "Rare Brawlers "
                map["description"] = "Brawlers of Rare rarity"
                map["brawlerCount"] = BrawlerData.rareList.size
                map["brawlers"] = BrawlerData.rareList
                call.respond(map)
            }

            get("/trophyRoad") {
                val map = mutableMapOf<String,Any>()
                map["title"] = "Trophy Road Brawlers "
                map["description"] = "Brawlers of Trophy Road rarity"
                map["brawlerCount"] = BrawlerData.trophyRoadList.size
                map["brawlers"] = BrawlerData.trophyRoadList
                call.respond(map)
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
                    call.respond(HttpStatusCode.OK)
                }
            }

            get("/numbers") {
                val one = Numbers(1,"one");
                val two = Numbers(2,"two");
                val three = Numbers(3,"three");
                val four = Numbers(4,"four");
                val five = Numbers(5,"five");
                val six = Numbers(6,"six");
                val subList = listOf(one,two,three,four,five,six);
                val itemCount = subList.size;

                val map = mutableMapOf<String,Any>()
                map["title"] = "numbers"
                map["description"] = "knowledge about numbers :)"
                map["totalItems"] = itemCount as Integer
                map["items"] = subList
                call.respond(map)
            }
        }


    }.start(wait = true)

}

