import Util.getLinesList
import kotlin.math.abs

object Day3 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lineList = getLinesList(FILENAME)
        val fullLine = lineList.reduce { acc, s -> "$acc$s"}
        getSumOfProductsWhenEnabled(fullLine)
    }

    private fun getSumOfProductsWhenEnabled(s: String) {
        val regex = Regex("mul\\([0-9]+,[0-9]+\\)|do\\(\\)|don\'t\\(\\)")
        val multiplications = regex.findAll(s).map { mult -> mult.value }
        var sum = 0
        var shouldMul = true
        multiplications.forEach { operation ->
            if (operation == DO) {
                shouldMul = true
                return@forEach
            }
            if (operation == DONT) {
                shouldMul = false
                return@forEach
            }
            if (!shouldMul) {
                return@forEach
            }
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

    private fun getSumOfProductsOfMulOperations(s :String) {
        val regex = Regex("mul\\([0-9]+,[0-9]+\\)")
        val multiplications = regex.findAll(s)
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


private const val DO = "do()"
private const val DONT = "don't()"
private const val FILENAME = "src/main/res/day3.txt"
private const val DELIM = ","
