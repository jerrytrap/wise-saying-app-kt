package org.example

class WiseSayingService(
    private val wiseSayingRepository: WiseSayingRepository
) {
    fun addWiseSaying(content: String, author: String): Int {
        val id = wiseSayingRepository.getLastIndex()
        val wiseSaying = WiseSaying(id, content, author)

        wiseSayingRepository.add(wiseSaying)
        return id
    }

    fun getCount() = wiseSayingRepository.getCount();

    fun getWiseSayings() = wiseSayingRepository.getAll();

    fun getWiseSayings(keywordType: KeywordType, keyword: String) = wiseSayingRepository.search(keywordType, keyword)

    fun deleteWiseSaying(id: Int): Boolean {
        return if (wiseSayingRepository.find(id) != null) {
            wiseSayingRepository.delete(id)
            true
        } else {
            false
        }
    }

    fun findWiseSaying(id: Int) = wiseSayingRepository.find(id)

    fun modifyWiseSaying(id: Int, content: String, author: String) {
        val newWiseSaying = WiseSaying(id, content, author)
        wiseSayingRepository.modify(id, newWiseSaying)
    }
}