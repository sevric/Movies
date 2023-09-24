package com.severin.movies.domain

import com.severin.movies.data.model.MovieItemApi
import com.severin.movies.data.model.MovieItemDB
import javax.inject.Inject

class InsertWatchLaterIntoDBUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(
        movieItemApi: MovieItemApi
    ): MovieItemDB =
        repository.insertWatchLaterIntoDB(movieItemApi)
}