package com.severin.movies.domain

import com.severin.movies.data.model.MovieItemDB
import javax.inject.Inject

class GetWatchLaterByIdFromDBUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(movieId: Int?): MovieItemDB? =
        repository.getWatchLaterByIdFromDB(movieId)
}