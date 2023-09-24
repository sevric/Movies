package com.severin.movies.domain

import javax.inject.Inject

class UpdateFavouriteUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movieId: Int, isFavourite: Boolean) {
        repository.updateFavourite(movieId, isFavourite)
    }
}