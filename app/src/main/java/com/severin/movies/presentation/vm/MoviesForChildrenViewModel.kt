package com.severin.movies.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.severin.movies.data.model.MoviesResponse
import com.severin.movies.domain.GetMoviesForKidsFromApiUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesForChildrenViewModel constructor(
    private val getMoviesForKidsFromApiUseCase: GetMoviesForKidsFromApiUseCase
) : ViewModel() {
    val moviesForKidsFromApi = MutableLiveData<MoviesResponse>()

    fun getMoviesForKidsFromApi() = viewModelScope.launch(Dispatchers.IO) {
        moviesForKidsFromApi.postValue(
            getMoviesForKidsFromApiUseCase.invoke(
                PAGES_COUNT_FOR_POPULAR_MOVIES,
                POPULARITY_SORTING_DIRECTION,
                VOTE_AVERAGE_GREATER_THAN_PARAMETER,
                MOVIE_CERTIFICATION_ID,
                GENRE_ID
            )
        )
    }

    companion object {
        private const val PAGES_COUNT_FOR_POPULAR_MOVIES = 1
        private const val POPULARITY_SORTING_DIRECTION = "popularity.desc"
        private const val VOTE_AVERAGE_GREATER_THAN_PARAMETER = 7
        private const val MOVIE_CERTIFICATION_ID = "G"
        private const val GENRE_ID = 16
    }
}