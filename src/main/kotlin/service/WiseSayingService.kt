package org.example.service

import org.example.entity.KeywordType
import org.example.entity.WiseSaying
import org.example.repository.WiseSayingRepository

class WiseSayingService(
    private val wiseSayingRepository: WiseSayingRepository
) {
    fun addWiseSaying(content: String, author: String): Int {
        val wiseSaying = WiseSaying(content = content, author = author)
        val generatedId = wiseSayingRepository.addWiseSaying(wiseSaying)

        return generatedId
    }

    fun getCount() = wiseSayingRepository.getCount();

    fun getWiseSayings(keywordType: KeywordType, keyword: String, page: Int = 1) =
        wiseSayingRepository.getWiseSayings(keywordType, keyword, page)

    fun deleteWiseSaying(id: Int) =
        wiseSayingRepository.deleteWiseSaying(id)

    fun findWiseSaying(id: Int) = wiseSayingRepository.findWiseSaying(id)

    fun modifyWiseSaying(id: Int, content: String, author: String) {
        val newWiseSaying = WiseSaying(id, content, author)
        wiseSayingRepository.modifyWiseSaying(id, newWiseSaying)
    }

    fun buildWiseSaying() = wiseSayingRepository.buildWiseSaying()
}