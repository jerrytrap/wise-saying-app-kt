package org.example.entity

data class WiseSaying(
    val id: Int = NULL_ID,
    val content: String,
    val author: String
) {
    companion object {
        private const val NULL_ID = -1
    }
}