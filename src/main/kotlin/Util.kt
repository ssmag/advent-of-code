import java.io.File
import java.io.InputStream

object Util {
    fun getLinesList(fileName: String): MutableList<String> {
        val result = mutableListOf<String>()
        File(fileName).useLines { lines -> lines.forEach { result.add(it) } }
        return result
    }

    fun getFileString(fileName: String): String {
        val inputStream: InputStream = File(fileName).inputStream()
        return inputStream.bufferedReader().use { it.readText() }
    }
}