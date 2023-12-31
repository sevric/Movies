package com.severin.movies.domain

import com.severin.movies.data.model.MovieItemApi
import javax.inject.Inject

class GetMovieByIdFromApiUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(
        movieId: Int?
    ): MovieItemApi =
        repository.getMovieByIdFromApi(movieId)
}