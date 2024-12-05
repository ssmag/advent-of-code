import kotlin.system.measureTimeMillis

object Day4 {
    private val charTable = mutableMapOf<Char, MutableList<Pair<Int, Int>>>(
        'X' to mutableListOf(),
        'M' to mutableListOf(),
        'A' to mutableListOf(),
        'S' to mutableListOf()
    )

    private val crossArr = listOf('M', 'S')

    private lateinit var matrix: Array<Array<Char>>
    private lateinit var chosenMatrix: Array<Array<Char>>

    @JvmStatic
    fun main(args: Array<String>) {
        val lineList = Util.getLinesList(FILENAME)
        var ceresNum: Int
        matrix = Array(lineList.size) { Array(lineList.first().length) { '.' } }
        chosenMatrix = Array(lineList.size) { Array(lineList.first().length) { '.' } }
        passCharactersToMatrix(lineList)

        findCeresNumber()
        val time = measureTimeMillis {
            countXMas()
        }
        println("time elapsed: $time")

        println(countXMas())
    }

    private fun countXMas(): Int {
        var res = 0
        charTable['A']?.forEach { aCoords ->
            //take top left
            val topLeftCords = aCoords.copy(aCoords.first - 1, aCoords.second - 1)
            val bottomRightCords = aCoords.copy(aCoords.first + 1, aCoords.second + 1)
            val topRightCords = aCoords.copy(aCoords.first - 1, aCoords.second + 1)
            val bottomLeftCords = aCoords.copy(aCoords.first + 1, aCoords.second - 1)
            try {
                val topLeftChar = matrix.getCords(topLeftCords)
                val bottomRightChar = matrix.getCords(bottomRightCords)
                val topRightChar = matrix.getCords(topRightCords)
                val bottomLeftChar = matrix.getCords(bottomLeftCords)

                if (crossArr.contains(topLeftChar) &&
                    crossArr.indexOf(bottomRightChar) == ((crossArr.indexOf(topLeftChar) + 1) % 2) &&
                    crossArr.contains(topRightChar) &&
                    crossArr.indexOf(bottomLeftChar) == ((crossArr.indexOf(topRightChar) + 1) % 2)
                ) {
                    res++
                }

            } catch (e: ArrayIndexOutOfBoundsException) {
                // do nothing
            }
        }

        return res

    }

    private fun findCeresNumber(): Int {
        return charTable['X']
            ?.map { xCoords ->
                findNearbyLetters(charTable, xCoords, 'M')
            }
            ?.map { mCoordsWithDir ->
                findNearByLettersWithDirection(charTable, mCoordsWithDir, 'A')
            }
            ?.map { aCoordsWithDir ->
                findNearByLettersWithDirection(charTable, aCoordsWithDir, 'S')
            }
            ?.flatMap { sLettersWithDir ->
                sLettersWithDir.values
            }
            ?.count() ?: -1
    }

    private fun findNearByLettersWithDirection(
        charTable: MutableMap<Char, MutableList<Pair<Int, Int>>>,
        roots: Map<Direction, Pair<Int, Int>>,
        c: Char,
    ): MutableMap<Direction, Pair<Int, Int>> {

        val result = mutableMapOf<Direction, Pair<Int, Int>>()
        roots.forEach { rootMap ->
            val root = rootMap.value
            val nearbyLettersByDir = findNearbyLetters(charTable, root, c)
                .filter { nearbyLetter ->
                    nearbyLetter.key == rootMap.key
                }
            result.putAll(nearbyLettersByDir)
        }

        return result
    }

    private fun findNearbyLetters(
        charTable: MutableMap<Char, MutableList<Pair<Int, Int>>>,
        root: Pair<Int, Int>,
        c: Char,
    ): Map<Direction, Pair<Int, Int>> {
        val x = root.first
        val y = root.second
        val allDirs = mapOf(
            Direction.TOP_LEFT to Pair(x - 1, y - 1),
            Direction.TOP_CENTER to Pair(x - 1, y),
            Direction.TOP_RIGHT to Pair(x - 1, y + 1),
            Direction.CENTER_LEFT to Pair(x, y - 1),
            Direction.CENTER_RIGHT to Pair(x, y + 1),
            Direction.BOTTOM_LEFT to Pair(x + 1, y - 1),
            Direction.BOTTOM_CENTER to Pair(x + 1, y),
            Direction.BOTTOM_RIGHT to Pair(x + 1, y + 1),
        )

        return allDirs
            .filter { dir ->
                charTable[c]?.contains(dir.value) == true
            }
    }

    private fun <T> Array<Array<T>>.getCords(pair: Pair<Int, Int>): T {
        return this[pair.first][pair.second]
    }


    enum class Direction {
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
        CENTER_LEFT,
        CENTER_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        BOTTOM_RIGHT,
    }

    private fun passCharactersToMatrix(lineList: MutableList<String>) {
        lineList.forEachIndexed { i, line ->
            line.forEachIndexed { j, c ->
                when (c) {
                    'X', 'M', 'A', 'S' -> {
                        charTable[c]!!.add(Pair(i, j))
                    }
                }
                matrix[i][j] = c
            }
        }

    }

    private const val FILENAME = "src/main/res/day4.txt"
    private const val EXAMPLE_FILENAME = "src/main/res/example-day4.txt"
}
