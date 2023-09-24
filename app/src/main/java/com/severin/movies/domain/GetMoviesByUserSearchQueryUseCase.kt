package com.severin.movies.domain

import javax.inject.Inject

class GetMoviesByUserSearchQueryUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(queryString: String) =
        repository.getMoviesByUserSearchQuery(queryString)
}