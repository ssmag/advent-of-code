import kotlin.math.max
import kotlin.system.measureTimeMillis

object Day5 {
    @JvmStatic
    fun main(args: Array<String>) {
        val file = Util.getFileString(EXAMPLE_FILENAME).split(DELIM)
        val pageOrder = file[0]
        val pages = file[1]
        val pageOrderList: List<String>
        val time = measureTimeMillis {
            pageOrderList = getPageOrder(pageOrder)
        }
        println(time)
        println(pageOrderList)

    }

    private fun getPageOrder(pageOrderString: String): List<String> {
        val listOfPairs = pageOrderString.split("\n")
        val orderedList = mutableListOf<String>()
        val map = mutableMapOf<String, List<String>>()

        for (pairS in listOfPairs) {
            val pair = pairS.split("|")
            val curList = map[pair[0]]
            map[pair[0]] = curList?.plus(pair[1]) ?: listOf(pair[1])
        }

        for (entry in map) {
            for (num in entry.value) {
                if (orderedList.contains(num).not()) {
                    orderedList.add(num)
                }
            }
            orderedList.remove(entry.key)
            val nextAdditionIndex = max(orderedList.indexOfFirst { entry.value.contains(it) },0)
            orderedList.add(nextAdditionIndex, entry.key)
        }
//        println("map: $map")
//        println("orderedList: $orderedList")

        return orderedList
    }


    private const val FILENAME = "src/main/res/day5.txt"
    private const val EXAMPLE_FILENAME = "src/main/res/example-day5.txt"
    private val DELIM = "\n\n"
}
