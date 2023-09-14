package com.severin.movies.domain

class GetAllWatchLaterFromDBUseCase constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke() =
        repository.getAllWatchLaterFromDB()
}