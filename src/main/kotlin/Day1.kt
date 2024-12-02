import Util.getLinesList
import kotlin.math.abs

object Day1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lineList = getLinesList(FILENAME)
        val list1Arr = mutableListOf<Int>()
        val list2Arr = mutableListOf<Int>()
        lineList.forEach { line ->
            line
                .split(DELIM)
                .map {
                    Integer.parseInt(it)
                }
                .also { intLine ->
                    list1Arr.add(intLine[0])
                    list2Arr.add(intLine[1])
                }
        }

        list1Arr.sort()
        list2Arr.sort()

        val distanceArr = list1Arr.zip(list2Arr) { i, j -> abs(i - j)}
        val sum = distanceArr.fold(0) { acc, i -> acc + i }

        println("sum: $sum")
    }

}


const val FILENAME = "src/main/res/day1.txt"
const val DELIM = "   "
