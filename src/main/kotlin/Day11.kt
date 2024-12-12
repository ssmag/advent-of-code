import kotlin.math.ceil
import kotlin.math.log10
import kotlin.system.measureTimeMillis

object Day11 {


    @JvmStatic
    fun main(args: Array<String>) {
        val time = measureTimeMillis {
            firstStar()
        }
        println("time elapsed: $time")
    }

    private fun firstStar() {
        val rocksArr = Util.getFileString(FILENAME).split(' ').map { it.toLong() }.toMutableList()

        println(rocksArr)
        println(blink(rocksArr).count())
    }

    private fun blink(rocksArr: MutableList<Long>, counter: Int = 0): List<Long> {
        if (counter == 25) {
            return rocksArr
        }
        val newRocksArr = mutableListOf<Long>()
        rocksArr.forEach { rock ->
            if (rock == 0L) {
                newRocksArr.add(1)
            } else if (getNumOfDigits(rock) != 0 &&  getNumOfDigits(rock) % 2 == 0) {
                val side1 = rock.toString().substring(0, rock.toString().length / 2).toLong()
                val side2 = rock.toString().substring(rock.toString().length / 2).toLong()
                newRocksArr.add(side1)
                newRocksArr.add(side2)
            } else {
                newRocksArr.add((rock * 2024))
            }
        }
        return blink(newRocksArr, counter+1)
    }

    private fun getNumOfDigits(num: Long): Int {
        if (num == 0L) return 1
        return ceil(log10(num.toDouble())).toInt()
    }

    private fun secondStar(): Int {
        return -1
    }


    private const val FILENAME = "src/main/res/day11.txt"
    private const val EXAMPLE_FILENAME = "src/main/res/example-day11.txt"
    private val DELIM = "\n\n"

}