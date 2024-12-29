import kotlin.system.measureTimeMillis

object Day25 {


    @JvmStatic
    fun main(args: Array<String>) {
        val time = measureTimeMillis {
            println(firstStar())
        }

        println(time)
    }

    private fun firstStar(): Int {
        val keysNlocks = Util.getFileString(FILENAME).split(DELIM)
        val lockStrings = mutableListOf<String>()
        val keysStrings = mutableListOf<String>()
        val locks = mutableListOf<List<Int>>()
        val keys = mutableListOf<List<Int>>()
        var sum = 0
        keysNlocks.forEach { itemString ->
            if (itemString.startsWith("#")) {
                lockStrings.add(itemString)
                locks.add(itemString.convertToList())
            } else {
                keysStrings.add(itemString)
                keys.add(itemString.convertToList())
            }
        }

        keys + locks
        keys.forEach { key ->
            locks.forEach { lock ->
                key.zip(lock) { k, l ->
                    k + l
                }.also {
                }.all { num ->
                    num <= 5
                }.also {
                    if (it) {
                        sum += 1
                    }
                }
            }
        }
        return sum
    }

    private fun secondStar(): Long {
        return -1
    }

    private operator fun List<Int>.plus(two: List<Int>): List<Int> {
        var result: List<Int> = listOf()
        this.forEach { key ->
            two.forEach { lock ->
                result = this.zip(two) { k, l ->
                    k + l
                }
            }
        }

        return result
    }

    private fun String.convertToList(): List<Int> {
        val result: MutableList<Int> = mutableListOf()
        val lineOfSlots =
            split("\n")
            .drop(1)
            .dropLast(1)
        lineOfSlots.first().forEachIndexed { i, _ ->
            val transposed = lineOfSlots.map { line ->
                line[i]
            }
            val slot = transposed.count { c -> c == '#'}
            result.add(slot)
        }

        return result

    }

    private const val FILENAME = "src/main/res/day25.txt"
    private const val EXAMPLE_FILENAME = "src/main/res/example-day25.txt"
    private val DELIM = "\n\n"

}