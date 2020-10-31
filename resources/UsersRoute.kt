import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get

private val userData = "{ \"users\": [\"Amber\", \"Collete\", \"Surge\", \"Gale\"] }"

fun Routing.users() {
    get("/users"){
        call.respondText(userData)
    }
}