package com.severin.movies.domain

import javax.inject.Inject

class GetAllWatchLaterFromDBUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke() =
        repository.getAllWatchLaterFromDB()
}