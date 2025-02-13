package org.example

data class WiseSaying(
    val content: String,
    val author: String
) {
    val id = index++

    companion object {
        private var index = 1

        fun resetIndex() {
            index = 1
        }
    }
}