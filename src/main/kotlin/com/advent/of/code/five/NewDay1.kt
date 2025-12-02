package com.advent.of.code.four

import java.lang.Math.floorMod

object NewDay1 {
    @JvmStatic
    fun main(args: Array<String>) {
        print(firstStar())
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

}


private const val FILENAME = "src/main/res/twenty-five/day1.txt"
private const val EXAMPLE_FILENAME = "src/main/res/twenty-five/example-day1.txt"
private const val DELIM = "\n"
