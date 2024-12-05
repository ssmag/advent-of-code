import kotlin.system.measureTimeMillis

object Day5 {
    private var pageOrderMap = mutableMapOf<String, List<String>>()
    @JvmStatic
    fun main(args: Array<String>) {
        val file = Util.getFileString(EXAMPLE_FILENAME).split(DELIM)
        val pageOrder = file[0]
        val pages = file[1].split("\n").map { it.split(",") }
        val validPages = mutableListOf<List<String>>()

        val time = measureTimeMillis {
            pageOrderMap = getPageOrder(pageOrder)
            validPages.addAll(getValidPageLists(pages))
            getSumOfMidNumbers(validPages.map { page -> page.map { numS -> Integer.parseInt(numS)} })
        }
        println("time: $time")
        println(validPages)
        println(pageOrderMap)
    }

    private fun getSumOfMidNumbers(map: List<List<Int>>) {

    }

    private fun getValidPageLists(pages: List<List<String>>): MutableList<List<String>> {
        val resultList = mutableListOf<List<String>>()
        pages.forEachIndexed outerloop@ { i, listOfNums ->
            val listOfNumsReversed = listOfNums.reversed()
            pageOrderMap.forEach { (k, v) ->
                val indexOfKey = listOfNumsReversed.indexOf(k)
                if (indexOfKey == -1) return@forEach
                val everythingBeforeNumKey = listOfNumsReversed.subList(indexOfKey + 1, listOfNumsReversed.size)
                if (v.any { it in everythingBeforeNumKey }) {
                    return@outerloop
                }
            }

            resultList.add(listOfNums)
        }
        return resultList
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
