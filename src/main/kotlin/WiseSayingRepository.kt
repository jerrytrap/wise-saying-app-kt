package org.example

class WiseSayingRepository {
    private val wiseSayings = ArrayList<WiseSaying>()
    private var lastIndex = 1

    fun getLastIndex() = lastIndex

    fun add(wiseSaying: WiseSaying) {
        wiseSayings.add(wiseSaying)
        lastIndex++
    }

    fun getCount() = wiseSayings.size

    fun getAll() = wiseSayings

    fun find(id: Int): WiseSaying? {
        return wiseSayings.find { it.id == id }
    }

    fun delete(id: Int) {
        wiseSayings.removeIf { it.id == id }
    }

    fun modify(id: Int, newWiseSaying: WiseSaying) {
        wiseSayings.replaceAll {
            if (it.id == id) newWiseSaying else it
        }
    }
}