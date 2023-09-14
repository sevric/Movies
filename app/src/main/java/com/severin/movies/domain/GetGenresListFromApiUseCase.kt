package com.severin.movies.domain

import com.severin.movies.data.model.GenresResponse

class GetGenresListFromApiUseCase constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(): GenresResponse =
        repository.getGenresListFromApi()
}