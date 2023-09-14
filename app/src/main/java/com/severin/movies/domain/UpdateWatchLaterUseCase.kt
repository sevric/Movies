package com.severin.movies.domain

class UpdateWatchLaterUseCase constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int, shouldWatchLater: Boolean) {
        repository.updateWatchLater(movieId, shouldWatchLater)
    }
}