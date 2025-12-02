package com.advent.of.code.five

import java.lang.Math.floorMod
import kotlin.math.abs
import kotlin.system.measureTimeMillis

object NewDay1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val time2 = measureTimeMillis {
            println(secondStar())
        }
        println(time2)
    }

    private fun firstStar(): Int {
        val movements = Util.getFileString(FILENAME).split(DELIM)
        var pointer = 50
        var counter = 0

        movements.forEach { m ->
            val direction = if (m.first() == 'L') -1 else 1
            val movement = m.substring(1).toInt()
            val res = movement * direction
            pointer = floorMod(pointer + res, 100)
            if (pointer == 0) { counter++ }
        }
        return counter
    }

    private fun secondStar(): Int {
        val movements = Util.getFileString(FILENAME).split(DELIM)
        var pointer = 50
        var counter = 0

        println("pointer: $pointer")
        movements.forEach { m ->
            val direction = if (m.first() == 'L') -1 else 1
            val movement = m.substring(1).toInt()
            val res = movement * direction
            val oldPointer = pointer
            val crudePointer = pointer + res
            pointer = floorMod(crudePointer, 100)

            println("m: $m")
            println("pointer: $pointer")
            if (crudePointer > 99) {
                val timesCrossed = abs(oldPointer + res)/100
                println("TimesCrossed positive: $timesCrossed")
                counter+=timesCrossed
                if (pointer == 0) {
                    counter--
                }
            }
            if (crudePointer < 0) {
                // gets triggered when starting from zero.
                // we want it to trigger for when oldPointer is non-zero and it hasnt lapped the circle
                val zeroOffset = if (oldPointer == 0) { 0 } else { 1 }
                val timesCrossed = abs(oldPointer + res)/100 + zeroOffset
                println("timesCrossed Negative: $timesCrossed")
                counter+=timesCrossed
                if (pointer == 0) {
                    counter--
                }
            }

            if (pointer == 0) { counter++ }
            println("Counter: $counter")
        }
        return counter
    }

}


private const val FILENAME = "src/main/res/twenty-five/day1.txt"
private const val EXAMPLE_FILENAME = "src/main/res/twenty-five/example-day1.txt"
private const val DELIM = "\n"
