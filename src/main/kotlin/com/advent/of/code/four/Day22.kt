package com.advent.of.code.four

import java.io.File
import kotlin.system.measureTimeMillis

object Day22 {


    @JvmStatic
    fun main(args: Array<String>) {
        val time = measureTimeMillis {
            println("firstStar: ${firstStar()}")
        }

        println(time)
    }

    private fun firstStar(): Long {
        var sum = 0L
        getLinesList(EXAMPLE_FILENAME).map { line: String ->
            line.toLong()
        }.forEach { secretNumber ->
            sum += predictSecretNumber(secretNumber)
        }

       return sum

    }

    private fun secondStar(): Map.Entry<List<Long>, Long>? {
        val  mapOfScores = mutableMapOf<List<Long>, Long>()
        val mapOfBuyers = mutableMapOf<List<Long>, MutableList<Int>>()
        getLinesList(FILENAME).map { line: String ->
            line.toLong()
        }.forEachIndexed { buyerIndex: Int, secretNumber: Long ->
            val pricesSequence = getListOfSecretNumbers(secretNumber).map { it % 10 }
//                .also {
//                    print("Price sequence:\t")
//                    println(String.format(it.joinToString(", ")))
//                }
            val differences = pricesSequence.zipWithNext { a, b -> b - a }
//                .also {
//                    print("Differences:\t")
//                    println(String.format(it.joinToString(", ")))
//                    println()
//                }

            pricesSequence.forEachIndexed { i, l ->
                if (i < 4) { return@forEachIndexed }
                val sequence = differences.subList(i-4, i)

                if (mapOfScores[sequence] == null) {
                    mapOfScores[sequence] = pricesSequence[i]
                    if (mapOfBuyers[sequence] == null) {
                        mapOfBuyers[sequence] = mutableListOf(buyerIndex)
                    } else {
                        mapOfBuyers[sequence]?.add(buyerIndex)
                    }
//                    println("Map of Buyers:")
//                    mapOfBuyers.forEach { (k, v) ->
//                        println("$k: $v")
//                    }
//                    println("hasBoughtFromThisGuy: $hasBoughtFromThisGuy")
                } else if (mapOfBuyers[sequence]?.contains(buyerIndex)?.not() == true) {
                    mapOfScores[sequence] = mapOfScores[sequence]!! + pricesSequence[i]
                    if (mapOfBuyers[sequence] == null) {
                        mapOfBuyers[sequence] = mutableListOf(buyerIndex)
                    } else {
                        mapOfBuyers[sequence]?.add(buyerIndex)
                    }
//                    println("Map of Buyers:")
//                    mapOfBuyers.forEach { (k, v) ->
//                        println("$k: $v")
//                    }
                }

            }
        }

//        mapOfScores.forEach { (k, v) ->
//            println("$k: $v")
//        }

        val maxKey = mapOfScores.maxByOrNull { it.value }


        return maxKey
    }

    private fun predictSecretNumber(secretNumber: Long): Long {
        var newSecretNumber = secretNumber
        for (i in 1..2000) {
            newSecretNumber = iteration(newSecretNumber)
        }
        return newSecretNumber
    }

    private fun getListOfSecretNumbers(secretNumber: Long): MutableList<Long> {
        val secretNumbers = mutableListOf<Long>()
        secretNumbers.add(secretNumber)
        for (i in 1..2000) {
             secretNumbers.add(iteration(secretNumbers.last()))
        }


        return secretNumbers
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

    private const val FILENAME = "src/main/res/twenty-four/day22.txt"
    private const val EXAMPLE_FILENAME = "src/main/res/twenty-four/example-day22.txt"
    private val DELIM = "\n\n"

    fun getLinesList(fileName: String): MutableList<String> {
        val result = mutableListOf<String>()
        File(fileName).useLines { lines -> lines.forEach { result.add(it) } }
        return result
    }
}