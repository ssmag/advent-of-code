import Util.getLinesList
import kotlin.math.abs

object Day3 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lineList = getLinesList(FILENAME)
        val fullLine = lineList.reduce { acc, s -> "$acc$s"}
        val regex = Regex("mul\\([0-9]+,[0-9]+\\)")
        val multiplications = regex.findAll(fullLine)
        var sum = 0
        multiplications.forEach { multiplication ->
            val operation = multiplication.value
            val product = operation
                .filterNot { c ->
                    c == ')' || c == '(' || (c in 'a'..'z')
                }
                .split(DELIM)
                .map { numString ->
                    Integer.parseInt(numString)
                }
                .reduce { acc, i -> acc * i }
            sum += product
        }
        println(sum)
    }

}


private const val FILENAME = "src/main/res/day3.txt"
private const val DELIM = ","
