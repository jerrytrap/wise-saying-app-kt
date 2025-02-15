package org.example

data class Page<T>(
    val data: List<T>,
    val page: Int,
    val totalSize: Int
)