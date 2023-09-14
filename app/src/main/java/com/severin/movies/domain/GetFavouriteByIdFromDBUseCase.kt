package com.severin.movies.domain

import com.severin.movies.data.model.MovieItemDB

class GetFavouriteByIdFromDBUseCase constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(movieId: Int?): MovieItemDB? =
        repository.getFavouriteByIdFromDB(movieId)
}