package com.severin.movies.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.severin.movies.data.model.MoviesResponse
import com.severin.movies.domain.GetMoviesByUserSearchQueryUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviesByUserSearchQueryViewModel @Inject constructor(
    private val getMoviesByUserSearchQueryUseCase: GetMoviesByUserSearchQueryUseCase
) : ViewModel() {
    val moviesByUserSearchQuery = MutableLiveData<MoviesResponse>()

    fun getMoviesByUserSearchQuery(queryString: String?) = viewModelScope.launch {
        val nonNullableQueryString: String = queryString ?: ""
        moviesByUserSearchQuery.postValue(
            getMoviesByUserSearchQueryUseCase.invoke(nonNullableQueryString)
        )
    }
}