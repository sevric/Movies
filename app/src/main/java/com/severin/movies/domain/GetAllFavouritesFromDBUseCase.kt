package com.severin.movies.domain

class GetAllFavouritesFromDBUseCase constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke() =
        repository.getAllFavouritesFromDB()
}