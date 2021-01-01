import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.Parameters

class Util(private val call: ApplicationCall,
           private val List: ArrayList<Brawler>) {

    private val request = call.request
    private val queryParameters: Parameters = request.queryParameters
    var page = 1
    var perPage = 4
    val totalItems = List.size
    var endItems = 0
    var lastPage = 0
    val currentList = ArrayList<Brawler>()

    init {

        val pageParam = queryParameters["page"]
        val perPageParam = queryParameters["per_page"]

        if (perPageParam != null && Integer.parseInt(perPageParam) > 0) {
            val i = Integer.parseInt(perPageParam)
            if (i <= 10) perPage = i
            // perPage max value = 10
        }

        if (pageParam != null && Integer.parseInt(pageParam) > 0) {
            val i = Integer.parseInt(pageParam)
            val uncountedItems = totalItems % perPage
            endItems = uncountedItems
            val r = totalItems / perPage
            val maxPage = if (uncountedItems > 0) r + 1 else r
            lastPage = maxPage
            if (i <= maxPage) page = i
            // calculate page max value
        }

    }

}