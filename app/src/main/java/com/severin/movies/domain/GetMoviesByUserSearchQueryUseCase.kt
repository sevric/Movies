package com.severin.movies.domain

class GetMoviesByUserSearchQueryUseCase constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(queryString: String) =
        repository.getMoviesByUserSearchQuery(queryString)
}