package com.severin.movies.domain

import com.severin.movies.data.model.MoviesResponse
import javax.inject.Inject

class GetPopularMoviesFromApiUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(
        page: Int,
        sort_by: String,
        vote_count_gte: Int,
        vote_average_gte: Int
    ): MoviesResponse =
        repository.getPopularMoviesFromApi(
            page,
            sort_by,
            vote_count_gte,
            vote_average_gte
        )
}