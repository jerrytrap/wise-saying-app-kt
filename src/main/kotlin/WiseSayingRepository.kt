package org.example

class WiseSayingRepository {
    private val fileUtil = FileUtil("db/wiseSaying/")
    private val wiseSayings = ArrayList<WiseSaying>()

    fun getLastIndex() = fileUtil.getLastIndex()

    fun add(wiseSaying: WiseSaying) {
        wiseSayings.add(wiseSaying)
        fileUtil.save(wiseSaying)
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