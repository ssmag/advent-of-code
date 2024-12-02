import java.io.File

object Day1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lineList = mutableListOf<String>()

        File("src/main/res/day1.txt").useLines { lines -> lines.forEach { lineList.add(it) }}
        lineList.forEach { println(">  $it") }
    }
}
