package org.example.entity

enum class KeywordType(private val type: String) {
    CONTENT("content"),
    AUTHOR("author"),
    NONE("none");

    companion object {
        fun fromType(type: String): KeywordType {
            return entries.find { it.type.equals(type, ignoreCase = true) } ?: NONE
        }
    }
}