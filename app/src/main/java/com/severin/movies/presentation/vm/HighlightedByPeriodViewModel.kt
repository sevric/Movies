package com.severin.movies.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.severin.movies.data.model.MoviesResponse
import com.severin.movies.domain.GetPopularMoviesByPeriodUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HighlightedByPeriodViewModel constructor(
    private val getPopularMoviesByPeriodUseCase: GetPopularMoviesByPeriodUseCase
) : ViewModel() {
    val highlightedByPeriodLiveData = MutableLiveData<MoviesResponse>()

    fun getHighlightedByPeriodMovies(period: String) = viewModelScope.launch(Dispatchers.IO) {
        highlightedByPeriodLiveData.postValue(
            getPopularMoviesByPeriodUseCase.invoke(
                period,
                POPULARITY_SORTING_DIRECTION,
                VOTE_AVERAGE_GREATER_THAN_PARAMETER
            )
        )
    }

    companion object {
        private const val POPULARITY_SORTING_DIRECTION = "popularity.desc"
        private const val VOTE_AVERAGE_GREATER_THAN_PARAMETER = 7
    }
}