package org.example

import java.io.File
import java.io.FileOutputStream

class FileUtil(
    private val basePath: String
) {
    init {
        createFileOrDirectoryIfNotExist(basePath)
    }

    fun loadAll(): List<WiseSaying> {
        val directory = File(basePath)
        createFileOrDirectoryIfNotExist(basePath)

        val fileList = directory.listFiles { _, name ->
            name.endsWith(".json")
        }?.reversed() ?: emptyList()

        return fileList.map { file ->
            parseJson(file.readText())
        }
    }

    fun loadLastIndex(): Int {
        val path = "${basePath}lastId.txt"
        val file = File(path)
        createFileOrDirectoryIfNotExist(path)

        val content = file.readText().trim()
        return content.toIntOrNull() ?: 1
    }

    fun clearAll() {
        val directory = File(basePath)

        if (directory.exists().and(directory.isDirectory)) {
            val files = directory.listFiles()

            if (files != null) {
                for (file in files) {
                    if (file.isFile) {
                        file.delete()
                    }
                }
            }
        }
    }

    fun save(id: Int, wiseSaying: WiseSaying): Int {
        val path = "$basePath${id}.json"
        val file = createFileOrDirectoryIfNotExist(path)

        FileOutputStream(file).use { outputStream ->
            outputStream.write(convertToJson(wiseSaying).toByteArray())
        }

        updateIndex(id + 1)
        return id + 1
    }

    fun remove(id: Int) {
        val path = "$basePath${id}.json"
        val file = File(path)

        if (file.exists()) {
            file.delete()
        }
    }

    private fun createFileOrDirectoryIfNotExist(path: String): File {
        val file = File(path)

        if (file.exists().not()) {
            if (path.endsWith(File.separator)) {
                file.mkdirs()
            } else {
                file.createNewFile()
            }
        }

        return file
    }

    private fun updateIndex(id: Int) {
        val path = basePath + "lastId.txt"

        FileOutputStream(path).use { outputStream ->
            outputStream.write(id.toString().toByteArray())
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

    private fun parseJson(jsonString: String): WiseSaying {
        val map = mutableMapOf<String, Any>()
        val cleanString = jsonString.trim().removeSurrounding("{", "}")

        cleanString.split(",").forEach { entry ->
            val keyValue = entry.split(":")
            if (keyValue.size == 2) {
                val key = keyValue[0].trim().removeSurrounding("\"")
                val value = keyValue[1].trim().removeSurrounding("\"")
                val parsedValue = value.toIntOrNull() ?: value

                map[key] = parsedValue
            }
        }

        val id: Int = map["id"].toString().toInt()
        val content = map["content"].toString()
        val author = map["author"].toString()

        return WiseSaying(id, content, author)
    }
}