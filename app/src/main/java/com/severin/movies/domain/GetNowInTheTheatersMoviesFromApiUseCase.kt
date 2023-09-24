package com.severin.movies.domain

import com.severin.movies.data.model.MoviesResponse
import javax.inject.Inject

class GetNowInTheTheatersMoviesFromApiUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(
        date: String
    ): MoviesResponse =
        repository.getNowInTheTheatersMoviesFromApi(date)
}