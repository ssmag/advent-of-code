import kotlin.system.measureTimeMillis

object Day6 {

    private val gameMap: Array<CharArray> by lazy { initGameMap() }

    @JvmStatic
    fun main(args: Array<String>) {
        val time = measureTimeMillis {
            firstStar()
        }
        println(time)
    }

    private fun initGameMap(): Array<CharArray> {
        val file = Util.getFileString(FILENAME)
        return  file.split("\n").map { line -> line.toCharArray() }.toTypedArray()
    }

    private fun printMap() {
        gameMap.forEach { row ->
            row.forEach { char ->
                print("$char ")
            }
            println()
        }
    }

    private fun firstStar() {
        val guard = Guard(getGuardCoords())

        while (guard.isInside()) {
            guard.move()
        }
        println(numOfXsOnMap())

    }

    private fun numOfXsOnMap(): Int {
        var numOfX = 1
        gameMap.forEach {  row ->
            numOfX += row.count { it == 'X' }
        }

        return numOfX
    }

    private fun getGuardCoords(): Pair<Int, Int> {
        gameMap.forEachIndexed { i, row ->
            row.forEachIndexed { j, c ->
                if (c != '.' && c != '#') {
                    return Pair(i,j)
                }
            }
        }

        return Pair(-1,-1)
    }

    private fun secondStar(pageOrder: String, pages: List<List<String>>) {
    }

    private class Guard(var currentCoords: Pair<Int, Int>) {
        val facingDirectionMap = mapOf(
            Pair(Y_UP, STILL)   to '^',
            Pair(STILL, X_UP)   to '>',
            Pair(Y_DOWN, STILL) to 'v',
            Pair(STILL, X_DOWN) to '<'
        )
        var facingDirection = getStartingFacingDirection()

        var facingRepresentation = facingDirectionMap[facingDirection] ?: '*'

        fun move() {
            if (lookAhead() == '#') {
                turnRight()
            } else {
                moveForward()
            }
        }

        fun moveForward() {
            try {
                gameMap[currentCoords] = 'X'
                currentCoords += facingDirection
                gameMap[currentCoords] = facingRepresentation
            } catch (e: ArrayIndexOutOfBoundsException) {
                println("out of bounds bitch")
            }
        }

        fun turnRight() {
            val newDirection = when (facingDirection) {
                Pair(-1, 0) -> {
                    Pair(0, 1)
                }

                Pair(0, 1) -> {
                    Pair(1, 0)
                }

                Pair(1, 0) -> {
                    Pair(0, -1)
                }

                Pair(0, -1) -> {
                    Pair(-1, 0)
                }

                else -> {
                    Pair(STILL, STILL)
                }
            }

            facingDirection = newDirection
            updateFacingRepresentation()
        }

        fun getStartingFacingDirection(): Pair<Int, Int> {
            return when (gameMap[currentCoords]) {
                '^' -> { Pair(Y_UP, STILL) }
                '>' -> { Pair(STILL, X_UP) }
                'v' -> { Pair(Y_DOWN, STILL) }
                '<' -> { Pair(STILL, X_DOWN) }
                else -> { Pair(STILL, STILL) }
            }
        }

        fun updateFacingRepresentation() {
            facingRepresentation = facingDirectionMap[facingDirection] ?: '%'
            gameMap[currentCoords] = facingRepresentation
        }

        fun lookAhead(): Char {
            return gameMap[currentCoords + facingDirection]
        }

        fun isInside() =
            currentCoords.first < gameMap.first().size - 1 &&
                    currentCoords.second <= gameMap.size - 1 &&
                    currentCoords.first >= 0 &&
                    currentCoords.second >= 0



        operator fun Pair<Int, Int>.plus(pair: Pair<Int, Int>): Pair<Int, Int> {
            return Pair(this.first + pair.first, this.second + pair.second)
        }


        companion object {
            private const val STILL = 0
            private const val Y_UP = -1
            private const val Y_DOWN = 1
            private const val X_UP = 1
            private const val X_DOWN = -1
        }
    }

    private const val FILENAME = "src/main/res/day6.txt"
    private const val EXAMPLE_FILENAME = "src/main/res/example-day6.txt"
    private val DELIM = "\n\n"
}

private operator fun Array<CharArray>.set(cords: Pair<Int, Int>, value: Char) {
    this[cords.first][cords.second] = value
}

private operator fun Array<CharArray>.get(coords: Pair<Int, Int>): Char {
         return this[coords.first][coords.second]
}
