import kotlin.system.measureTimeMillis

object Day6 {

    private val gameMap: Array<CharArray> by lazy { initGameMap() }

    @JvmStatic
    fun main(args: Array<String>) {
        val time = measureTimeMillis {
            val guard = Guard()
            firstStar(guard)
            secondStar(guard)
        }
        println("time elapsed: $time")
    }

    private fun initGameMap(): Array<CharArray> {
        val file = Util.getFileString(EXAMPLE_FILENAME)
        return  file.split("\n").map { line -> line.toCharArray() }.toTypedArray()
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

    private fun numOfXsOnMap(): Int {
        var numOfX = 1
        gameMap.forEach {  row ->
            numOfX += row.count { it == PASSED_SPOT }
        }

        return numOfX
    }

    private fun secondStar(guard: Guard) {
        guard.reset()
        printMap()
        guard.move()
        printMap()
    }

    private class Guard {
        var currentCoords = getGuardCoords()
        var facingDirection = getStartingFacingDirection()
        val originalPosition = currentCoords
        val originalFacingDirection = facingDirection


        private fun getGuardCoords(): Pair<Int, Int> {
            gameMap.forEachIndexed { i, row ->
                row.forEachIndexed { j, c ->
                    if (Direction.entries.map { it.repChar }.contains(c)) {
                        return Pair(i,j)
                    }
                }
            }

            return Pair(-1,-1)
        }

        fun move() {
            if (lookAhead() == '#') {
                turnRight()
            } else {
                moveForward()
            }
        }

        fun moveForward() {
            try {
                gameMap[currentCoords] = PASSED_SPOT
                currentCoords += facingDirection.unitVector
                gameMap[currentCoords] = facingDirection.repChar
            } catch (e: ArrayIndexOutOfBoundsException) {
                println("out of bounds bitch")
            }
        }

        fun turnRight() {
            val newDirection = when (facingDirection) {
                Direction.RIGHT -> Direction.DOWN
                Direction.DOWN -> Direction.LEFT
                Direction.LEFT -> Direction.UP
                Direction.UP -> Direction.RIGHT
                Direction.NONE -> Direction.NONE
            }

            facingDirection = newDirection
            gameMap[currentCoords] = facingDirection.repChar
        }

        fun reset() {
            currentCoords = originalPosition
            facingDirection = originalFacingDirection
            gameMap[currentCoords] = facingDirection.repChar
        }

        fun getStartingFacingDirection(): Direction {
            return Direction.entries.find { it.repChar == gameMap[currentCoords] } ?: Direction.NONE
        }

        fun lookAhead(): Char {
            return gameMap[currentCoords + facingDirection.unitVector]
        }

        fun isInside() =
            currentCoords.first < gameMap.first().size - 1 &&
                    currentCoords.second < gameMap.first().size - 1 &&
                    currentCoords.first >= 0 &&
                    currentCoords.second >= 0

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
    private const val FILENAME = "src/main/res/day6.txt"
    private const val EXAMPLE_FILENAME = "src/main/res/example-day6.txt"
    private val DELIM = "\n\n"

    const val STILL = 0
    const val Y_UP = -1
    const val Y_DOWN = 1
    const val X_UP = 1
    const val X_DOWN = -1
    const val PASSED_SPOT = 'X'
}


private operator fun Array<CharArray>.set(cords: Pair<Int, Int>, value: Char) {
    this[cords.first][cords.second] = value
}

private operator fun Array<CharArray>.get(coords: Pair<Int, Int>): Char {
         return this[coords.first][coords.second]
}
