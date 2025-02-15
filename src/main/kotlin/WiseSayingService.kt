package org.example

class WiseSayingService(
    private val wiseSayingRepository: WiseSayingRepository
) {
    fun addWiseSaying(content: String, author: String): Int {
        val id = wiseSayingRepository.getLastIndex()
        val wiseSaying = WiseSaying(id, content, author)

        wiseSayingRepository.addWiseSaying(wiseSaying)
        return id
    }

    fun getCount() = wiseSayingRepository.getCount();

    fun getWiseSayings(keywordType: KeywordType, keyword: String, page: Int = 1) =
        wiseSayingRepository.getWiseSayings(keywordType, keyword, page)

    fun deleteWiseSaying(id: Int): Boolean {
        return if (wiseSayingRepository.findWiseSaying(id) != null) {
            wiseSayingRepository.deleteWiseSaying(id)
            true
        } else {
            false
        }
    }

    fun findWiseSaying(id: Int) = wiseSayingRepository.findWiseSaying(id)

    fun modifyWiseSaying(id: Int, content: String, author: String) {
        val newWiseSaying = WiseSaying(id, content, author)
        wiseSayingRepository.modifyWiseSaying(id, newWiseSaying)
    }
}