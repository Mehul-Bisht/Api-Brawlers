package com.example.mehulapi

import Brawler
import BrawlerDetail
import Numbers
import Util
import UtilRarity
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
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

    var brawlerChromatic: Brawler? = null

    embeddedServer(Netty, port) {
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
            masterList.add(it)
        }
        BrawlerData.chromaticList.forEach {
            masterList.add(it)
        }
        BrawlerData.mythicList.forEach {
            masterList.add(it)
        }
        BrawlerData.epicList.forEach {
            masterList.add(it)
        }
        BrawlerData.superRareList.forEach {
            masterList.add(it)
        }
        BrawlerData.rareList.forEach {
            masterList.add(it)
        }
        BrawlerData.trophyRoadList.forEach {
            masterList.add(it)
        }
        masterList.add(BrawlerData.shelly)


        val masterListPro = ArrayList<BrawlerDetail>()
        BrawlerDetailData.legendaryListDetail.forEach {
            masterListPro.add(it)
        }
        BrawlerDetailData.chromaticListDetail.forEach {
            masterListPro.add(it)
        }
        BrawlerDetailData.mythicListDetail.forEach {
            masterListPro.add(it)
        }
        BrawlerDetailData.epicListDetail.forEach {
            masterListPro.add(it)
        }
        BrawlerDetailData.superRareListDetail.forEach {
            masterListPro.add(it)
        }
        BrawlerDetailData.rareListDetail.forEach {
            masterListPro.add(it)
        }
        BrawlerDetailData.trophyRoadListDetail.forEach {
            masterListPro.add(it)
        }
        masterListPro.add(BrawlerDetailData.Shelly)

        routing {

            val numList = ArrayList<Int>()
            for (i in 0..99) {
                numList.add(i)
            }

            get("/test") {

                val request = call.request
                val queryParameters: Parameters = request.queryParameters
                var page = 1
                var perPage = 4

                val pageParamGet = queryParameters["page"]
                val perPageParamGet = queryParameters["per_page"]

                if(pageParamGet != null) {
                    page = Integer.parseInt(pageParamGet)
                    // page max value = total items/per_page
                }

                if(perPageParamGet != null) {
                    perPage = Integer.parseInt(perPageParamGet)
                    // perPage default max value = 10
                }

                val currentList = ArrayList<Int>()

                var startIdx = (page - 1) * perPage
                val endIdx = startIdx + perPage

                while (startIdx < endIdx) {
                    currentList.add(numList[startIdx])
                    startIdx++
                }

                val map = mutableMapOf<String,Any>()
                map["title"] = "new year title"
                map["description"] = "new year description"
                map["list"] = currentList

                call.respond(map)
                println(map)
                
            }

            get("/allBrawlers") {

                val util = Util(call,masterList)

                var startIdx = (util.page - 1) * util.perPage

                if(util.page == util.lastPage){
                    var counter = 0

                    while(counter < util.endItems){
                        util.currentList.add(masterList[startIdx + counter])
                        counter++
                    }
                } else {
                    val endIdx = startIdx + util.perPage

                    while (startIdx < endIdx) {
                        util.currentList.add(masterList[startIdx])
                        startIdx++
                    }

                }

                val map = mutableMapOf<String, Any>()
                map["title"] = "All Brawlers "
                map["description"] = "Brawlers of all rarities"
                map["brawlerCount"] = masterList.size
                map["brawlers"] = if(util.shouldRespond) util.currentList else ArrayList<Brawler>()

                call.respond(map)

            }

            get("/legendary") {

                val util = UtilRarity(call,BrawlerData.legendaryList,5)

                var startIdx = (util.page - 1) * util.perPage

                if(util.page == util.lastPage && util.page != 1){
                    var counter = 0

                    while(counter < util.endItems){
                        util.currentList.add(BrawlerData.legendaryList[startIdx + counter])
                        counter++
                    }
                } else {
                    val endIdx = startIdx + util.perPage

                    while (startIdx < endIdx) {
                        util.currentList.add(BrawlerData.legendaryList[startIdx])
                        startIdx++
                    }

                }

                val map = mutableMapOf<String, Any>()
                map["title"] = "Legendary Brawlers "
                map["description"] = "Brawlers of legendary rarity"
                map["brawlerCount"] = BrawlerData.legendaryList.size
                map["brawlers"] = util.currentList
                call.respond(map)
            }

            get("/chromatic") {

                val util = UtilRarity(call,BrawlerData.chromaticList,4)

                var startIdx = (util.page - 1) * util.perPage

                if(util.page == util.lastPage && util.page != 1){
                    var counter = 0

                    while(counter < util.endItems){
                        util.currentList.add(BrawlerData.chromaticList[startIdx + counter])
                        counter++
                    }
                } else {
                    val endIdx = startIdx + util.perPage

                    while (startIdx < endIdx) {
                        util.currentList.add(BrawlerData.chromaticList[startIdx])
                        startIdx++
                    }

                }


                val map = mutableMapOf<String, Any>()
                map["title"] = "Chromatic Brawlers "
                map["description"] = "Brawlers of chromatic rarity"
                map["brawlerCount"] = BrawlerData.chromaticList.size
                map["brawlers"] = util.currentList
                call.respond(map)
            }

            get("/mythic") {

                val util = UtilRarity(call,BrawlerData.mythicList,5)

                var startIdx = (util.page - 1) * util.perPage

                if(util.page == util.lastPage && util.page != 1){
                    var counter = 0

                    while(counter < util.endItems){
                        util.currentList.add(BrawlerData.mythicList[startIdx + counter])
                        counter++
                    }
                } else {
                    val endIdx = startIdx + util.perPage

                    while (startIdx < endIdx) {
                        util.currentList.add(BrawlerData.mythicList[startIdx])
                        startIdx++
                    }

                }

                val map = mutableMapOf<String, Any>()
                map["title"] = "Mythic Brawlers "
                map["description"] = "Brawlers of mythic rarity"
                map["brawlerCount"] = BrawlerData.mythicList.size
                map["brawlers"] = util.currentList
                call.respond(map)
            }

            get("/epic") {

                val util = UtilRarity(call,BrawlerData.epicList,5)

                var startIdx = (util.page - 1) * util.perPage

                if(util.page == util.lastPage && util.page != 1){
                    var counter = 0

                    while(counter < util.endItems){
                        util.currentList.add(BrawlerData.epicList[startIdx + counter])
                        counter++
                    }
                } else {
                    val endIdx = startIdx + util.perPage

                    while (startIdx < endIdx) {
                        util.currentList.add(BrawlerData.epicList[startIdx])
                        startIdx++
                    }

                }

                val map = mutableMapOf<String, Any>()
                map["title"] = "Epic Brawlers "
                map["description"] = "Brawlers of epic rarity"
                map["brawlerCount"] = BrawlerData.epicList.size
                map["brawlers"] = util.currentList
                call.respond(map)
            }

            get("/superRare") {

                val util = UtilRarity(call,BrawlerData.superRareList,5)

                var startIdx = (util.page - 1) * util.perPage

                if(util.page == util.lastPage && util.page != 1){
                    var counter = 0

                    while(counter < util.endItems){
                        util.currentList.add(BrawlerData.superRareList[startIdx + counter])
                        counter++
                    }
                } else {
                    val endIdx = startIdx + util.perPage

                    while (startIdx < endIdx) {
                        util.currentList.add(BrawlerData.superRareList[startIdx])
                        startIdx++
                    }

                }

                val map = mutableMapOf<String, Any>()
                map["title"] = "Super-Rare Brawlers "
                map["description"] = "Brawlers of Super-Rare rarity"
                map["brawlerCount"] = BrawlerData.superRareList.size
                map["brawlers"] = util.currentList
                call.respond(map)
            }

            get("/rare") {

                val util = UtilRarity(call,BrawlerData.rareList,4)

                var startIdx = (util.page - 1) * util.perPage

                if(util.page == util.lastPage && util.page != 1){
                    var counter = 0

                    while(counter < util.endItems){
                        util.currentList.add(BrawlerData.rareList[startIdx + counter])
                        counter++
                    }
                } else {
                    val endIdx = startIdx + util.perPage

                    while (startIdx < endIdx) {
                        util.currentList.add(BrawlerData.rareList[startIdx])
                        startIdx++
                    }

                }

                val map = mutableMapOf<String, Any>()
                map["title"] = "Rare Brawlers "
                map["description"] = "Brawlers of Rare rarity"
                map["brawlerCount"] = BrawlerData.rareList.size
                map["brawlers"] = util.currentList
                call.respond(map)
            }

            get("/trophyRoad") {

                val util = UtilRarity(call,BrawlerData.trophyRoadList,5)

                var startIdx = (util.page - 1) * util.perPage

                if(util.page == util.lastPage && util.page != 1){
                    var counter = 0

                    while(counter < util.endItems){
                        util.currentList.add(BrawlerData.trophyRoadList[startIdx + counter])
                        counter++
                    }
                } else {
                    val endIdx = startIdx + util.perPage

                    while (startIdx < endIdx) {
                        util.currentList.add(BrawlerData.trophyRoadList[startIdx])
                        startIdx++
                    }

                }

                val map = mutableMapOf<String, Any>()
                map["title"] = "Trophy Road Brawlers "
                map["description"] = "Brawlers of Trophy Road rarity"
                map["brawlerCount"] = BrawlerData.trophyRoadList.size
                map["brawlers"] = util.currentList
                call.respond(map)
            }

            for (brawler in masterList) {
                if (brawler.name != "8-Bit") {
                    get("/${brawler.name}") {
                        call.respond(masterListPro.get(masterList.indexOf(brawler)))
                    }
                } else {
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
                val one = Numbers(1, "one");
                val two = Numbers(2, "two");
                val three = Numbers(3, "three");
                val four = Numbers(4, "four");
                val five = Numbers(5, "five");
                val six = Numbers(6, "six");
                val subList = listOf(one, two, three, four, five, six);
                val itemCount = subList.size;

                val map = mutableMapOf<String, Any>()
                map["title"] = "numbers"
                map["description"] = "knowledge about numbers :)"
                map["totalItems"] = itemCount as Integer
                map["items"] = subList
                call.respond(map)
            }
        }


    }.start(wait = true)

}

