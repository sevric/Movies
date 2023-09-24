package com.severin.movies.domain

import com.severin.movies.data.model.MoviesResponse
import javax.inject.Inject

class GetPopularMoviesByPeriodUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(
        period: String,
        sort_by: String,
        vote_average_gte: Int
    ): MoviesResponse =
        repository.getPopularMoviesByPeriod(
            period,
            sort_by,
            vote_average_gte
        )
}