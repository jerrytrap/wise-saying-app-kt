package org.example

class WiseSayingService(
    private val wiseSayingRepository: WiseSayingRepository
) {
    fun addWiseSaying(wiseSaying: WiseSaying) = wiseSayingRepository.add(wiseSaying)

    fun getCount() = wiseSayingRepository.getCount();

    fun getWiseSayings() = wiseSayingRepository.getAll();

    fun deleteWiseSaying(id: Int): Boolean {
        return if (wiseSayingRepository.find(id) != null) {
            wiseSayingRepository.delete(id)
            true
        } else {
            false
        }
    }
}