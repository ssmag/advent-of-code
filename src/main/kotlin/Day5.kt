import kotlin.math.max
import kotlin.system.measureTimeMillis

object Day5 {
    private var pageOrderMap = mutableMapOf<String, List<String>>()
    @JvmStatic
    fun main(args: Array<String>) {
        val file = Util.getFileString(EXAMPLE_FILENAME).split(DELIM)
        val pageOrder = file[0]
        val pages = file[1]
        val time = measureTimeMillis {
            pageOrderMap = getPageOrder(pageOrder)
            getValidPageLists(pages)
        }
        println(time)
        println(pageOrderMap)
    }

    private fun getValidPageLists(pages: String): List<String> {
        println(pages)
        val listOfListOfNums = pages.split("\n").map { it.split(",").reversed() }
        listOfListOfNums.forEachIndexed outerloop@ { i, listOfNums ->
            var isValid = true
            pageOrderMap.forEach { (k, v) ->
                val indexOfKey = listOfNums.indexOf(k)
                if (indexOfKey == -1) return@forEach
                val everythingBeforeNumKey = listOfNums.subList(indexOfKey + 1, listOfNums.size)
                if (v.any { it in everythingBeforeNumKey }) {
                    isValid = false
                    return@outerloop
                }

            }
            println("${listOfNums.reversed()} isvalid: $isValid")
        }
        print(listOfListOfNums)
        return emptyList()
    }

    private fun getPageOrder(pageOrderString: String): MutableMap<String, List<String>> {
        val listOfPairs = pageOrderString.split("\n")
        val map = mutableMapOf<String, List<String>>()

        for (pairS in listOfPairs) {
            val pair = pairS.split("|")
            val curList = map[pair[0]]
            map[pair[0]] = curList?.plus(pair[1]) ?: listOf(pair[1])
        }

//        println("orderedList: $orderedList")

        return map
    }


    private const val FILENAME = "src/main/res/day5.txt"
    private const val EXAMPLE_FILENAME = "src/main/res/example-day5.txt"
    private val DELIM = "\n\n"
}
