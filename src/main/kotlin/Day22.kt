import kotlin.system.measureTimeMillis

object Day22 {


    @JvmStatic
    fun main(args: Array<String>) {
        val time = measureTimeMillis {
            println(firstStar())
        }

        println(time)
    }

    private fun firstStar(): Long {
        var sum = 0L
        Util.getLinesList(FILENAME).map { line ->
            line.toLong()
        }.forEach { secretNumber ->
            sum += predictSecretNumber(secretNumber)
        }

       return sum

    }

    private fun predictSecretNumber(secretNumber: Long): Long {
        var newSecretNumber = secretNumber
        for (i in 1..2000) {
            newSecretNumber = iteration(newSecretNumber)
        }
        return newSecretNumber
    }

    private fun iteration(secretNumber: Long): Long {
        val step1 = secretNumber.times(64)
        val step2 = step1.mix(secretNumber)
        val step3 = step2.prune()
        val step4 = step3.floorDiv(32)
        val step5 = step4.mix(step3)
        val step6 = step5.prune()
        val step7 = step6.times(2048)
        val step8 = step7.mix(step6)
        val step9 = step8.prune()
        return step9
    }

    private fun Long.mix(secretNumber: Long): Long {
        return this xor secretNumber
    }

    private fun Long.prune(): Long {
        return this % 16777216
    }

    private fun secondStar(): Long {
        return -1
    }


    private const val FILENAME = "src/main/res/day22.txt"
    private const val EXAMPLE_FILENAME = "src/main/res/example-day22.txt"
    private val DELIM = "\n\n"

}