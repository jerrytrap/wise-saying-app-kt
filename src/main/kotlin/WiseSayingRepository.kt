package org.example

import kotlin.math.ceil
import kotlin.math.min

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

    fun addWiseSaying(wiseSaying: WiseSaying) {
        wiseSayings.add(wiseSaying)
        lastIndex = fileUtil.save(lastIndex, wiseSaying)
    }

    fun getCount() = wiseSayings.size

    fun getWiseSayings(keywordType: KeywordType, keyword: String, page: Int = 1): Page<WiseSaying> {
        val searchResult = wiseSayings
            .filter {
                when (keywordType) {
                    KeywordType.CONTENT -> it.content.contains(keyword)
                    KeywordType.AUTHOR -> it.author.contains(keyword)
                    KeywordType.NONE -> true
                }
            }
        val totalPageCount = ceil(searchResult.size / OFFSET.toFloat()).toInt()

        if (totalPageCount < page) {
            return Page(emptyList(), page, totalPageCount)
        }

        return Page(searchResult
            .subList(min(searchResult.size - 1, (page - 1) * OFFSET), min(page * OFFSET, searchResult.size)),
            page,
            totalPageCount
        )
    }

    fun findWiseSaying(id: Int): WiseSaying? = wiseSayings.find { it.id == id }

    fun deleteWiseSaying(id: Int) {
        wiseSayings.removeIf { it.id == id }
        fileUtil.remove(id)
    }

    fun modifyWiseSaying(id: Int, newWiseSaying: WiseSaying) {
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

    companion object {
        private const val OFFSET = 5
    }
}