package com.severin.movies.domain

import javax.inject.Inject

class UpdateWatchLaterUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int, shouldWatchLater: Boolean) {
        repository.updateWatchLater(movieId, shouldWatchLater)
    }
}