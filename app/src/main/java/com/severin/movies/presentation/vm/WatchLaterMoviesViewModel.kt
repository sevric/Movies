package com.severin.movies.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.severin.movies.data.model.MovieItemApi
import com.severin.movies.data.model.MovieItemDB
import com.severin.movies.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchLaterMoviesViewModel constructor(
    private val getWatchLaterByIdFromDBUseCase: GetWatchLaterByIdFromDBUseCase,
    private val getMovieByIdFromDBUseCase: GetMovieByIdFromDBUseCase,
    private val insertWatchLaterIntoDBUseCase: InsertWatchLaterIntoDBUseCase,
    private val updateWatchLaterUseCase: UpdateWatchLaterUseCase,
    private val removeMovieFromDBByIdUseCase: RemoveMovieFromDBByIdUseCase
): ViewModel() {
    private val _movieWatchLaterById = MutableLiveData<MovieItemDB?>()
    val movieWatchLaterById: LiveData<MovieItemDB?>
        get() = _movieWatchLaterById

    fun getWatchLaterMovieById(movieId: Int?) = viewModelScope.launch(Dispatchers.IO) {
        _movieWatchLaterById.postValue(
            getWatchLaterByIdFromDBUseCase.invoke(movieId)
        )
    }

    fun toggleWatchLaterMovie(movieItemApi: MovieItemApi) = viewModelScope.launch(Dispatchers.IO) {
        val movieItemFromDB = getMovieByIdFromDBUseCase.invoke(movieItemApi.id)
        if (movieItemFromDB == null) {
            insertWatchLaterIntoDBUseCase.invoke(movieItemApi)
        } else {
            updateWatchLaterUseCase.invoke(
                movieItemFromDB.movieDbId, !movieItemFromDB.shouldWatchLater
            )
            val movieItemFromDBUpdated = getMovieByIdFromDBUseCase.invoke(movieItemApi.id)
            if (
                (movieItemFromDBUpdated != null) &&
                !(movieItemFromDBUpdated.shouldWatchLater) &&
                !(movieItemFromDBUpdated.isFavourite)
            ) {
                removeMovieFromDBByIdUseCase.invoke(movieItemApi)
            }
        }
        _movieWatchLaterById.postValue(
            getWatchLaterByIdFromDBUseCase.invoke(movieItemApi.id)
        )
    }
}