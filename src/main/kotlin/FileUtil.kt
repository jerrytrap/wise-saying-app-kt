package org.example

import java.io.File
import java.io.FileOutputStream

class FileUtil(
    private val basePath: String
) {
    private var lastIndex = 1

    fun getLastIndex() = lastIndex

    fun save(wiseSaying: WiseSaying) {
        val dataPath = "$basePath$lastIndex.json"
        val file = createFileIfNotExist(dataPath)

        FileOutputStream(file).use { outputStream ->
            outputStream.write(convertToJson(wiseSaying).toByteArray())
        }

        updateIndex()
    }

    private fun createFileIfNotExist(path: String): File {
        val file = File(path)
        file.parentFile?.mkdirs()

        return file
    }

    private fun updateIndex() {
        val path = basePath + "lastId.txt"

        FileOutputStream(path).use { outputStream ->
            outputStream.write(lastIndex++.toString().toByteArray())
        }
    }

    private fun convertToJson(wiseSaying: WiseSaying): String {
        return """
            {
              "id": ${wiseSaying.id},
              "content": "${wiseSaying.content}",
              "author": "${wiseSaying.author}"
            }
        """.trimIndent()
    }
}