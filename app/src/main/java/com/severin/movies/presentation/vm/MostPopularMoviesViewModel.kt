package com.severin.movies.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.severin.movies.data.model.MoviesResponse
import com.severin.movies.domain.GetPopularMoviesFromApiUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MostPopularMoviesViewModel @Inject constructor(
    private val getPopularMoviesFromApiUseCase: GetPopularMoviesFromApiUseCase
) : ViewModel() {
    val popularMoviesLiveData = MutableLiveData<MoviesResponse>()

    fun getPopularMoviesFromApi() = viewModelScope.launch(Dispatchers.IO) {
        popularMoviesLiveData.postValue(
            getPopularMoviesFromApiUseCase.invoke(
                PAGES_COUNT_FOR_POPULAR_MOVIES,
                POPULARITY_SORTING_DIRECTION,
                VOTE_COUNT_GREATER_THAN_PARAMETER,
                VOTE_AVERAGE_GREATER_THAN_PARAMETER
            )
        )
    }

    companion object {
        private const val PAGES_COUNT_FOR_POPULAR_MOVIES = 1
        private const val POPULARITY_SORTING_DIRECTION = "popularity.desc"
        private const val VOTE_COUNT_GREATER_THAN_PARAMETER = 20000
        private const val VOTE_AVERAGE_GREATER_THAN_PARAMETER = 7
    }
}