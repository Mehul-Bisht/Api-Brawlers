data class holder(
        val name : String,
        val rarity : String
)

fun main(){
//    val l = listOf("a","b","c")
//    val l2 = l.toMutableList()
//    l2.add("a")
//    for(ele in l2) {
//        print("$ele ")
//    }

    val h1 = holder("aa","magic")
    val h2 = holder("bb","magic")
    val h3 = holder("cc","magic")
    val h4 = holder("dd","magic")

    val list = listOf(h1,h2,h3,h4)
    val tempList = list.toMutableList()
    tempList.add(holder("ab","new"))

    tempList.sortBy {
        it.name
    }

    for(ele in tempList){
        println(ele)
    }
}