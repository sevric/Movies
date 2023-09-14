package com.severin.movies.domain

import com.severin.movies.data.model.MovieItemApi
import com.severin.movies.data.model.MovieItemDB

class RemoveMovieFromDBByIdUseCase constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(
        movieItemApi: MovieItemApi
    ): MovieItemDB =
        repository.removeMovieFromDBById(movieItemApi)
}