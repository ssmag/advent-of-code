import java.io.File

object Util {
    fun getLinesList(fileName: String): MutableList<String> {
        val result = mutableListOf<String>()
        File(fileName).useLines { lines -> lines.forEach { result.add(it) } }
        return result
    }
}