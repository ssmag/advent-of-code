import kotlin.system.measureTimeMillis

object Day4 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lineList = Util.getLinesList(EXAMPLE_FILENAME)
        var ceresNum = -1
        val time = measureTimeMillis {
            ceresNum = findCeresNumber(lineList)
        }
        println("time elapsed: $time")
        println(ceresNum)
    }

    private fun findCeresNumber(lineList: MutableList<String>): Int {
        val matrix = Array(lineList.size) { Array(lineList.first().length) { '.' } }
        val charTable = mutableMapOf<Char, MutableList<Pair<Int, Int>>>(
            'X' to mutableListOf(),
            'M' to mutableListOf(),
            'A' to mutableListOf(),
            'S' to mutableListOf()
        )

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
    ): Map<Direction, Pair<Int,Int>> {
        val x = root.first
        val y = root.second
        val allDirs = mapOf(
           Direction.TOP_LEFT       to Pair(x-1,   y-1),
           Direction.TOP_CENTER     to Pair(x-1,   y),
           Direction.TOP_RIGHT      to Pair(x-1,   y+1),
           Direction.CENTER_LEFT    to Pair(x,     y-1),
           Direction.CENTER_RIGHT   to Pair(x,     y+1),
           Direction.BOTTOM_LEFT    to Pair(x+1,   y-1),
           Direction.BOTTOM_CENTER  to Pair(x+1,   y),
           Direction.BOTTOM_RIGHT   to Pair(x+1,   y+1),
        )

        return allDirs
            .filter { dir ->
                charTable[c]?.contains(dir.value) == true
            }
    }


    private enum class Direction {
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
        CENTER_LEFT,
        CENTER_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        BOTTOM_RIGHT,
    }

    private const val FILENAME = "src/main/res/day4.txt"
    private const val EXAMPLE_FILENAME = "src/main/res/example-day4.txt"
    private const val DELIM = ","
}
