package com.severin.movies.domain

import javax.inject.Inject

class GetAllFavouritesFromDBUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke() =
        repository.getAllFavouritesFromDB()
}