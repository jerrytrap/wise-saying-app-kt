package org.example

class WiseSayingRepository(
    private val fileUtil: FileUtil
) {
    private val wiseSayings = ArrayList<WiseSaying>()
    private var lastIndex = fileUtil.loadLastIndex()

    init {
        wiseSayings.addAll(fileUtil.loadAll())
        fileUtil.loadLastIndex()
    }

    fun getLastIndex() = lastIndex

    fun add(wiseSaying: WiseSaying) {
        wiseSayings.add(wiseSaying)
        lastIndex = fileUtil.save(lastIndex, wiseSaying)
    }

    fun getCount() = wiseSayings.size

    fun getAll() = wiseSayings.toList()

    fun search(keywordType: KeywordType, keyword: String) =
        wiseSayings.filter {
            when (keywordType) {
                KeywordType.CONTENT -> it.content.contains(keyword)
                KeywordType.AUTHOR -> it.author.contains(keyword)
                KeywordType.NONE -> true
            }
        }

    fun find(id: Int): WiseSaying? {
        return wiseSayings.find { it.id == id }
    }

    fun delete(id: Int) {
        wiseSayings.removeIf { it.id == id }
        fileUtil.remove(id)
    }

    fun modify(id: Int, newWiseSaying: WiseSaying) {
        wiseSayings.replaceAll {
            if (it.id == id) newWiseSaying else it
        }
        fileUtil.save(id, newWiseSaying)
    }

    fun clearAll() {
        wiseSayings.clear()
        lastIndex = 1
        fileUtil.clearAll()
    }
}