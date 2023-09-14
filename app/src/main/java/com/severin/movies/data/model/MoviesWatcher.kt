package com.severin.movies.data.model

data class MoviesWatcher (
    val id: String,
    val name: String,
    val lastName: String,
    val age: Int
) {
    constructor() : this(
        "0",
        "default name",
        "default last name",
        20
    )
}