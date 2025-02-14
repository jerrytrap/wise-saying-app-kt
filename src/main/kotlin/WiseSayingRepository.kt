package org.example

class WiseSayingRepository {
    private val wiseSayings = ArrayList<WiseSaying>()

    fun add(wiseSaying: WiseSaying) {
        wiseSayings.add(wiseSaying)
    }

    fun getCount() = wiseSayings.size

    fun getAll() = wiseSayings

    fun find(id: Int): WiseSaying? {
        return wiseSayings.find { it.id == id }
    }

    fun delete(id: Int) {
        wiseSayings.removeIf { it.id == id }
    }
}