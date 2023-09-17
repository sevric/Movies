package com.severin.movies.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.severin.movies.data.model.MoviesResponse
import com.severin.movies.domain.GetMoviesByGenreIdFromApiUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesByGenreViewModel constructor(
    private val getMoviesByGenreIdFromApiUseCase: GetMoviesByGenreIdFromApiUseCase
) : ViewModel() {
    val moviesByGenre = MutableLiveData<MoviesResponse>()

    fun getMoviesByGenreId(movieId: Int?) = viewModelScope.launch(Dispatchers.IO) {
        moviesByGenre.postValue(
            getMoviesByGenreIdFromApiUseCase.invoke(
                PAGES_COUNT_FOR_POPULAR_MOVIES,
                POPULARITY_SORTING_DIRECTION,
                movieId
            )
        )
    }

    companion object {
        private const val PAGES_COUNT_FOR_POPULAR_MOVIES = 1
        private const val POPULARITY_SORTING_DIRECTION = "popularity.desc"
    }
}