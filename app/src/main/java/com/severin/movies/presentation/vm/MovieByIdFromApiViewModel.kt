package com.severin.movies.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.severin.movies.data.model.MovieItemApi
import com.severin.movies.domain.GetMovieByIdFromApiUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieByIdFromApiViewModel constructor(
    private val getMovieByIdFromApiUseCase: GetMovieByIdFromApiUseCase
) : ViewModel() {
    val movieById = MutableLiveData<MovieItemApi>()//TODO(brakes Clean Architecture)

    fun getMovieById(movieId: Int?) = viewModelScope.launch(Dispatchers.IO) {
        movieById.postValue(
            getMovieByIdFromApiUseCase.invoke(movieId)
        )
    }
}