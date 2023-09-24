package com.severin.movies.domain

import com.severin.movies.data.model.GenresResponse
import javax.inject.Inject

class GetGenresListFromApiUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(): GenresResponse =
        repository.getGenresListFromApi()
}