package org.example.repository

import org.example.entity.KeywordType
import org.example.entity.Page
import org.example.entity.WiseSaying
import kotlin.math.ceil
import kotlin.math.min

class WiseSayingRepository(
    private val fileManager: FileManager
) {
    private val wiseSayings = ArrayList<WiseSaying>()
    private var lastIndex = fileManager.loadLastIndex()

    init {
        wiseSayings.addAll(fileManager.loadAll())
    }

    fun addWiseSaying(wiseSaying: WiseSaying): Int {
        val newWiseSaying = WiseSaying(lastIndex, wiseSaying.content, wiseSaying.author)

        wiseSayings.add(0, newWiseSaying)
        fileManager.apply {
            save(newWiseSaying)
            updateIndex(lastIndex + 1)
        }

        return lastIndex++
    }

    fun getCount() = wiseSayings.size

    fun getWiseSayings(keywordType: KeywordType, keyword: String, page: Int = 1): Page<WiseSaying> {
        if (wiseSayings.isEmpty()) {
            return Page(emptyList(), page, 0)
        }

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

        val pagedList = searchResult
            .subList(
                min(searchResult.size - 1, (page - 1) * OFFSET),
                min(page * OFFSET, searchResult.size)
            )

        return Page(pagedList, page, totalPageCount)
    }

    fun findWiseSaying(id: Int): WiseSaying? = wiseSayings.find { it.id == id }

    fun deleteWiseSaying(id: Int) {
        wiseSayings.removeIf { it.id == id }
        fileManager.remove(id)
    }

    fun modifyWiseSaying(id: Int, newWiseSaying: WiseSaying) {
        wiseSayings.replaceAll {
            if (it.id == id) newWiseSaying else it
        }
        fileManager.modify(id, newWiseSaying)
    }

    fun buildWiseSaying() {
        fileManager.build(wiseSayings)
    }

    fun clearAll() {
        wiseSayings.clear()
        lastIndex = 1
        fileManager.clearAll()
    }

    companion object {
        private const val OFFSET = 5
    }
}