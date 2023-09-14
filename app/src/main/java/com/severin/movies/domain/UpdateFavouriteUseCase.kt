package com.severin.movies.domain

class UpdateFavouriteUseCase constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int, isFavourite: Boolean) {
        repository.updateFavourite(movieId, isFavourite)
    }
}