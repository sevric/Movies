package com.severin.movies.domain

import com.severin.movies.data.model.MoviesResponse

class GetMoviesForKidsFromApiUseCase constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(
        page: Int,
        sort_by: String,
        vote_average_gte: Int,
        certification_lte: String,
        with_genres: Int
    ): MoviesResponse =
        repository.getMoviesForKidsFromApi(
            page,
            sort_by,
            vote_average_gte,
            certification_lte,
            with_genres
        )
}