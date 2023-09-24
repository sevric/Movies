package com.severin.movies.domain

import com.severin.movies.data.model.MoviesResponse
import javax.inject.Inject

class GetMoviesByGenreIdFromApiUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(
        page: Int,
        sort_by: String,
        genreId: Int?
    ): MoviesResponse =
        repository.getMoviesByGenreIdFromApi(
            page,
            sort_by,
            genreId
        )
}