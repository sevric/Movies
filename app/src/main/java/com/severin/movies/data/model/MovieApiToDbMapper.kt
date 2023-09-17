package com.severin.movies.data.model

class MovieApiToDbMapper {
    fun mapFromApiToDb(movieItemApi: MovieItemApi): MovieItemDB {
        return MovieItemDB(
            movieItemApi.backdrop_path ?: EMPTY_STRING,
            movieItemApi.genre_ids ?: emptyList(),
            movieItemApi.genres ?: emptyList(),
            movieItemApi.id ?: DEFAULT_ID_ZERO,
            movieItemApi.overview ?: EMPTY_STRING,
            movieItemApi.popularity ?: DEFAULT_ID_ZERO.toDouble(),
            movieItemApi.poster_path ?: EMPTY_STRING,
            movieItemApi.release_date ?: EMPTY_STRING,
            movieItemApi.title ?: EMPTY_STRING,
            movieItemApi.vote_average ?: DEFAULT_ID_ZERO.toDouble(),
            isFavourite = false,
            shouldWatchLater = false
        )
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val DEFAULT_ID_ZERO = 0
    }
}