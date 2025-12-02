package com.advent.of.code.four

import kotlin.system.measureTimeMillis

object Day6 {

    private val gameMap: Array<CharArray> by lazy { initGameMap() }

    @JvmStatic
    fun main(args: Array<String>) {
        val time = measureTimeMillis {
            val guard = Guard()
            firstStar(guard)
            println(secondStar(guard))
        }
        println("time elapsed: $time")
    }

    private fun initGameMap(): Array<CharArray> {
        val file = Util.getFileString(FILENAME)
        return file.split("\n").map { line -> line.toCharArray() }.toTypedArray()
    }

    private fun printMap() {
        println()
        gameMap.forEach { row ->
            row.forEach { char ->
                print("$char ")
            }
            println()
        }
    }

    private fun firstStar(guard: Guard) {
        while (guard.isInside()) {
            guard.move()
        }
        gameMap[guard.currentCoords] = 'X'

    }

    private fun secondStar(guard: Guard): Int {
        guard.reset()
        val solutionsList = mutableListOf<Pair<Int, Int>>()
        getXCoords().forEach { coord ->
            gameMap[coord] = MY_OBSTACLE
            while (guard.isInside()) {
                guard.move()
                if (guard.hasBeenHereBefore()) {
                    solutionsList.add(coord)
                    guard.reset()
                    break
                }
            }

            gameMap[coord] = EMPTY_SPOT
            guard.reset()

        }
        return solutionsList.size

    }

    private fun numOfXsOnMap(): Int {
        var numOfX = 1
        gameMap.forEach { row ->
            numOfX += row.count { it == PASSED_SPOT }
        }

        return numOfX
    }

    private fun getXCoords(): List<Pair<Int, Int>> {
        val resList = mutableListOf<Pair<Int, Int>>()
        gameMap.forEachIndexed { i, row ->
            row.forEachIndexed { j, c ->
                if (c == PASSED_SPOT) {
                    resList.add(Pair(i, j))
                }
            }
        }

        return resList
    }

    private class Guard {
        var currentCoords = getGuardCoords()
        var currentDirection = getStartingFacingDirection()
        val originalPosition = currentCoords
        val originalFacingDirection = currentDirection
        val listOfPreviousLocations = mutableListOf<LocationWithDirection>()


        private fun getGuardCoords(): Pair<Int, Int> {
            gameMap.forEachIndexed { i, row ->
                row.forEachIndexed { j, c ->
                    if (Direction.entries.map { it.repChar }.contains(c)) {
                        return Pair(i, j)
                    }
                }
            }

            return Pair(-1, -1)
        }

        fun move() {
            if (lookAhead() == OBSTACLE || lookAhead() == MY_OBSTACLE) {
                turnRight()
            } else {
                moveForward()
            }
        }

        fun moveForward() {
            try {
                listOfPreviousLocations.add(LocationWithDirection(currentCoords, currentDirection))
                gameMap[currentCoords] = PASSED_SPOT
                currentCoords += currentDirection.unitVector
                gameMap[currentCoords] = currentDirection.repChar
            } catch (e: ArrayIndexOutOfBoundsException) {
                println("out of bounds bitch")
            }
        }

        fun turnRight() {
            val newDirection = when (currentDirection) {
                Direction.RIGHT -> Direction.DOWN
                Direction.DOWN -> Direction.LEFT
                Direction.LEFT -> Direction.UP
                Direction.UP -> Direction.RIGHT
                Direction.NONE -> Direction.NONE
            }

            currentDirection = newDirection
            gameMap[currentCoords] = currentDirection.repChar
        }

        fun reset() {
            listOfPreviousLocations.clear()
            gameMap[currentCoords] = PASSED_SPOT
            currentCoords = originalPosition
            currentDirection = originalFacingDirection
            gameMap[currentCoords] = currentDirection.repChar
        }

        fun hasBeenHereBefore(): Boolean {
            return listOfPreviousLocations.contains(LocationWithDirection(currentCoords, currentDirection))
        }

        fun getStartingFacingDirection(): Direction {
            return Direction.entries.find { it.repChar == gameMap[currentCoords] } ?: Direction.NONE
        }

        fun lookAhead(): Char {
            return gameMap[currentCoords + currentDirection.unitVector]
        }

        fun isInside() =
            currentCoords.first < gameMap.first().size - 1 &&
                    currentCoords.second < gameMap.first().size - 1 &&
                    currentCoords.first > 0 &&
                    currentCoords.second >  0

        operator fun Pair<Int, Int>.plus(pair: Pair<Int, Int>): Pair<Int, Int> {
            return Pair(this.first + pair.first, this.second + pair.second)
        }


    }

    enum class Direction(val repChar: Char, val unitVector: Pair<Int, Int>) {
        UP('^', Pair(Y_UP, STILL)),
        DOWN('v', Pair(Y_DOWN, STILL)),
        LEFT('<', Pair(STILL, X_DOWN)),
        RIGHT('>', Pair(STILL, X_UP)),
        NONE('*', Pair(STILL, STILL))
    }

    private data class LocationWithDirection(
        val location: Pair<Int, Int>,
        val direction: Direction
    )

    private const val FILENAME = "src/main/res/twenty-four/day6.txt"
    private const val EXAMPLE_FILENAME = "src/main/res/twenty-four/example-day6.txt"
    private val DELIM = "\n\n"

    const val STILL = 0
    const val Y_UP = -1
    const val Y_DOWN = 1
    const val X_UP = 1
    const val X_DOWN = -1
    const val PASSED_SPOT = 'X'
    const val EMPTY_SPOT = '.'
    const val OBSTACLE = '#'
    const val MY_OBSTACLE = 'O'
}


private operator fun Array<CharArray>.set(cords: Pair<Int, Int>, value: Char) {
    this[cords.first][cords.second] = value
}

private operator fun Array<CharArray>.get(coords: Pair<Int, Int>): Char {
    return this[coords.first][coords.second]
}
